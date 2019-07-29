/*
 *  Copyright 2016-2019 新商态（北京）科技有限公司
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.grails.plugins.i18n

import grails.core.GrailsApplication
import grails.plugins.GrailsPluginManager
import org.grails.spring.context.support.PluginAwareResourceBundleMessageSource

import java.text.MessageFormat

/*
 * Created by wdg100 on 19/7/21
 */

class BundleMessageSourceWithPrecessor extends PluginAwareResourceBundleMessageSource{

    IMessageSourcePrecessor messageSourcePrecessor

    BundleMessageSourceWithPrecessor() {
    }

    BundleMessageSourceWithPrecessor(GrailsApplication application, GrailsPluginManager pluginManager) {
        super(application, pluginManager)
    }


    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        if(messageSourcePrecessor!=null){
            String result=messageSourcePrecessor.resolveCodeWithoutArguments(code,locale)
            if(result!=null){
                return result;
            }
        }

        return super.resolveCodeWithoutArguments(code,locale);
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        if(messageSourcePrecessor!=null) {
            MessageFormat result = messageSourcePrecessor.resolveCode(code, locale)
            if (result != null) {
                return result;
            }
        }
        return super.resolveCode(code,locale);
    }


}
