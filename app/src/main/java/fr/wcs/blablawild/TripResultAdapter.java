package fr.wcs.blablawild;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.Query;

import java.util.ArrayList;

import static fr.wcs.blablawild.R.layout.trip_item;

/**
 * Created by wilder on 08/03/17.
 */

public class TripResultAdapter extends FirebaseListAdapter<ItineraryModel>{


    public TripResultAdapter(Query ref, Activity activity, int layout) {
        super(ref, ItineraryModel.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param model An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, ItineraryModel model) {
        // Map a Chat object to an entry in our listview
        TextView textViewDepart = (TextView) view.findViewById(R.id.textViewDate);
        TextView textViewName = (TextView) view.findViewById(R.id.textViewDriverFirstName);
        TextView textViewPrice = (TextView) view.findViewById(R.id.textViewPrice);

        textViewDepart.setText(String.valueOf(model.getDeparture() + "->" + model.getDestination()));
        textViewName.setText(String.valueOf(model.getDriverFirstName()));
        textViewPrice.setText(String.valueOf(model.getPrice()));
    }
}

