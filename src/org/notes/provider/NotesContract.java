package org.notes.provider;

import android.net.Uri;

public class NotesContract {

    public static final String CONTENT_AUTHORITY = "org.notes";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static Uri buildListsUri() {
    	return BASE_CONTENT_URI;
    }

    public static Uri buildListUri(String name) {
        return BASE_CONTENT_URI.buildUpon().appendPath(name).build();
    }

    public static Uri buildNoteUri(String listName, int noteIndex) {
        return BASE_CONTENT_URI.buildUpon().appendPath(listName).appendPath(String.valueOf(noteIndex)).build();
    }

}
