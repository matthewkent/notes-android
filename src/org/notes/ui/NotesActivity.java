package org.notes.ui;

import org.notes.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class NotesActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_notes);
	}
}
