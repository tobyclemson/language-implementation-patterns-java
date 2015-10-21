package com.tobyclemson.lip.refactored.common;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.javafunk.funk.Eagerly;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CircularQueue<T> {
    Integer size;
    @NonFinal Integer start;
    @NonFinal Integer end;
    @NonFinal Operation lastOperation;
    List<T> buffer;

    public CircularQueue(Integer size) {
        this.size = size;
        this.start = 0;
        this.end = 0;
        this.lastOperation = Operation.Pop;
        this.buffer = new ArrayList<>(size);
        Eagerly.times(size, i -> buffer.add(null));
    }

    public T peek(Integer offset) {
        validate(offset);
        return buffer.get(indexOf(offset));
    }

    public T pop() {
        assertNotEmpty();
        T element = buffer.get(start);
        buffer.set(start, null);
        start = (start + 1) % size;
        lastOperation = Operation.Pop;
        return element;
    }

    public void push(T element) {
        assertNotFull();
        buffer.set(end, element);
        end = (end + 1) % size;
        lastOperation = Operation.Push;
    }

    private void assertNotFull() {
        if (isFull()) {
            throw new IllegalStateException("Queue is full but push was attempted.");
        }
    }

    private void assertNotEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty but pop was attempted.");
        }
    }

    private boolean isFull() {
        return start.equals(end) && lastOperation.equals(Operation.Push);
    }

    private boolean isEmpty() {
        return start.equals(end) && lastOperation.equals(Operation.Pop);
    }

    private Integer indexOf(Integer offset) {
        return (start + offset) % size;
    }

    private void validate(Integer offset) {
        if (offset > (size - 1)) {
            throw new IndexOutOfBoundsException(
                    format("Buffer has a maximum size of %d but an offset of %d was provided.", size, offset));
        };
    }

    private enum Operation {
        Push, Pop;
    }
}
