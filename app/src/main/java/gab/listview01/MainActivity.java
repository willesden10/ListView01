package gab.listview01;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

/*
* An Adapter object acts as a bridge between an AdapterView and the underlying data for that view.
* The Adapter provides access to the data items.
* The Adapter is also responsible for making a View for each item in the data set.
* */

public class MainActivity extends Activity {
    ListView mListView;
    ArrayList<String> mArrayList;
    ArrayAdapter<String> mArrayAdapter;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Suppress the soft-keyboard until the user actually touches the editText View.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //when the app is created for the first time mArrayList is a new empty array.
        //when it's recreated the previous array is recovered from saveInstanceState.
        mArrayList = savedInstanceState == null?
                new ArrayList<String>()
                :savedInstanceState.getStringArrayList("ArrayList");

        //A concrete BaseAdapter that is backed by an array of arbitrary objects.
        // By default this class expects that the provided resource id references a single TextView.
        // If you want to use a more complex layout, use the constructors that also takes a field id.
        // That field id should reference a TextView in the larger layout resource.
        mArrayAdapter = new ArrayAdapter<>(this,R.layout.one_line_item,mArrayList);

        //Setting the adapter for the ListView
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mArrayAdapter);

        //when an item of the array list is clicked a toast appear showing the text that the item contains.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //parent: The AdapterView where the click happened.
            //view: The view within the AdapterView that was clicked (this will be a view provided by the adapter)
            //position: The position of the view in the adapter.
            //id: The row id of the item that was clicked. check setId() and getId().
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //when an item from the ListView is long clicked it is deleted from the ListView
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mArrayAdapter.remove(((TextView)view).getText().toString());
                return true;
            }
        });

        //Set a special listener to be called when an action is performed on the text view.
        //This will be called when the enter key is pressed, or when an action supplied to the IME is selected by the user.
        //Setting this means that the normal hard key event will not insert a newline into the text view, even if it is multi-line;
        //holding down the ALT modifier will, however, allow the user to insert a newline character.
        mEditText = (EditText) findViewById(R.id.edittext);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            //v: The view that was clicked.
            //actionId: Identifier of the action. This will be either the identifier you supplied, or EditorInfo.IME_NULL if being called due to the enter key being pressed.
            //event: If triggered by an enter key, this is the event; otherwise, this is null.
            //Returns: Return true if you have consumed the action, else false.
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT)
                    addToList();
                return true;
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
    }

    public void addToList(){
        mArrayAdapter.add(mEditText.getText().toString());
        mEditText.getText().clear();

        //Hide the keyboard afted adding the item to the ListView
        //Central system API to the overall input method framework (IMF) architecture,
        //which arbitrates interaction between applications and the current input method
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("ArrayList",mArrayList);
    }
}
