package org.kaivos.gentzen.value;

public record TupleValue<T extends Value, U extends Value>(T left, U right) implements Value {}
