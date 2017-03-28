package fr.wcs.blablawild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitItineraryActivity extends AppCompatActivity {
    private EditText editTextDeparture;
    private EditText editTextDestination;
    private EditText editTextPrice;
    private EditText editTextDate;
    private EditText editTextHour;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_itinerary);

        editTextDeparture = (EditText) findViewById(R.id.editTextDeparture);
        editTextDestination = (EditText) findViewById(R.id.editTextDestination);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextHour = (EditText) findViewById(R.id.editTextHour);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextDeparture.length() != 0 && editTextDestination.length() != 0 && editTextPrice.length() != 0 && editTextDate.length() != 0) {
                    String driverLastName = "Doctor";
                    String driverFirstName = "Who";
                    int userID = 0;
                    String departure = editTextDeparture.getText().toString();
                    String destination = editTextDestination.getText().toString();
                    String date = editTextDate.getText().toString();
                    String hour = editTextHour.getText().toString();
                    int price = Integer.parseInt(editTextPrice.getText().toString());

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("itinerary");

                    ItineraryModel itineraryModel1 = new ItineraryModel(date, price, departure, destination);
                    myRef.push().setValue(itineraryModel1);
                    finish();
                }
            }
        });
    }
}