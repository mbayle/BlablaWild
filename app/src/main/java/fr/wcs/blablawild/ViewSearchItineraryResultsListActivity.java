package fr.wcs.blablawild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ViewSearchItineraryResultsListActivity extends AppCompatActivity {

    ListView mListViewResults;
    ArrayList<TripResultModel> results;

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
        ArrayList<TripResultModel> results = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-hh:mm");

        try {
            results.add(new TripResultModel("Bruce", sdf.parse("21/02/2017-15:30"), 15));
            results.add(new TripResultModel("Clark", sdf.parse("21/02/2017-16:00"), 20));
            results.add(new TripResultModel("Bary", sdf.parse("21/02/2017-16:30"), 16));
            results.add(new TripResultModel("Lex", sdf.parse("21/02/2017-17:00"), 40));
        } catch (ParseException e) {
        }
        TripResultAdapter adapter = new TripResultAdapter(ViewSearchItineraryResultsListActivity.this, results);

        mListViewResults.setAdapter(adapter);

    }
}
