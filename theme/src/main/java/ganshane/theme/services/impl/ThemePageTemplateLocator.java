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

import static java.lang.String.format;

import java.util.Locale;

import org.apache.tapestry5.TapestryConstants;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.model.ComponentModel;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.templates.ComponentTemplateLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支持模板样式的模板locator
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class ThemePageTemplateLocator implements ComponentTemplateLocator {

    private final Resource contextRoot;

    private Logger logger = LoggerFactory.getLogger(ThemePageTemplateLocator.class);

    public ThemePageTemplateLocator(Resource contextRoot, ComponentClassResolver resolver)
    {
        this.contextRoot = contextRoot;
    }

    public Resource locateTemplate(ComponentModel model, Locale locale)
    {
        String className = model.getComponentClassName();

        // A bit of a hack, but should work.
        String logicalName = className.replaceAll("\\.", "/");
        
        if(logicalName == null) {
            return null;
        }


        int slashx = logicalName.lastIndexOf('/');

        if (slashx > 0)
        {
            // However, the logical name isn't quite what we want. It may have been somewhat
            // trimmed.

            String simpleClassName = InternalUtils.lastTerm(className);

            logicalName = logicalName.substring(0, slashx + 1) + simpleClassName;
        }

        String path = format("%s%s.%s","themes/default/",logicalName, TapestryConstants.TEMPLATE_EXTENSION);
        
        
        logger.debug("page theme path:{}",path);

        Resource file = contextRoot.forFile(path);
        if(file.exists())  {
           return file.forLocale(locale); 
        }
        return null;
    }

}
