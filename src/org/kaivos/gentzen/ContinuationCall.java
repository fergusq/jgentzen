package org.kaivos.gentzen;

import org.kaivos.gentzen.value.Value;

public record ContinuationCall<T extends Value>(Continuation<T> continuation, T value) {}
