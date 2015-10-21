package com.tobyclemson.lip.refactored.pattern4.phrases;

import com.tobyclemson.lip.refactored.common.Phrase;
import com.tobyclemson.lip.refactored.common.PhraseHandler;
import com.tobyclemson.lip.refactored.common.PhraseSelector;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DelegatingPhrase implements Phrase {
    @Delegate PhraseSelector selector;
    @Delegate PhraseHandler handler;
}
