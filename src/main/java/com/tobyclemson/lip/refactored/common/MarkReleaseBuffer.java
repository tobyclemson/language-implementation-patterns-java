package com.tobyclemson.lip.refactored.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.javafunk.funk.Eagerly;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarkReleaseBuffer<T> {
    Reader<T> reader;
    List<T> contents;
    List<Integer> marks;
    @NonFinal Integer cursor;

    public MarkReleaseBuffer(Reader<T> reader) {
        this.reader = reader;
        this.contents = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.cursor = 0;
        sync(0);
    }

    public T peek(Integer offset) {
        sync(offset);
        return elementAt(indexOf(offset));
    }

    public T advance() {
        T element = currentElement();
        advanceCursor();
        return element;
    }

    private void advanceCursor() {
        incrementCursor();
        resetCursorIfPossible();
        sync(0);
    }

    public void mark() {
        addMark();
    }

    public void release() {
        Integer mark = latestMark();
        removeMark();
        seek(mark);
    }

    public boolean isMarked() {
        return marks.size() > 0;
    }

    private void seek(Integer index) {
        cursor = index;
    }

    private void sync(Integer offset) {
        if (!elementAvailableFor(offset)) {
            fill(elementsNeededFor(offset));
        }
    }

    private void fill(Integer count) {
        Eagerly.times(count, i -> contents.add(reader.readNext()));
    }

    private T currentElement() {
        return elementAt(cursor);
    }

    private T elementAt(Integer cursor) {
        return contents.get(cursor);
    }

    private boolean elementAvailableFor(Integer offset) {
        return indexOf(offset) <= highestAvailableIndex();
    }

    private Integer elementsNeededFor(Integer offset) {
        return indexOf(offset) - highestAvailableIndex();
    }

    private void incrementCursor() {
        cursor = cursor + 1;
    }

    private void resetCursorIfPossible() {
        if (cursor == contents.size() && !isMarked()) {
            cursor = 0;
            contents.clear();
        }
    }

    private Integer highestAvailableIndex() {
        return contents.size() - 1;
    }

    private Integer indexOf(Integer offset) {
        return cursor + offset;
    }

    private Integer latestMark() {
        return marks.get(marks.size() - 1);
    }

    private void addMark() {
        marks.add(cursor);
    }

    private void removeMark() {
        marks.remove(marks.size() - 1);
    }
}
