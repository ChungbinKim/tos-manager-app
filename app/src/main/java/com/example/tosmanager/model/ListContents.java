package com.example.tosmanager.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ListContents {
    private HashMap<CharSequence, ListContent> contents = new HashMap<>();

    public void addItem(ListContent item) {
        contents.put(item.getKey(), item);
    }
}

class ListContent {
    private CharSequence key;
    private ArrayList<CharSequence> contents = new ArrayList<>();

    ListContent(CharSequence key) {
        this.key = key;
    }

    public CharSequence getKey() {
        return key;
    }
    public Iterable<CharSequence> getContents() {
        return contents;
    }

    public void addContent(CharSequence content) {
        contents.add(content);
    }

    public void addContents(Collection<CharSequence> contents) {
        this.contents.addAll(contents);
    }
}