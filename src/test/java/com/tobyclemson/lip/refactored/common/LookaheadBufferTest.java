package com.tobyclemson.lip.refactored.common;

import org.junit.Test;

import static com.tobyclemson.lip.refactored.common.IteratorReader.readerWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LookaheadBufferTest {
    @Test
    public void providesOneElementOfLookahead() {
        // Given
        LookaheadBuffer<Integer> lookaheadBuffer = new LookaheadBuffer<>(readerWith(1, 2, 3));
        Integer expected = 1;

        // When
        Integer actual = lookaheadBuffer.lookahead();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void allowsLookaheadToBeAdvanced() {
        // Given
        LookaheadBuffer<Integer> lookaheadBuffer = new LookaheadBuffer<>(readerWith(1, 2, 3));
        Integer expected = 3;

        // When
        lookaheadBuffer.advance();
        lookaheadBuffer.advance();
        Integer actual = lookaheadBuffer.lookahead();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void providesKElementsOfLookahead() {
        // Given
        LookaheadBuffer<Integer> lookaheadBuffer = new LookaheadBuffer<>(3, readerWith(1, 2, 3, 4, 5));
        Integer expected = 3;

        // When
        Integer actual = lookaheadBuffer.lookahead(3);

        // Then
        assertThat(actual, is(expected));
    }
}