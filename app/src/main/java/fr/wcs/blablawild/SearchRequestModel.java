package fr.wcs.blablawild;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wilder on 06/03/17.
 */

public class SearchRequestModel implements Parcelable {

    private String mDeparture;
    private String mDestination;
    private String mDate;

    public SearchRequestModel(String mDeparture, String mDestination, String mDate) {
        this.mDeparture = mDeparture;
        this.mDestination = mDestination;
        this.mDate = mDate;
    }


    protected SearchRequestModel(Parcel in) {
        mDeparture = in.readString();
        mDestination = in.readString();
        mDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDeparture);
        dest.writeString(mDestination);
        dest.writeString(mDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchRequestModel> CREATOR = new Creator<SearchRequestModel>() {
        @Override
        public SearchRequestModel createFromParcel(Parcel in) {
            return new SearchRequestModel(in);
        }

        @Override
        public SearchRequestModel[] newArray(int size) {
            return new SearchRequestModel[size];
        }
    };

    public String getmDeparture() {
        return mDeparture;
    }

    public String getmDestination() {
        return mDestination;
    }

    public String getmDate() {
        return mDate;
    }
}