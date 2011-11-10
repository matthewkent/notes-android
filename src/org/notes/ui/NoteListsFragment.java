package org.notes.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class NoteListsFragment extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{"One", "Two"}));
	}
}
