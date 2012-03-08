package org.notes.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class NotesContract {

    public static final String CONTENT_AUTHORITY = "org.notes";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class Lists implements BaseColumns, ListsColumns {
    	public static final String PATH = "lists";
    	public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
	    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.notes.list";

    	public static Uri buildListsUri() {
	    	return CONTENT_URI;
	    }
	
	    public static Uri buildListUri(String name) {
	        return CONTENT_URI.buildUpon().appendPath(name).build();
	    }
    }

    interface ListsColumns {
    	String NAME = "name";
    }
    
    public static class Notes implements BaseColumns, NotesColumns {
    	public static final String PATH = "notes";
    	public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
	    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.notes.note";

	    public static Uri buildNoteUri(String listName, int noteIndex) {
	        return CONTENT_URI.buildUpon().appendPath(listName).appendPath(String.valueOf(noteIndex)).build();
	    }
    }

    interface NotesColumns {
    	String BODY = "body";
    }

}
