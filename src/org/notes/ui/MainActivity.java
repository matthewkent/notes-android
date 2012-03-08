package org.notes.ui;

import org.notes.R;
import org.notes.provider.NotesContract;

import android.app.Dialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private static final int DIALOG_NEW_LIST = 123;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.menu_option_new_list: {
    		showDialog(DIALOG_NEW_LIST);
    	}
    	case R.id.menu_option_settings: {
    		
    	}
    	default: {
    		return super.onOptionsItemSelected(item);
    	}
    	}
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	switch(id) {
    	case DIALOG_NEW_LIST: {
    		final Dialog dialog = new Dialog(this);
    		dialog.setContentView(R.layout.dialog_new_list);
    		dialog.setTitle(R.string.new_list);
    		
    		dialog.findViewById(R.id.new_list_create).setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				String name = ((TextView) dialog.findViewById(R.id.list_name)).getText().toString().trim();
            		ContentValues values = new ContentValues();
            		values.put(NotesContract.Lists.NAME, name);
            		Uri uri = NotesContract.Lists.buildListsUri();
            		getContentResolver().insert(uri, values);
            		dialog.dismiss();
    			}
    		});
    		
    		dialog.findViewById(R.id.new_list_cancel).setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				dialog.cancel();
    			}
    		});
    		return dialog;
    	}
    	}
    	return null;
    }
}