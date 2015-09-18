package gab.listview01;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;

/*
* An Adapter object acts as a bridge between an AdapterView and the underlying data for that view.
* The Adapter provides access to the data items.
* The Adapter is also responsible for making a View for each item in the data set.
* */

public class MainActivity extends Activity {
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //A concrete BaseAdapter that is backed by an array of arbitrary objects.
        // By default this class expects that the provided resource id references a single TextView.
        // If you want to use a more complex layout, use the constructors that also takes a field id.
        // That field id should reference a TextView in the larger layout resource.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.one_line_item,Arrays.asList(Utilities.mockData));

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(arrayAdapter);

        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Line: "+ (position+1) +" selected", Toast.LENGTH_SHORT).show();
            }
        });
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
}
