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
package corner;

import org.apache.tapestry5.ioc.annotations.SubModule;

import corner.cache.CacheModule;
import corner.config.ConfigurationModule;
import corner.encrypt.EncryptModule;
import corner.orm.OrmModule;
import corner.protobuf.ProtocolBuffersModule;
import corner.transaction.TransactionModule;

/**
 * corner core module definition
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
@SubModule( {
     ProtocolBuffersModule.class,
    ConfigurationModule.class,TransactionModule.class,
    EncryptModule.class,OrmModule.class,CacheModule.class})
public class CornerModule {

}
