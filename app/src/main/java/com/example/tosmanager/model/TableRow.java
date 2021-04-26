package com.example.tosmanager.model;

public abstract class TableRow {
    private final CharSequence key;

    abstract public CharSequence toCharSequence();

    TableRow(CharSequence key) {
        this.key = key;
    }

    public CharSequence getKey() {
        return key;
    }
}

class TableStringRow extends TableRow {
    private final CharSequence value;

    TableStringRow(CharSequence key, CharSequence value) {
        super(key);
        this.value = value;
    }

    @Override
    public CharSequence toCharSequence() {
        return value;
    }
}

class TableBooleanRow extends TableRow {
    private final Boolean value;

    TableBooleanRow(CharSequence key, Boolean value) {
        super(key);
        this.value = value;
    }

    @Override
    public CharSequence toCharSequence() {
        return value.toString();
    }
}
