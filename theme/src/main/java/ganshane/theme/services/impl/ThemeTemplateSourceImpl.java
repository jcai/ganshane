/* 
 * Copyright 2010 The Ganshane Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ganshane.theme.services.impl;

import ganshane.theme.services.ReloadTemplateListener;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.tapestry5.TapestryConstants;
import org.apache.tapestry5.internal.event.InvalidationEventHubImpl;
import org.apache.tapestry5.internal.parser.ComponentTemplate;
import org.apache.tapestry5.internal.parser.TemplateToken;
import org.apache.tapestry5.internal.services.ComponentTemplateSource;
import org.apache.tapestry5.internal.services.TemplateParser;
import org.apache.tapestry5.internal.util.MultiKey;
import org.apache.tapestry5.internal.util.URLChangeTracker;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.services.ClasspathURLConverter;
import org.apache.tapestry5.model.ComponentModel;
import org.apache.tapestry5.services.InvalidationEventHub;
import org.apache.tapestry5.services.UpdateListener;
import org.apache.tapestry5.services.templates.ComponentTemplateLocator;

/**
 * 复写tapestry自带的模板source能够方便的进行重新加载模板j
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class ThemeTemplateSourceImpl extends InvalidationEventHubImpl implements ComponentTemplateSource,
        UpdateListener,ReloadTemplateListener
{
    private final TemplateParser parser;

    private final ComponentTemplateLocator locator;

    private final URLChangeTracker tracker;

    /**
     * Caches from a key (combining component name and locale) to a resource. Often, many different keys will point to
     * the same resource (i.e., "foo:en_US", "foo:en_UK", and "foo:en" may all be parsed from the same "foo.tml"
     * resource). The resource may end up being null, meaning the template does not exist in any locale.
     */
    private final Map<MultiKey, Resource> templateResources = CollectionFactory.newConcurrentMap();

    /**
     * Cache of parsed templates, keyed on resource.
     */
    private final Map<Resource, ComponentTemplate> templates = CollectionFactory.newConcurrentMap();

    private final ComponentTemplate missingTemplate = new ComponentTemplate()
    {
        public Map<String, Location> getComponentIds()
        {
            return Collections.emptyMap();
        }

        public Resource getResource()
        {
            return null;
        }

        public List<TemplateToken> getTokens()
        {
            return Collections.emptyList();
        }

        public boolean isMissing()
        {
            return true;
        }

        public List<TemplateToken> getExtensionPointTokens(String extensionPointId)
        {
            return null;
        }

        public boolean isExtension()
        {
            return false;
        }
    };

    public ThemeTemplateSourceImpl(TemplateParser parser, @Primary
    ComponentTemplateLocator templateLocator, ClasspathURLConverter classpathURLConverter)
    {
        this(parser, templateLocator, new URLChangeTracker(classpathURLConverter));
    }

    ThemeTemplateSourceImpl(TemplateParser parser, ComponentTemplateLocator locator, URLChangeTracker tracker)
    {
        this.parser = parser;
        this.locator = locator;
        this.tracker = tracker;
    }

    /**
     * Resolves the component name to a localized {@link Resource} (using the {@link ComponentTemplateLocator} chain of
     * command service). The localized resource is used as the key to a cache of {@link ComponentTemplate}s.
     * <p/>
     * If a template doesn't exist, then the missing ComponentTemplate is returned.
     */
    public ComponentTemplate getTemplate(ComponentModel componentModel, Locale locale)
    {
        String componentName = componentModel.getComponentClassName();

        MultiKey key = new MultiKey(componentName, locale);

        // First cache is key to resource.

        Resource resource = templateResources.get(key);

        if (resource == null)
        {
            resource = locateTemplateResource(componentModel, locale);
            templateResources.put(key, resource);
        }

        // If we haven't yet parsed the template into the cache, do so now.

        ComponentTemplate result = templates.get(resource);

        if (result == null)
        {
            result = parseTemplate(resource);
            templates.put(resource, result);
        }

        return result;
    }

    private ComponentTemplate parseTemplate(Resource r)
    {
        // In a race condition, we may parse the same template more than once. This will likely add
        // the resource to the tracker multiple times. Not likely this will cause a big issue.

        if (!r.exists())
            return missingTemplate;

        tracker.add(r.toURL());

        return parser.parseTemplate(r);
    }

    private Resource locateTemplateResource(ComponentModel initialModel, Locale locale)
    {
        ComponentModel model = initialModel;
        while (model != null)
        {
            Resource localized = locator.locateTemplate(model, locale);

            if (localized != null)
                return localized;

            // Otherwise, this component doesn't have its own template ... lets work up to its
            // base class and check there.

            model = model.getParentModel();
        }

        // This will be a Resource whose URL is null, which will be picked up later and force the
        // return of the empty template.

        return initialModel.getBaseResource().withExtension(TapestryConstants.TEMPLATE_EXTENSION);
    }

    /**
     * Checks to see if any parsed resource has changed. If so, then all internal caches are cleared, and an
     * invalidation event is fired. This is brute force ... a more targeted dependency management strategy may come
     * later.
     */
    public void checkForUpdates()
    {
        if (tracker.containsChanges())
        {
            tracker.clear();
            templateResources.clear();
            templates.clear();
            fireInvalidationEvent();
        }
    }

    public InvalidationEventHub getInvalidationEventHub()
    {
        return this;
    }

    /**
     * @see ganshane.theme.services.ReloadTemplateListener#reloadTemplate()
     */
    public void reloadTemplate() {
            tracker.clear();
            templateResources.clear();
            templates.clear();
            fireInvalidationEvent();
    }
}