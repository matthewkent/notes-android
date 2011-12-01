package org.notes.provider;

import java.io.IOException;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class NotesProvider extends ContentProvider {

	private static final String TAG = "NotesProvider";

	protected NotesOpenHelper mOpenHelper;
	
	@Override
	public boolean onCreate() {
		mOpenHelper = new NotesOpenHelper(getContext());
		return true;
	}
	
	static interface Routes {
		public static final int LISTS = 1;
		public static final int LIST = 2;
		public static final int NOTE = 3;
	}
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(NotesContract.CONTENT_AUTHORITY, NotesContract.BASE_CONTENT_URI.toString(), Routes.LISTS);
        matcher.addURI(NotesContract.CONTENT_AUTHORITY, NotesContract.BASE_CONTENT_URI + "/*", Routes.LIST);
        matcher.addURI(NotesContract.CONTENT_AUTHORITY, NotesContract.BASE_CONTENT_URI + "/*/%", Routes.NOTE);
        return matcher;
    }

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int route = sUriMatcher.match(uri);

		try {
			switch(route) {
			case Routes.LISTS: {
				String[] lists = mOpenHelper.getLists().keySet().toArray(new String[]{});
				MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "name"});
				for(int i = 0; i < lists.length; i++) {
					cursor.addRow(new Object[]{i, lists[i]});
				}
				return cursor;
			}
			case Routes.LIST: {
				String name = uri.getPathSegments().get(0);
				List<String> notes = mOpenHelper.getList(name).getNotes();
				MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "body"});
				for(int i = 0; i < notes.size(); i++) {
					cursor.addRow(new Object[]{i, notes.get(i)});
				}
				return cursor;
			}
			default: {
				throw new UnsupportedOperationException("Unknown URI: " + uri);
			}
			}
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		}
		return null;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int route = sUriMatcher.match(uri);
		
		try {
			switch(route) {
			case Routes.LISTS: {
				String name = values.getAsString("name");
				mOpenHelper.createList(name);
				return NotesContract.buildListUri(name);
			}
			case Routes.LIST: {
				String name = uri.getPathSegments().get(0);
				int index = mOpenHelper.getList(name).addNote(values.getAsString("body"));
				return NotesContract.buildNoteUri(name, index);
			}
			default: {
				throw new UnsupportedOperationException("Unknown URI: " + uri);
			}
			}
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int route = sUriMatcher.match(uri);
		
		try {
			switch(route) {
			case Routes.LIST: {
				String name = uri.getPathSegments().get(0);
				mOpenHelper.deleteList(name);
				return 1;
			}
			case Routes.NOTE: {
				String name = uri.getPathSegments().get(0);
				int noteIndex = Integer.parseInt(uri.getPathSegments().get(1));
				mOpenHelper.getList(name).deleteNote(noteIndex);
				return 1;
			}
			default: {
				throw new UnsupportedOperationException("Unknown URI: " + uri);
			}
			}
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException("Update not supported");
	}

}
