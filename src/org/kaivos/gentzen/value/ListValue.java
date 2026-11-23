package org.kaivos.gentzen.value;

import java.util.ArrayList;
import java.util.Collection;

public class ListValue<T extends Value> {
    private ArrayList<T> list = new ArrayList<>();

    public ListValue<T> add(T value) {
        var newList = new ListValue<T>();
        newList.list.addAll(this.list);
        newList.list.add(value);
        return newList;
    }

    public<U extends T> ListValue<T> addAll(Collection<U> value) {
        var newList = new ListValue<T>();
        newList.list.addAll(this.list);
        newList.list.addAll(value);
        return newList;
    }
}
