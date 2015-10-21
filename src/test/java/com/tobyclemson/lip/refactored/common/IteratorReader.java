package com.tobyclemson.lip.refactored.common;

import lombok.AllArgsConstructor;

import java.util.Iterator;

import static org.javafunk.funk.Literals.iteratorFrom;

@AllArgsConstructor
public class IteratorReader<T> implements Reader<T> {
    Iterator<T> iterator;

    @SafeVarargs
    public static <T> Reader<T> readerWith(T... elements) {
        return new IteratorReader<>(iteratorFrom(elements));
    }

    @Override
    public T readNext() {
        return iterator.next();
    }
}
