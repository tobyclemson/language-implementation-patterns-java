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
    public void providesManyElementsOfLookahead() {
        // Given
        LookaheadBuffer<Integer> lookaheadBuffer = new LookaheadBuffer<>(readerWith(1, 2, 3, 4, 5));
        Integer expected = 3;

        // When
        Integer actual = lookaheadBuffer.lookahead(3);

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
    public void allowsLookaheadToBeMarkedAndReleased() {
        // Given
        LookaheadBuffer<Integer> lookaheadBuffer = new LookaheadBuffer<>(readerWith(1, 2, 3, 4));
        lookaheadBuffer.advance();
        lookaheadBuffer.mark();
        lookaheadBuffer.advance();
        lookaheadBuffer.advance();

        // When
        lookaheadBuffer.release();
        Integer lookahead = lookaheadBuffer.lookahead(2);

        // Then
        assertThat(lookahead, is(3));
    }

    @Test
    public void returnsTrueForIsMarkedWhenMarkExists() {
        // Given
        LookaheadBuffer<Integer> lookaheadBuffer = new LookaheadBuffer<>(readerWith(1, 2, 3, 4));
        lookaheadBuffer.advance();
        lookaheadBuffer.mark();

        // When
        boolean marked = lookaheadBuffer.isMarked();

        // Then
        assertThat(marked, is(true));
    }

    @Test
    public void returnsFalseForIsMarkedIfNeverMarked() {
        // Given
        LookaheadBuffer<Integer> lookaheadBuffer = new LookaheadBuffer<>(readerWith(1, 2, 3, 4));
        lookaheadBuffer.advance();

        // When
        boolean marked = lookaheadBuffer.isMarked();

        // Then
        assertThat(marked, is(false));
    }

    @Test
    public void returnsFalseForIsMarkedIfMarkedAndReleased() {
        // Given
        LookaheadBuffer<Integer> lookaheadBuffer = new LookaheadBuffer<>(readerWith(1, 2, 3, 4));
        lookaheadBuffer.advance();
        lookaheadBuffer.mark();
        lookaheadBuffer.advance();
        lookaheadBuffer.release();

        // When
        boolean marked = lookaheadBuffer.isMarked();

        // Then
        assertThat(marked, is(false));
    }
}