package codepath.examples.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	
	private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        readItems();
        
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        items.add("Second Item");
        setupListViewListener();
    }
    
    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> aView, View item, int pos, long id) {
    			items.remove(pos);
    			itemsAdapter.notifyDataSetInvalidated();
    			saveItems();
    			return true;
    		}
    	});
    	lvItems.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> aView, View item, int pos, long id) {
    			Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
    			
    			i.putExtra("etItemEntry", items.get(pos).toString());
    			i.putExtra("pos", pos);
    			startActivityForResult(i, REQUEST_CODE);
    		}
    	});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		int pos = data.getExtras().getInt("updatePos");
    		String updatedText = data.getExtras().getString("updateText"); 
    		items.set(pos, updatedText);
    		itemsAdapter.notifyDataSetChanged();
    		Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
    		saveItems();
    	}
    }
    
    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e) {
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    private void saveItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, items);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    public void addTodoItem(View v) {
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
}
