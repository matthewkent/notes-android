package org.notes.provider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class NotesOpenHelper {

	protected Context mContext;
	protected Map<String, NotesList> mLists;

	public NotesOpenHelper(Context context) {
		mContext = context;
		mLists = null;
	}
	
	public NotesList getList(String name) throws IOException {
		return getLists().get(name);
	}

	public void deleteList(String name) throws IOException {
		getLists().remove(name);
		mContext.deleteFile(name);
	}

	public NotesList createList(String name) throws IOException {
		NotesList list = new NotesList(mContext, name);
		// save the empty list to write the file to disk
		list.save();
		getLists().put(name, list);
		return list;
	}
	
	public Map<String, NotesList> getLists() throws IOException {
		if(mLists == null) {
			mLists = new HashMap<String, NotesList>();
			for(String listName: mContext.fileList()) {
				NotesList list = new NotesList(mContext, listName);
				list.load();
				mLists.put(listName, list);
			}
		}
		return mLists;
	}
}
