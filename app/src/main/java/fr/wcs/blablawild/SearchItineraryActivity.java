package fr.wcs.blablawild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchItineraryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_itinerary);
        final EditText editTextSearchDeparture = (EditText) findViewById(R.id.editTextSearchDeparture);
        final EditText editTextSearchDestination = (EditText) findViewById(R.id.editTextSearchDestination);
        final Button button = (Button) findViewById(R.id.buttonButtonSearch);
        final Intent intent = new Intent(SearchItineraryActivity.this, ViewSearchItineraryResultsListActivity.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String Departure = editTextSearchDeparture.getText().toString();
                final String Destination = editTextSearchDestination.getText().toString();

                if (editTextSearchDeparture.length() == 0 || editTextSearchDestination.length() == 0) {
                    Toast.makeText(SearchItineraryActivity.this, getString(R.string.Toast), Toast.LENGTH_LONG).show();
                }
                else intent.putExtra("abc", Departure);
                intent.putExtra("abcd", Destination);
                startActivity(intent);
            }
        });
    }




}
