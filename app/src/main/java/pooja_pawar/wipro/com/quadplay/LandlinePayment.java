
package pooja_pawar.wipro.com.quadplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LandlinePayment extends AppCompatActivity {

    Bundle bundle;
    TextView textView;
    ImageView imageView;
    EditText telephone,amount;
    Button pay;

    String numberS,amountS,b,dateD,timeD;
    long number, userMobile;
    int am;

    DbHandlersUsers db;
    TimeToDate dateObj;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_pay);

        sessionManager = new SessionManager(this);

        //Code for getting the telephone and amount & sending the information to the payment page
        bundle = getIntent().getExtras();
        int a = bundle.getInt("Image");
        b = bundle.getString("provider");

        textView =(TextView)findViewById(R.id.textView1);
        imageView = (ImageView)findViewById(R.id.imageView1);
        telephone = (EditText)findViewById(R.id.telephoneNumber);
        amount = (EditText)findViewById(R.id.paymentAmount);
        pay = (Button)findViewById(R.id.buttonContinue);
        userMobile = sessionManager.getMobile();

        textView.setText(b);
        imageView.setImageResource(a);

        //Getting the current date and time from TimeToDate Class,
        db = new DbHandlersUsers(this);

        dateObj = new TimeToDate(this);

        dateD = dateObj.getDate();
        timeD = dateObj.getTime();

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numberS = telephone.getText().toString();
                amountS = amount.getText().toString();

                //Basic error handling to check for valid inputs,
                if(numberS.equals("") || amountS.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter All The Details",Toast.LENGTH_SHORT).show();
                }else if(numberS.length()<11){
                    Toast.makeText(getApplicationContext(),"Enter Valid 11-digit Telephone Number",Toast.LENGTH_SHORT).show();
                }else if(!numberS.startsWith("0")){
                    Toast.makeText(getApplicationContext(),"Telephone Number should start with 0 as Prefix",Toast.LENGTH_SHORT).show();
                }else if(amountS.equals("0") || amountS.startsWith("0")){
                    Toast.makeText(getApplicationContext(),"Enter Valid Amount",Toast.LENGTH_SHORT).show();
                }else{

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);

                    number = Long.parseLong(numberS);
                    am = Integer.parseInt(amountS);

                    //Inserting CARD DETAILS INTO DB
//                    long id = db.addLandline( userMobile , number, am, b, dateD, timeD);
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

