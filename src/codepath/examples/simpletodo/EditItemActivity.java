package codepath.examples.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends Activity {
	int positionOfCurrentEdit;
	
	TextView etItemEntry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		this.positionOfCurrentEdit  = getIntent().getIntExtra("pos", 0);
		String currentEditText 		= getIntent().getStringExtra("etItemEntry");
		
		EditText etEditText = (EditText) findViewById(R.id.etItemEntry);
		etEditText.setText(currentEditText);
		
		int txtPos = currentEditText.length();
		etEditText.setSelection(txtPos);
		
	}
	
	public void updateTodoItem(View v) {
    	EditText etNewItem = (EditText) findViewById(R.id.etItemEntry);
    	
    	
    	Intent data = new Intent();
    	
    	data.putExtra("updateText", etNewItem.getText().toString());
    	data.putExtra("updatePos",this.positionOfCurrentEdit);
    	etNewItem.setText("");
    	setResult(RESULT_OK, data);
    	finish();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
