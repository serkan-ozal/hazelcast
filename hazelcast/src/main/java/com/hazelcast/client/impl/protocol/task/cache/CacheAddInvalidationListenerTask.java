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

package com.hazelcast.client.impl.protocol.task.cache;

import com.hazelcast.cache.impl.CacheEventListener;
import com.hazelcast.cache.impl.CacheService;
import com.hazelcast.cache.impl.client.CacheBatchInvalidationMessage;
import com.hazelcast.cache.impl.client.CacheInvalidationMessage;
import com.hazelcast.cache.impl.client.CacheSingleInvalidationMessage;
import com.hazelcast.client.ClientEndpoint;
import com.hazelcast.client.impl.protocol.ClientMessage;
import com.hazelcast.client.impl.protocol.codec.CacheAddInvalidationListenerCodec;
import com.hazelcast.client.impl.protocol.task.AbstractCallableMessageTask;
import com.hazelcast.instance.Node;
import com.hazelcast.nio.Connection;
import com.hazelcast.nio.serialization.Data;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class CacheAddInvalidationListenerTask
        extends AbstractCallableMessageTask<CacheAddInvalidationListenerCodec.RequestParameters> {

    public CacheAddInvalidationListenerTask(ClientMessage clientMessage, Node node, Connection connection) {
        super(clientMessage, node, connection);
    }

    @Override
    protected Object call() {
        final ClientEndpoint endpoint = getEndpoint();
        CacheService cacheService = getService(CacheService.SERVICE_NAME);
        String registrationId = cacheService.addInvalidationListener(parameters.name, new CacheEventListener() {
            @Override
            public void handleEvent(Object eventObject) {
                if (!endpoint.isAlive()) {
                    return;
                }
                if (eventObject instanceof CacheInvalidationMessage) {
                    if (eventObject instanceof CacheSingleInvalidationMessage) {
                        handleSingleInvalidationMessage((CacheSingleInvalidationMessage) eventObject);
                    } else if (eventObject instanceof CacheBatchInvalidationMessage) {
                        handleBatchInvalidationMessage((CacheBatchInvalidationMessage) eventObject);
                    } else {
                        throw new IllegalArgumentException("Unsupported invalidation message: " + eventObject);
                    }
                }
            }
        });
        endpoint.setListenerRegistration(CacheService.SERVICE_NAME, parameters.name, registrationId);
        return registrationId;
    }

    private void handleSingleInvalidationMessage(CacheSingleInvalidationMessage singleInvalidationMessage) {
        // No need to send event to its source
        if (!endpoint.getUuid().equals(singleInvalidationMessage.getSourceUuid())) {
            String name = singleInvalidationMessage.getName();
            // We don't need "name" at client and
            // we filtered as source uuid so need to send it to client
            ClientMessage eventMessage = CacheAddInvalidationListenerCodec.
                    encodeCacheInvalidationEvent(null, singleInvalidationMessage.getKey(), null);
            sendClientMessage(name, eventMessage);
        }
    }

    private void handleBatchInvalidationMessage(CacheBatchInvalidationMessage batchInvalidationMessage) {
        String name = batchInvalidationMessage.getName();
        List<CacheSingleInvalidationMessage> invalidationMessages =
                batchInvalidationMessage.getInvalidationMessages();
        List<Data> keys = new ArrayList<Data>(invalidationMessages.size());
        for (CacheSingleInvalidationMessage invalidationMessage : invalidationMessages) {
            // No need to send event to its source
            if (!endpoint.getUuid().equals(invalidationMessage.getSourceUuid())) {
                keys.add(invalidationMessage.getKey());
            }
        }
        // We don't need "name" at client and
        // we filtered as source uuid list so need to send it to client
        ClientMessage eventMessage = CacheAddInvalidationListenerCodec.
                encodeCacheBatchInvalidationEvent(null, keys, null);
        sendClientMessage(name, eventMessage);
    }

    @Override
    protected CacheAddInvalidationListenerCodec.RequestParameters decodeClientMessage(ClientMessage clientMessage) {
        return CacheAddInvalidationListenerCodec.decodeRequest(clientMessage);
    }

    @Override
    protected ClientMessage encodeResponse(Object response) {
        return CacheAddInvalidationListenerCodec.encodeResponse((String) response);
    }

    @Override
    public String getDistributedObjectName() {
        return parameters.name;
    }

    @Override
    public String getMethodName() {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    @Override
    public String getServiceName() {
        return CacheService.SERVICE_NAME;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

}
