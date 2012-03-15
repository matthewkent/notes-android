package org.notes.ui;

import org.notes.R;
import org.notes.provider.NotesContract;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

public class NotesFragment extends Fragment implements LoaderCallbacks<Cursor> {
	private SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getLoaderManager().initLoader(0, null, this);

        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_item,
                null, new String[] {
                    NotesContract.Notes.BODY
                }, new int[] {
                    android.R.id.text1
                }, 0){
        	public void bindView(View view, Context context, final Cursor cursor) {
        		final int pos = cursor.getPosition();
        		view.findViewById(R.id.delete_note_button).setOnClickListener(new OnClickListener() {
	    			@Override
	    			public void onClick(View v) {
	    				String listName = getActivity().getIntent().getData().getLastPathSegment();
	    				Uri uri = NotesContract.Notes.buildNoteUri(listName, pos);
	    				getActivity().getContentResolver().delete(uri, null, null);
	    			}
	    		});
        		super.bindView(view, context, cursor);
        	};
        };
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notes, container, false);
		ListView list = (ListView) view.findViewById(R.id.notes_list);
		list.setAdapter(mAdapter);
		
        view.findViewById(R.id.add_note_button).setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		TextView input = (TextView) getView().findViewById(R.id.add_note_content);
        		String body = input.getText().toString();
        		ContentValues values = new ContentValues();
        		values.put(NotesContract.Notes.BODY, body);
        		getActivity().getContentResolver().insert(getActivity().getIntent().getData(), values);
        		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        		imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
        		input.setText("");
        	}
        });
        
		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = getActivity().getIntent().getData();
		return new CursorLoader(getActivity().getApplicationContext(), uri, new String[] { NotesContract.Notes._ID, NotesContract.Notes.BODY }, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

}
