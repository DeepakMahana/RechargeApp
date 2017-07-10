package pooja_pawar.wipro.com.quadplay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class RechargeMobile extends AppCompatActivity implements OnTapList{

    EditText MobileNo, Amount;
    Button buttonRecharge;
    Spinner spinnerCompany, spinnerState;
    RadioButton radioPrepaid, radioPostpaid;
    long mobile;
    long cardno;
    String mode = "", provider = "", state = "", dateD, timeD;
    String mobileS, amountS;
    CardListFragment cd;
    DbHandlersUsers db;
    TimeToDate dateObj;
    SessionManager sessionManager;
    Bundle bundle;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_mobile);

        db = new DbHandlersUsers(RechargeMobile.this);
        sessionManager = new SessionManager(this);
        mobile = sessionManager.getMobile();

        // Check if Payment Card is Added by the User
        boolean res1 = db.BankDetailsCount();
        boolean res2 = db.CardDetailsUser(mobile);
        if (res1 == false){
            AlertDialog();
        }
        else if(res2 == false){
            AlertDialog();
        }
        else {

            MobileNo = (EditText) findViewById(R.id.MobileNo);
            Amount = (EditText) findViewById(R.id.Amount);
            radioPrepaid = (RadioButton) findViewById(R.id.prepaidRadioButton);
            radioPostpaid = (RadioButton) findViewById(R.id.postpaidRadioButton);

            // Getting Date and Time
            dateObj = new TimeToDate(this);
            dateD = dateObj.getDate();
            timeD = dateObj.getTime();

            //Handling Spinner events
            spinnerCompany = (Spinner) findViewById(R.id.spinnerCompanyName);
            spinnerState = (Spinner) findViewById(R.id.spinnerStateName);

            final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.providers, android.R.layout.simple_spinner_item);
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

            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerState.setAdapter(adapter2);

            spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Value for state variable is obtained here
                    state = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            buttonRecharge = (Button) findViewById(R.id.buttonRecharge);
            buttonRecharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mobileS = MobileNo.getText().toString();
                    amountS = Amount.getText().toString();

                    if (mobileS.equals("") || amountS.equals("") || mode.equals("") || provider.equals("") || state.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter All The Details", Toast.LENGTH_SHORT).show();
                    } else if (mobileS.length() < 10) {
                        Toast.makeText(getApplicationContext(), "Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();
                    } else if (amountS.equals("0") || amountS.startsWith("0")) {
                        Toast.makeText(getApplicationContext(), "Enter Valid Amount", Toast.LENGTH_SHORT).show();
                    } else {

                        final android.app.FragmentManager fm = getFragmentManager();
                        final CardListFragment cd = new CardListFragment();
                        cd.show(fm, "Card_List");
                        Toast.makeText(getApplicationContext(), "Select Your Payment Card", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    public void onRadioButtonClicked(View view){
        boolean checked=((RadioButton)view).isChecked();
        switch(view.getId()){
            case R.id.prepaidRadioButton:
                if(checked){
                    mode="prepaid";
                }
                break;
            case R.id.postpaidRadioButton:
                if(checked){
                    mode="postpaid";
                }
                break;
        }
    }

    public void AlertDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(RechargeMobile.this);
        builder.setTitle("You Didnt Add Any Payment Card Yet !!");
        // Set up the buttons
        builder.setPositiveButton("Add Card", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    Intent i = new Intent(getApplicationContext(), AddCard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
        });
        builder.setNegativeButton("Back To Main", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });

        builder.show();
    }

    @Override
    public void OnTapView(long cno) {
        cardno = cno;

        long rmobno = Long.parseLong(mobileS);
        int am = Integer.parseInt(amountS);
        int balance = db.checkBalance(cardno);

        if (balance < am){
            Toast.makeText(getApplicationContext(), "Payment Not Done ! Balance is Low !", Toast.LENGTH_SHORT).show();
        }
        else {

            long id = db.addRecharge(mobile, rmobno, mode, am, provider, state, dateD, timeD, cardno);
            boolean res = db.updateBalance(cardno, balance - am);
            long transID = db.addTrans(cardno, "Mobile", rmobno, am, balance - am ,mobile, dateD, timeD);


            if ((id != 0) && (transID != 0) && (res == true)) {

                Toast.makeText(getApplicationContext(), "Mobile Recharge Done Successfully !!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Card Not Added ! Please Try Again !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

