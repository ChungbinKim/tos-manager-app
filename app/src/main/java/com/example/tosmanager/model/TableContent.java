package com.example.tosmanager.model;

import java.util.HashMap;

public class TableContent {
    private final CharSequence key;
    private final HashMap<CharSequence, TableRow> rows = new HashMap<>();

    public TableContent(CharSequence key) {
        this.key = key;
    }

    public CharSequence getKey() {
        return key;
    }

    public TableContent addRow(CharSequence key, CharSequence value) {
        rows.put(key, new TableStringRow(key, value));
        return this;
    }

    public TableContent addRow(CharSequence key, boolean value) {
        rows.put(key, new TableBooleanRow(key, value));
        return this;
    }

    public TableRow getRow(CharSequence key) {
        return rows.get(key);
    }
}
