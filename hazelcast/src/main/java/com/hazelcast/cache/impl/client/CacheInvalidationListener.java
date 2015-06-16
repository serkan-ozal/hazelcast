/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.cache.impl.client;

import com.hazelcast.cache.impl.CacheEventListener;
import com.hazelcast.client.ClientEndpoint;

import java.util.Iterator;
import java.util.List;

public final class CacheInvalidationListener implements CacheEventListener {

    private final ClientEndpoint endpoint;
    private final int callId;

    public CacheInvalidationListener(ClientEndpoint endpoint, int callId) {
        this.endpoint = endpoint;
        this.callId = callId;
    }

    @Override
    public void handleEvent(Object eventObject) {
        if (eventObject instanceof CacheInvalidationMessage) {
            CacheInvalidationMessage message = (CacheInvalidationMessage) eventObject;
            if (endpoint.isAlive()) {
                if (message instanceof CacheSingleInvalidationMessage) {
                    handleSingleInvalidationMessage((CacheSingleInvalidationMessage) message);
                } else if (message instanceof CacheBatchInvalidationMessage) {
                    handleBatchInvalidationMessage((CacheBatchInvalidationMessage) message);
                } else {
                    throw new IllegalArgumentException("Unsupported invalidation message: " + message);
                }
            }
        }
    }

    private void handleSingleInvalidationMessage(CacheSingleInvalidationMessage singleInvalidationMessage) {
        // No need to send event to its source
        if (!endpoint.getUuid().equals(singleInvalidationMessage.getSourceUuid())) {
            String name = singleInvalidationMessage.getName();
            // We don't need "name" at client
            singleInvalidationMessage.setName(null);
            endpoint.sendEvent(name, singleInvalidationMessage, callId);
        }
    }

    private void handleBatchInvalidationMessage(CacheBatchInvalidationMessage batchInvalidationMessage) {
        List<CacheSingleInvalidationMessage> singleInvalidationMessageList =
                batchInvalidationMessage.getInvalidationMessages();
        if (singleInvalidationMessageList != null && !singleInvalidationMessageList.isEmpty()) {
            Iterator<CacheSingleInvalidationMessage> it =
                    batchInvalidationMessage.getInvalidationMessages().iterator();
            while (it.hasNext()) {
                CacheSingleInvalidationMessage singleInvalidationMessage = it.next();
                // We don't need "name" at client
                singleInvalidationMessage.setName(null);
            }
            String name = batchInvalidationMessage.getName();
            // We don't need "name" at client
            batchInvalidationMessage.setName(null);
            endpoint.sendEvent(name, batchInvalidationMessage, callId);
        }
    }

}
