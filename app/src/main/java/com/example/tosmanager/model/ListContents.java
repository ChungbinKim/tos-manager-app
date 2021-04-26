package com.example.tosmanager.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ListContents {
    private final HashMap<CharSequence, ListContent> contents = new HashMap<>();
    private ListContent content;

    public ListContents addContent(CharSequence key) {
        content = new ListContent(key);
        contents.put(key, content);
        return this;
    }

    public ListContent getContent(CharSequence key) {
        return contents.get(key);
    }

    public ListContents addItem(CharSequence item) {
        content.addItem(item);
        return this;
    }

    public ListContents addItems(Collection<CharSequence> items) {
        content.addItems(items);
        return this;
    }
}
