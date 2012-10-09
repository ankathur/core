/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.context;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.serialization.spi.helpers.SerializableContextual;
import org.jboss.weld.util.ForwardingContext;

/**
 * Forwarding context that wraps passivating contexts and wraps non-serializable {@link Contextual} instances within
 * {@link SerializableContextual}.
 *
 * @author Jozef Hartinger
 *
 */
public class PassivatingContextWrapper extends ForwardingContext {

    private final Context context;
    private final ContextualStore store;

    public PassivatingContextWrapper(Context context, ContextualStore store) {
        this.context = context;
        this.store = store;
    }

    @Override
    public <T> T get(Contextual<T> contextual) {
        contextual = store.getSerializableContextual(contextual);
        return context.get(contextual);
    }

    @Override
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        contextual = store.getSerializableContextual(contextual);
        return context.get(contextual, creationalContext);
    }

    @Override
    protected Context delegate() {
        return context;
    }
}
