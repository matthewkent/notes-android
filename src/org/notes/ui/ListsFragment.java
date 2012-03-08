package org.notes.ui;

import org.notes.provider.NotesContract;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

public class ListsFragment extends ListFragment implements LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getLoaderManager().initLoader(0, null, this);

        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
                null, new String[] {
                    NotesContract.Lists.NAME
                }, new int[] {
                    android.R.id.text1
                }, 0);//CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(mAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cursor = (Cursor) mAdapter.getItem(position);
		String name = cursor.getString(cursor.getColumnIndex(NotesContract.Lists.NAME));
		Uri uri = NotesContract.Lists.buildListUri(name);

		Intent intent = new Intent(Intent.ACTION_VIEW, uri, getActivity().getApplicationContext(), NotesActivity.class);
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = NotesContract.Lists.buildListsUri();
		return new CursorLoader(getActivity().getApplicationContext(), uri, new String[] { NotesContract.Lists._ID, NotesContract.Lists.NAME }, null, null, null);
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
