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
package ganshane.theme;

import ganshane.theme.services.ForceReloadTemplateHub;
import ganshane.theme.services.impl.ForceReloadTemplateHubImpl;
import ganshane.theme.services.impl.ThemePageTemplateLocator;
import ganshane.theme.services.impl.ThemeTemplateSourceImpl;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.ComponentTemplateSource;
import org.apache.tapestry5.internal.services.TemplateParser;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.annotations.ServiceId;
import org.apache.tapestry5.ioc.services.ClasspathURLConverter;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ContextProvider;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.UpdateListenerHub;
import org.apache.tapestry5.services.templates.ComponentTemplateLocator;

/**
 * 样式模块，能够自定义样式
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class ThemeModule {
    public static void bind(ServiceBinder binder) {
        binder.bind(ForceReloadTemplateHub.class, ForceReloadTemplateHubImpl.class).preventReloading();
    }
    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
        configuration.add(SymbolConstants.SUPPRESS_REDIRECT_FROM_ACTION_REQUESTS, String.valueOf(Boolean.TRUE));
    }
    public static void contributeComponentTemplateLocator(OrderedConfiguration<ComponentTemplateLocator> configuration,
            @ContextProvider
            AssetFactory contextAssetFactory, ComponentClassResolver componentClassResolver)
    {
        configuration
                .add("Theme", new ThemePageTemplateLocator(contextAssetFactory.getRootResource(), componentClassResolver),
                        "before:Default");
    }
    @ServiceId("ThemeComponentTemplateSource")
    public ComponentTemplateSource buildComponentTemplateSource(TemplateParser parser, @Primary
            ComponentTemplateLocator locator, ClasspathURLConverter classpathURLConverter,
            ForceReloadTemplateHub forceReloadTemplateHub,UpdateListenerHub updateListenerHub)
            {
                ThemeTemplateSourceImpl service = new ThemeTemplateSourceImpl(parser, locator, classpathURLConverter);
                updateListenerHub.addUpdateListener(service);
                forceReloadTemplateHub.addReloadListener(service);
                return service;
            }

    public static void contributeServiceOverride(MappedConfiguration<Class<?>, Object> configuration,
            @Local ComponentTemplateSource componentTemplateSource) {
        configuration.add(ComponentTemplateSource.class,componentTemplateSource);
    }
    /**
     * Contribution to the
     * {@link org.apache.tapestry5.services.ComponentClassResolver} service
     * configuration.
     */
    public static void contributeComponentClassResolver(
            Configuration<LibraryMapping> configuration) {
        configuration.add(new LibraryMapping("theme","ganshane.theme"));
    }
}
