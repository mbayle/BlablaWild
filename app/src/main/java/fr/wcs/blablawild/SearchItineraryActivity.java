package fr.wcs.blablawild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchItineraryActivity extends AppCompatActivity {

    public final static String EXTRA_REQUEST = "trip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_itinerary);
        final EditText editTextSearchDeparture = (EditText) findViewById(R.id.editTextSearchDeparture);
        final EditText editTextSearchDestination = (EditText) findViewById(R.id.editTextSearchDestination);
        final EditText editTextSearchDate = (EditText) findViewById(R.id.editTextSearchDate);
        final Button button = (Button) findViewById(R.id.buttonButtonSearch);

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
