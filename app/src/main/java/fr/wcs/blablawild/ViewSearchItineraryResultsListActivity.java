package fr.wcs.blablawild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ViewSearchItineraryResultsListActivity extends AppCompatActivity {

    ListView mListViewResults;
    DatabaseReference database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_itinerary_results_list);

        Intent request = getIntent();
        SearchRequestModel result = request.getParcelableExtra(SearchItineraryActivity.EXTRA_REQUEST);
        String mDeparture = (result.getmDeparture());
        String mDestination = (result.getmDestination());
        this.setTitle(mDeparture + " " + mDestination + " ");

        if (result.getmDate().length() ==0) {
           Toast.makeText(ViewSearchItineraryResultsListActivity.this, getString(R.string.NoDate), Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(ViewSearchItineraryResultsListActivity.this,getString(R.string.Date)+" "+result.getmDate(), Toast.LENGTH_LONG).show();
        }

        mListViewResults = (ListView) findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance().getReference("itinerary");
        TripResultAdapter adapter = new TripResultAdapter(database, this, R.layout.trip_item);

        mListViewResults.setAdapter(adapter);

    }
}
