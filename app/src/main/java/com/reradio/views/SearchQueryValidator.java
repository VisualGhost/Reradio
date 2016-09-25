package com.reradio.views;

public class SearchQueryValidator {

    public static boolean isValidSearch(String searchQuery) {
        return searchQuery != null && searchQuery.trim().length() > 1;
    }

}
