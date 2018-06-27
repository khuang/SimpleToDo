package codepath.demos.helloworlddemo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends Activity {


	//declaring stateful objects here; these will be null before onCreate is called
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;

	//obtain a reference to the ListView created with the layout
	ListView lvItems;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		readItems();

		lvItems = (ListView) findViewById(R.id.lvItems);
		//initialize the adapter using the items list
		itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
		//wire the adapter to the view
		lvItems.setAdapter(itemsAdapter);

		//add mock items
		items.add("First Item");
		items.add("Finish app!");

		setupListViewListener();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/*Read Text Field and Add Item on Button Press*/
	public void onAddItem(View v){

		EditText editText = (EditText) findViewById(R.id.editText);
		String itemText = editText.getText().toString();
		itemsAdapter.add(itemText);
		editText.setText("");
		writeItems();

		Toast.makeText(getApplicationContext(), "Item Added to List", Toast.LENGTH_SHORT).show();
	}

	private void setupListViewListener(){
		Log.i("MainAction","Setting up listener on list view");
		lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
				Log.i("MainAction","Item removed from list: " + position);
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});

	}

	private File getDataFile(){
		return new File(getFilesDir(), "todo.txt");

	}

	private void readItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }

    }

    private void writeItems(){
		try {
			FileUtils.writeLines(getDataFile(),items);
		} catch (IOException e) {
			Log.e("MainActivity", "Error writing items",e);
		}

	}

}
