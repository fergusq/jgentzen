package org.kaivos.gentzen;

import static java.util.stream.Collectors.toList;
import static org.kaivos.gentzen.Utils.fromEntries;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface HasTypeVars<T> {
    public T withInstance(int instance);
    public T substitute(
        Map<ExpressionType.TypeVar, ExpressionType> substitutions
    );

    public static <K, T extends HasTypeVars<? extends T>> Map<
        K,
        T
    > mapWithInstance(Map<K, T> map, int instance) {
        return map
            .entrySet()
            .stream()
            .map(e ->
                Map.entry(e.getKey(), e.getValue().withInstance(instance))
            )
            .collect(fromEntries());
    }

    public static <T extends HasTypeVars<? extends T>> List<T> listWithInstance(
        Collection<T> list,
        int instance
    ) {
        return list
            .stream()
            .map(i -> i.withInstance(instance))
            .collect(toList());
    }

    public static <T extends HasTypeVars<? extends T>> Stream<T> streamWithInstance(
        Collection<T> list,
        int instance
    ) {
        return list.stream().map(i -> i.withInstance(instance));
    }

    public static <K, T extends HasTypeVars<? extends T>> Map<
        K,
        T
    > mapSubstitute(
        Map<K, T> map,
        Map<ExpressionType.TypeVar, ExpressionType> substitutions
    ) {
        return map
            .entrySet()
            .stream()
            .map(e ->
                Map.entry(e.getKey(), e.getValue().substitute(substitutions))
            )
            .collect(fromEntries());
    }

    public static <T extends HasTypeVars<? extends T>> List<T> listSubstitute(
        Collection<T> list,
        Map<ExpressionType.TypeVar, ExpressionType> substitutions
    ) {
        return list
            .stream()
            .map(i -> i.substitute(substitutions))
            .collect(toList());
    }

    public static <T extends HasTypeVars<? extends T>> Stream<T> streamSubstitute(
        Collection<T> list,
        Map<ExpressionType.TypeVar, ExpressionType> substitutions
    ) {
        return list.stream().map(i -> i.substitute(substitutions));
    }
}
