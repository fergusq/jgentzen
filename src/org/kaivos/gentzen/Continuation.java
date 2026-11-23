package org.kaivos.gentzen;

import org.kaivos.gentzen.value.Value;

public interface Continuation<T extends Value> extends Value {
    public void continueWith(T t);
}
