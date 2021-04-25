package com.example.tosmanager.model;

import java.util.HashMap;

public class TableContent {
    private CharSequence key;
    private HashMap<CharSequence, TableRow> contents;

    public TableContent(CharSequence key) {
        this.key = key;
    }

    public void addRow(TableRow row) {
        contents.put(row.getKey(), row);
    }
}

abstract class TableRow {
    private CharSequence key;

    abstract public CharSequence getValue();

    TableRow(CharSequence key) {
        this.key = key;
    }

    public CharSequence getKey() {
        return key;
    }
}

class TableStringRow extends TableRow {
    private CharSequence value;

    TableStringRow(CharSequence key, CharSequence value) {
        super(key);
        this.value = value;
    }

    @Override
    public CharSequence getValue() {
        return value;
    }
}

class TableBooleanRow extends TableRow {
    private Boolean value;

    TableBooleanRow(CharSequence key, Boolean value) {
        super(key);
        this.value = value;
    }

    @Override
    public CharSequence getValue() {
        return value.toString();
    }
}
