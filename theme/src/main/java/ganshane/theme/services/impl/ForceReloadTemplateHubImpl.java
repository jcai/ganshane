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

import ganshane.theme.services.ForceReloadTemplateHub;
import ganshane.theme.services.ReloadTemplateListener;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.internal.util.Defense;

/**
 * 强制重新加载模板的监听器组合
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class ForceReloadTemplateHubImpl implements ForceReloadTemplateHub{
    private final List<WeakReference<ReloadTemplateListener>> listeners = CollectionFactory.newThreadSafeList();
    /**
     * @see ganshane.theme.services.ForceReloadTemplateHub#addReloadListener(ganshane.theme.services.ReloadTemplateListener)
     */
    public void addReloadListener(ReloadTemplateListener listener) {
        Defense.notNull(listener, "listener");

        listeners.add(new WeakReference<ReloadTemplateListener>(listener));
    }

    /**
     * @see ganshane.theme.services.ForceReloadTemplateHub#fireReloadTemplateEvent()
     */
    public void fireReloadTemplateEvent() {
        List<WeakReference<ReloadTemplateListener>> deadReferences = CollectionFactory.newList();

        Iterator<WeakReference<ReloadTemplateListener>> i = listeners.iterator();

        while (i.hasNext())
        {
            WeakReference<ReloadTemplateListener> reference = i.next();

            ReloadTemplateListener listener = reference.get();

            if (listener == null)
                deadReferences.add(reference);
            else
                listener.reloadTemplate();
        }

        if (!deadReferences.isEmpty())
            listeners.removeAll(deadReferences);
    }
}
