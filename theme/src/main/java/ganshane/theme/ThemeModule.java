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

import ganshane.theme.services.impl.ThemePageResponseRendererImpl;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.PageResponseRenderer;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.ServiceId;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;

/**
 * 样式模块，能够自定义样式
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class ThemeModule {
    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
        configuration.add(SymbolConstants.SUPPRESS_REDIRECT_FROM_ACTION_REQUESTS, String.valueOf(Boolean.TRUE));
    }
    public void contributeMarkupRenderer(OrderedConfiguration<MarkupRendererFilter> configuration) {
        configuration.add("layout", new MarkupRendererFilter() {

            public void renderMarkup(MarkupWriter writer,
                    MarkupRenderer renderer) {
               writer.element("html");
               
               writer.element("head");
               writer.end();
               
               writer.element("body");
               
               renderer.renderMarkup(writer);
               
               writer.end();
               writer.end();
            }},"before:DocumentLinker");
        
    }
    @ServiceId("ThemePageResponseRenderer")
    public PageResponseRenderer buildPageResponseRenderer(ObjectLocator locator) {
        return locator.autobuild(ThemePageResponseRendererImpl.class);
    }
    public static void contributeServiceOverride(MappedConfiguration<Class<?>, Object> configuration,
            @Local PageResponseRenderer pageResponseRenderer) {
        configuration.add(PageResponseRenderer.class,pageResponseRenderer);
    }
}
