package pranav_wasnik.wipro.com.recharger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CardPayment extends AppCompatActivity {

    EditText cNumber, cExpiry1, cExpiry2, cCvv;
    Button pay,newCard;

    long cardNumber, mobile;
    String cardExpiry1, cardExpiry2, cardCvv;
    String providerString, TAG,dateD,timeD;
    int cardCvv1;
    double amount;

    DbHandlersUsers db;
    TimeToDate dateObj;

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_pay);

        cNumber = (EditText) findViewById(R.id.cardNumber);
        cExpiry1 = (EditText) findViewById(R.id.cardExpiry1);
        cExpiry2 = (EditText) findViewById(R.id.cardExpiry2);
        cCvv = (EditText) findViewById(R.id.cardCvv);
        pay = (Button) findViewById(R.id.buttonMakePayment);
        newCard=(Button)findViewById(R.id.addNewCard);

        //Getting the current date and time from TimeToDate Class,
        db = new DbHandlersUsers(this);
        dateObj=new TimeToDate(this);

        bundle = getIntent().getExtras();
        amount = bundle.getDouble("amount");
        mobile = bundle.getLong("mobile");
        dateD=dateObj.getDate();
        timeD=dateObj.getTime();
        providerString = bundle.getString("provider");
        TAG=bundle.getString("tag");

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting the required data
                cardExpiry1 = cExpiry1.getText().toString();
                cardExpiry2 = cExpiry2.getText().toString();
                cardCvv = cCvv.getText().toString();

                if(cardExpiry1.equals("") || cardExpiry2.equals("") || cardCvv.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter All The Details", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);

                    String a = cNumber.getText().toString();
                    cardNumber = Long.parseLong(a);

                    cardCvv1 = Integer.parseInt(cardCvv);

                    //Code For checking valid card details
                    double result = db.checkCredential(cardNumber, cardExpiry1 + "/" + cardExpiry2, cardCvv1);

                    //Checking whether the entered recharge amount is greater than their respective account balance
                    System.out.println(amount);
                    if (result == 0.0) {
                        Toast.makeText(getApplicationContext(), "Invalid Information", Toast.LENGTH_SHORT).show();
                    } else if (result >= amount) {
                        Toast.makeText(getApplicationContext(), "Recharge Done", Toast.LENGTH_SHORT).show();

                        //Changing/Updating the balance
                        boolean flag=db.updateBalance(cardNumber,result-amount);
                        if(flag){
                            System.out.println("Failed to update");
                        }else{
                            System.out.println("Updated Successfully");
                        }

                        //Code for inserting for transaction history thing
                        long id=db.addRechargeInfo(mobile,amount,dateD,timeD,providerString,TAG);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Insufficient Balance", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        newCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AddNewcard.class);
                bundle=new Bundle();

                bundle.putLong("mobile",mobile);
                bundle.putString("provider",providerString);
                bundle.putString("tag","Mobile");
                bundle.putDouble("amount",amount);

                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}
