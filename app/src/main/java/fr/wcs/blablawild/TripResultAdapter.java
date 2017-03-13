package fr.wcs.blablawild;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static fr.wcs.blablawild.R.layout.trip_item;

/**
 * Created by wilder on 08/03/17.
 */

public class TripResultAdapter extends BaseAdapter {

    private ArrayList<TripResultModel> result = new ArrayList<>();
    private Context mContext;

    public TripResultAdapter (Context context, ArrayList<TripResultModel> result) {
        this.mContext = context;
        this.result  = result;
    }

    public int getCount() {
        return result.size();
    };

    public Object getItem(int position) {
        return result.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(trip_item, parent, false);
        }
        TripResultModel currentIntem = (TripResultModel) getItem(position);

        TextView textView = (TextView)
                convertView.findViewById(R.id.textViewDriverFirstName);
        TextView textViewDate = (TextView)
                convertView.findViewById((R.id.textViewDate));
        TextView textViewPrice = (TextView)
                convertView.findViewById(R.id.textViewPrice);

        textView.setText(result.get(position).getmDriverFirstName());
        textViewDate.setText(result.get(position).getmDate().toString());
        textViewPrice.setText(String.valueOf(result.get(position).getmPrice()));
        return convertView;
    }
}

