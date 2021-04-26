package com.example.tosmanager.model;

import java.util.ArrayList;
import java.util.Collection;

public class ListContent {
    private final CharSequence key;
    private final ArrayList<CharSequence> items = new ArrayList<>();

    ListContent(CharSequence key) {
        this.key = key;
    }

    public CharSequence getKey() {
        return key;
    }
    public Iterable<CharSequence> getItems() {
        return items;
    }

    public void addItem(CharSequence item) {
        items.add(item);
    }

    public void addItems(Collection<CharSequence> contents) {
        this.items.addAll(contents);
    }
}
