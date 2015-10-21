package com.tobyclemson.lip.refactored.common;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;
import static org.junit.Assert.assertThat;

public class CircularQueueTest {
    @Test
    public void allowsElementsToBePeekedUpToTheSizeOfTheQueue() {
        // Given
        CircularQueue<String> queue = new CircularQueue<>(2);
        queue.push("one");
        queue.push("two");

        // When
        String firstElement = queue.peek(0);
        String secondElement = queue.peek(1);

        // Then
        assertThat(listWith(firstElement, secondElement),
                is(listWith("one", "two")));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void doesNotAllowElementsBeyondTheEndOfTheBufferToBePeeked() {
        // Given
        CircularQueue<String> queue = new CircularQueue<>(2);
        queue.push("one");
        queue.push("two");

        // When
        queue.peek(2);

        // Then an exception is thrown
    }

    @Test
    public void allowsElementsToBePoppedFromTheQueue() {
        // Given
        CircularQueue<String> queue = new CircularQueue<>(2);
        queue.push("one");
        queue.push("two");

        // When
        queue.pop();
        String firstElement = queue.peek(0);

        // Then
        assertThat(firstElement, is("two"));
    }

    @Test(expected = IllegalStateException.class)
    public void doesNotAllowElementsToBePoppedWhenTheQueueIsInitiallyEmpty() {
        // Given
        CircularQueue<String> queue = new CircularQueue<>(2);

        // When
        queue.pop();

        // Then an exception is thrown
    }

    @Test(expected = IllegalStateException.class)
    public void doesNotAllowElementsToBePoppedWhenTheQueueIsPopulatedAndThenEmptied() {
        // Given
        CircularQueue<String> queue = new CircularQueue<>(2);
        queue.push("one");
        queue.push("two");
        queue.pop();
        queue.pop();

        // When
        queue.pop();

        // Then an exception is thrown
    }

    @Test(expected = IllegalStateException.class)
    public void doesNotAllowElementsToBePushedWhenTheQueueIsFull() {
        // Given
        CircularQueue<String> queue = new CircularQueue<>(2);
        queue.push("one");
        queue.push("two");

        // When
        queue.push("three");

        // Then an exception is thrown
    }

    @Test
    public void allowsElementsToBePushedWhenTheQueueIsFilledAndSubsequentlySpaceIsMade() {
        // Given
        CircularQueue<String> queue = new CircularQueue<>(2);
        queue.push("one");
        queue.push("two");
        queue.pop();

        // When
        queue.push("three");

        // Then
        assertThat(queue.peek(1), is("three"));
    }
}