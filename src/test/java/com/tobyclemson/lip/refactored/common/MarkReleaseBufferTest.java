package com.tobyclemson.lip.refactored.common;

import org.junit.Test;

import static com.tobyclemson.lip.refactored.common.IteratorReader.readerWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MarkReleaseBufferTest {
    @Test
    public void allowsAccessArbitrarilyDeepIntoTheContents() {
        // Given
        MarkReleaseBuffer<String> buffer = new MarkReleaseBuffer<>(
                readerWith("one", "two", "three", "four", "five", "six", "seven"));
        String expectedOne = "one";
        String expectedSeven = "seven";

        // When
        String actualOne = buffer.peek(0);
        String actualSeven = buffer.peek(6);

        // Then
        assertThat(actualOne, is(expectedOne));
        assertThat(actualSeven, is(expectedSeven));
    }

    @Test
    public void allowsBufferToBeMarkedAdvancedAndReleasedBackToMark() {
        // Given
        MarkReleaseBuffer<String> buffer = new MarkReleaseBuffer<>(
                readerWith("one", "two", "three", "four", "five", "six", "seven"));
        buffer.advance();
        buffer.advance();
        buffer.mark();
        buffer.advance();

        // When
        String afterMark = buffer.peek(0);
        buffer.release();
        String afterRelease = buffer.peek(0);

        // Then
        assertThat(afterMark, is("four"));
        assertThat(afterRelease, is("three"));
    }

    @Test
    public void allowsBufferToBeMarkedAndReleasedMultipleTimes() {
        // Given
        MarkReleaseBuffer<String> buffer = new MarkReleaseBuffer<>(
                readerWith("one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));
        buffer.advance();
        buffer.advance();
        buffer.mark();
        buffer.advance();
        buffer.release();
        buffer.advance();
        buffer.mark();
        buffer.advance();

        // When
        String afterMark = buffer.peek(0);
        buffer.release();
        String afterRelease = buffer.peek(0);

        // Then
        assertThat(afterMark, is("five"));
        assertThat(afterRelease, is("four"));
    }

    @Test
    public void allowsBufferToBeMarkedAndReleasedWithNestedMarks() {
        // Given
        MarkReleaseBuffer<String> buffer = new MarkReleaseBuffer<>(
                readerWith("one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));
        buffer.advance();
        buffer.advance();
        buffer.mark();
        buffer.advance();
        buffer.advance();
        buffer.mark();
        buffer.advance();
        buffer.advance();

        // When
        String afterLatestMark = buffer.peek(0);
        buffer.release();
        String afterFirstRelease = buffer.peek(0);
        buffer.release();
        String afterSecondRelease = buffer.peek(0);

        // Then
        assertThat(afterLatestMark, is("seven"));
        assertThat(afterFirstRelease, is("five"));
        assertThat(afterSecondRelease, is("three"));
    }
}