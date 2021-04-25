package com.example.tosmanager.model;

import android.util.Pair;

import java.util.ArrayList;

public class SearchResultItem {
    private CharSequence serviceName;
    private ArrayList<Pair<Integer, Integer>> highlightRanges = new ArrayList<>();

    private SearchResultItem() {}

    public static class Builder {
        private SearchResultItem obj;

        public Builder() {
            obj = new SearchResultItem();
        }

        public SearchResultItem create() {
            return obj;
        }

        public Builder serviceName(CharSequence serviceName) {
            obj.serviceName = serviceName;
            return this;
        }
        public Builder addHighlightRange(int start, int end) {
            obj.highlightRanges.add(new Pair<>(start, end));
            return this;
        }
    }

    public CharSequence getServiceName() {
        return serviceName;
    }
    public Iterable<Pair<Integer, Integer>> getHighlightRanges() {
        return highlightRanges;
    }
}
