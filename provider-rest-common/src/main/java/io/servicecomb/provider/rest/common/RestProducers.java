/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.servicecomb.provider.rest.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import io.servicecomb.core.provider.CseBeanPostProcessor.ProviderProcessor;
import io.servicecomb.core.provider.producer.ProducerMeta;
import io.servicecomb.foundation.common.utils.BeanUtils;

@Component
public class RestProducers implements ProviderProcessor {
    private List<ProducerMeta> producerMetaList = new ArrayList<>();

    public List<ProducerMeta> getProducerMetaList() {
        return producerMetaList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processProvider(ApplicationContext applicationContext, String beanName, Object bean) {
        RestSchema restSchema = bean.getClass().getAnnotation(RestSchema.class);
        if (restSchema == null) {
            return;
        }

        Class<?> beanCls = BeanUtils.getImplClassFromBean(bean);
        ProducerMeta producerMeta = new ProducerMeta(restSchema.schemaId(), bean, beanCls);
        producerMetaList.add(producerMeta);
    }
}