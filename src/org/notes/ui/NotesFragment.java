package org.notes.ui;

import org.notes.R;

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
import android.view.ViewGroup;
import android.widget.ListView;

public class NotesFragment extends Fragment implements LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
                null, new String[] {
                    "note"
                }, new int[] {
                    android.R.id.text1
                }, 0);//CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notes, container, false);
		ListView list = (ListView) view.findViewById(R.id.notes);
		list.setAdapter(mAdapter);
		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = getActivity().getIntent().getData();
		return new CursorLoader(getActivity().getApplicationContext(), uri, new String[] { "_id", "note" }, null, null, null);
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
