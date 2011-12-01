package org.notes.provider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class NotesList {

	private static final String MANIFEST_FILENAME = "notes-manifest.txt";

	private List<String> mNotes;
	private Context mContext;
	private String mListName;
	
	public NotesList(Context context, String listName) throws IOException {
		mNotes = new ArrayList<String>();
		mContext = context;
		mListName = listName;
	}

	/*
	 * Returns note index
	 */
	public int addNote(String note) {
		mNotes.add(note);
		return mNotes.size();
	}
	
	public void deleteNote(int index) {
		mNotes.remove(index);
	}
	
	public List<String> getNotes() {
		return mNotes;
	}
	
	void load() throws IOException {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(mContext.openFileInput(mListName)));
			String line = null;
			while((line = input.readLine()) != null) {
				mNotes.add(line);
			}
		} finally {
			if(input != null) {
				input.close();
			}
		}
	}

	void save() throws IOException {
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(mContext.openFileOutput(mListName, Context.MODE_PRIVATE)));
			for(String note: mNotes) {
				output.write(note);
				output.newLine();
			}
			output.flush();
		} finally {
			if(output != null) {
				output.close();
			}
		}
	}
	
	static List<String> listAll(Context context) throws IOException {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(context.openFileInput(MANIFEST_FILENAME)));
			List<String> listNames = new ArrayList<String>();
			String line = null;
			while((line = input.readLine()) != null) {
				listNames.add(line);
			}
			return listNames;
		} finally {
			if(input != null) {
				input.close();
			}
		}
	}
}
