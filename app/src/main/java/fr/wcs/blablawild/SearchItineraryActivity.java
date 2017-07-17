package fr.wcs.blablawild;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class SearchItineraryActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public final static String EXTRA_REQUEST = "trip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_itinerary);
        final EditText editTextSearchDeparture = (EditText) findViewById(R.id.editTextSearchDeparture);
        final EditText editTextSearchDestination = (EditText) findViewById(R.id.editTextSearchDestination);
        final EditText editTextSearchDate = (EditText) findViewById(R.id.editTextSearchDate);
        final Button button = (Button) findViewById(R.id.buttonButtonSearch);

        editTextSearchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SearchItineraryActivity.this,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                Log.d("SELECTION DATE", "NOUVELLE DATE : dd/mm/yyy" + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                editTextSearchDate.setText(date);
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (editTextSearchDeparture.length() == 0 || editTextSearchDestination.length() == 0){
                    Toast.makeText(SearchItineraryActivity.this, getString(R.string.Toast), Toast.LENGTH_LONG).show(); }
                else{
                    String Departure = editTextSearchDeparture.getText().toString();
                    String Destination = editTextSearchDestination.getText().toString();
                    String Date = editTextSearchDate.getText().toString();

                    SearchRequestModel trip = new SearchRequestModel(Departure, Destination, Date);
                    Intent request = new Intent(SearchItineraryActivity.this, ViewSearchItineraryResultsListActivity.class);
                    request.putExtra(EXTRA_REQUEST, trip);
                    startActivity(request);
                }
            }
        });
    }
}
