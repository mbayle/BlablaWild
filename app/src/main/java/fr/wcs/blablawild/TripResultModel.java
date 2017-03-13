package fr.wcs.blablawild;

import java.util.Date;

/**
 * Created by wilder on 08/03/17.
 */

public class TripResultModel {
    private String mDriverFirstName;
    private Date mDate;
    private int mPrice;

    public TripResultModel(String mDriverFirstName, Date mDate, int mPrice) {
        this.mDriverFirstName = mDriverFirstName;
        this.mDate = mDate;
        this.mPrice = mPrice;
    }

    public void setmDriverFirstName(String mDriverFirstName) {
        this.mDriverFirstName = mDriverFirstName;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getmDriverFirstName() {
        return mDriverFirstName;
    }

    public Date getmDate() {
        return mDate;
    }

    public int getmPrice() {
        return mPrice;
    }

}
