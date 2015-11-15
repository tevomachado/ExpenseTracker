package com.example.estevam.expensetracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private int btnnumber;
    String date_selected1;
    String date_selected2;
    static final int DATE_DIALOG_ID = 0;
    ArrayList<String> itemArrayList;
    TextView totalView;
    TextView dataRange;
    ListView lst;
    ArrayAdapter<String> adapter;
    String[] myList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
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

    public void getFromDate(View v) {
        // Toast.makeText(this, "btn1", Toast.LENGTH_LONG).show();

        setBtnnumber(1);
        showDialog(DATE_DIALOG_ID);

    }

    @SuppressWarnings("deprecation")
    public void getToDate(View v) {
        // Toast.makeText(this, "btn1", Toast.LENGTH_LONG).show();
        setBtnnumber(2);
        showDialog(DATE_DIALOG_ID);

    }

    public int getBtnnumber() {
        return btnnumber;
    }

    public void setBtnnumber(int btnnumber) {
        this.btnnumber = btnnumber;
    }
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, cyear, cmonth,
                        cday);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String date_selected = String.valueOf(year) + "-"
                    + String.valueOf(monthOfYear + 1) + "-"
                    + String.valueOf(dayOfMonth);
            if (getBtnnumber() == 1) {
                date_selected1 = date_selected;
                Toast.makeText(ReviewActivity.this,
                        "Selected Date is =" + date_selected + "from button1 ",
                        Toast.LENGTH_SHORT).show();
            }
            if (getBtnnumber() == 2) {
                date_selected2 = date_selected;
                Toast.makeText(ReviewActivity.this,
                        "Selected Date is =" + date_selected + "from button2 ",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void getExpenses(View v) {
        itemArrayList = new ArrayList<String>();
        List<Item> items = null;
        SqlDbHelper db = new SqlDbHelper(this);
        try{
            items = db.getDataForReport(date_selected1, date_selected2);
        }
        catch(Exception e)
        {
            Toast.makeText(this,"Exception", Toast.LENGTH_LONG).show();
        }
        int sl_no = 1;
        dataRange = (TextView)findViewById(R.id.daterange);
        dataRange.setText("From:  " + date_selected1 + "  To:  "
                + date_selected2);

        for (Item cn : items) {
            String data = sl_no + "\t\t" + cn.getItem_name() + "\t\t"
                    + cn.getItem_price();
            itemArrayList.add(data);
            sl_no++;
        }

        double total = db.getTotalForReport(date_selected1, date_selected2);
        totalView = (TextView)findViewById(R.id.totalview);
        totalView.setText("TOTAL:" + "\t\t" + total);
        myList = itemArrayList.toArray(new String[itemArrayList.size()]);
        lst = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, myList);
        lst.setAdapter(adapter);

    }
}
