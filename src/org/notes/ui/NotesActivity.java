package org.notes.ui;

import org.notes.R;
import org.notes.provider.NotesContract;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class NotesActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_notes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_option_delete_list: {
			new AlertDialog.Builder(this)
				.setMessage(R.string.are_you_sure_you_want_to_delete)
				.setCancelable(false)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						getContentResolver().delete(getIntent().getData(), null, null);
						dialog.dismiss();
						Uri uri = NotesContract.Lists.buildListsUri();
						Intent intent = new Intent(Intent.ACTION_VIEW, uri, getApplicationContext(), MainActivity.class);
						startActivity(intent);
					}
				})
				.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
		}
		default: {
			return super.onOptionsItemSelected(item);
		}
		}
	}
}
