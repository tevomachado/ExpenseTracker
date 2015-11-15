package com.example.estevam.expensetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    ArrayList<String> itemArrayList;
    AutoCompleteTextView nameIn;
    EditText priceIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ArrayList<String> itemsArray = getItems();
        String[] myList = itemsArray.toArray(new String[itemsArray.size()]);
        nameIn = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        nameIn.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,myList));
        priceIn = (EditText) findViewById(R.id.priceField);
        for(int i = 0; i < myList.length; i++){
            Toast.makeText(this,myList[i],Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;


    }

    public ArrayList<String> getItems(){
        itemArrayList = new ArrayList<String>();
        SqlDbHelper db = new SqlDbHelper(this);
        List<Item> items = db.getAllItems();

        if(items.isEmpty()){
            db.addItem(new Item("Gas"));
            db.addItem(new Item("Grocery"));
            items = db.getAllItems();
        }

        Log.d("Reading: ", "Reading all items..");

        int c = 0;
        for(Item cn : items){
            String log = "Id: " + cn.getItem_No() + " ,Name: " + cn.getItem_name();
            itemArrayList.add(cn.getItem_name());
            Log.d("Name: ", log);
        }
        return itemArrayList;
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

    public void ShowText(View v) {
        String s = nameIn.getText().toString();
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        SqlDbHelper db = new SqlDbHelper(this);
        if (!(itemArrayList.contains(s))) {
            Toast.makeText(this, "contains " + s, Toast.LENGTH_SHORT).show();
            db.addItem(new Item(s));
        }
        Date date = new Date();
        Item i2 = new Item();
        String s1 = nameIn.getText().toString();
        String s2 = priceIn.getText().toString();
        i2.setItem_name(s1);
        i2.setItem_price(Double.parseDouble(s2));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
        String timeStr = format.format(date);
        i2.setItem_date(timeStr);
        db.addItemtoPricetable(i2);
        db.close();
    }

}
