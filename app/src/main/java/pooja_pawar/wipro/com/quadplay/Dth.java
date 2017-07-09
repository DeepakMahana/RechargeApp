package pooja_pawar.wipro.com.quadplay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Dth extends AppCompatActivity {

    EditText dthNo,amount;
    Spinner spinnerCompany;
    long mobile;
    DbHandlersUsers db;
    TimeToDate dateObj;

    SessionManager sessionManager;
    Bundle bundle;
    Button paySubmit;
    String dthN,amt,dateD,timeD;

    String provider="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dth);

        dthNo = (EditText)findViewById(R.id.dthNumber);
        amount = (EditText)findViewById(R.id.amount);
        paySubmit = (Button)findViewById(R.id.buttonDone);

        dateObj = new TimeToDate(this);

        dateD = dateObj.getDate();
        timeD = dateObj.getTime();

        //Getting the current date and time from TimeToDate Class,
        db = new DbHandlersUsers(this);
        mobile = sessionManager.getMobile();

        // Check if Payment Card is Added by the
        if(!db.CardDetailsUser(mobile) == true) {
            Intent i = new Intent(getApplicationContext(), AddCard.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();

        }
        else {
//            String[] values = db.getUserCards(mobile);
//            createListOfCards(values);
            //Handling Spinner events
            spinnerCompany = (Spinner) findViewById(R.id.spinnerCompanyName);

            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.dthproviders, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCompany.setAdapter(adapter1);

            spinnerCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Value for provider variable is obtained here
                    provider = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            paySubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dthN = dthNo.getText().toString();
                    amt = amount.getText().toString();

                    mobile = sessionManager.getMobile();
                    // Convert cardN to Long
                    long dth = Long.parseLong(dthN);
                    int a = Integer.parseInt(amt);

                    if (dthN.equals("") || provider.equals("") || amt.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter All The Details", Toast.LENGTH_SHORT).show();
                    } else if (dthN.length() != 11) {
                        Toast.makeText(getApplicationContext(), "Enter 11 digit DTH Number", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);

//                    //Inserting DTH Subscriptions DETAILS INTO DB
//                    long id = db.addDth(mobile , dth, a, provider , dateD, timeD, cardno);
//
//                    if(id == 1) {
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(i);
//                        finish();
//                    }
                    }
                }
            });
        }
    }

    public void createListOfCards(final String[] values) {

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        for (String itr : values){
            arrayAdapter.add(itr);
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                android.app.AlertDialog.Builder builderInner = new android.app.AlertDialog.Builder(Dth.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builder.show();
    }

}
