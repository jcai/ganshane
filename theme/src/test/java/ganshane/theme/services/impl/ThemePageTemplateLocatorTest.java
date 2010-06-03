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

import org.apache.tapestry5.model.ComponentModel;
import org.apache.tapestry5.test.TapestryTestCase;
import org.testng.annotations.Test;

/**
 * test theme page template locator
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class ThemePageTemplateLocatorTest extends TapestryTestCase{
    @Test
    public void test_convertAsTemplatePath() {
        ThemePageTemplateLocator locator = new ThemePageTemplateLocator(null);
        ComponentModel model = newMock(ComponentModel.class);
        expect(model.getComponentClassName()).andReturn("foo.bar.TestPage");
        replay();
        assertEquals(locator.convertAsTemplatePath(model),"themes/default/foo/bar/TestPage.tml");
        verify();
    }
}