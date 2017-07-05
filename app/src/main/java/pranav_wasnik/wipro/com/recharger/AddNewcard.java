package pranav_wasnik.wipro.com.recharger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewcard extends AppCompatActivity {

    EditText cardNumber,cardExpiry1,cardExpiry2,cardCvv,cardMobile,cardBalance;
    Button addCard;
    DbHandlersUsers db;
    String a,b1,b2,c,d,e;
    double bal;
    long number,mobileNumber;
    int cvv;

    String providerString, TAG,dateD,timeD;
    double amount;
    long mobile;

    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newcard);

        cardNumber=(EditText)findViewById(R.id.cardNumber);
        cardExpiry1=(EditText)findViewById(R.id.expiry1);
        cardExpiry2=(EditText)findViewById(R.id.expiry2);
        cardCvv=(EditText)findViewById(R.id.cardCvv);
        cardBalance=(EditText)findViewById(R.id.cardBalance);
        addCard=(Button)findViewById(R.id.addCard);

        db=new DbHandlersUsers(this);
        sm=new SessionManager(this);

        //getting the stored mobile no. during login from shared preferences
        mobileNumber=sm.getMobile();

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a=cardNumber.getText().toString();
                b1=cardExpiry1.getText().toString();
                b2=cardExpiry2.getText().toString();
                c=cardCvv.getText().toString();
                e=cardBalance.getText().toString();

                if(a.equals("") || b2.equals("") || b1.equals("") || c.equals("") || e.equals("")){
                    Toast.makeText(getApplication(),"Please Enter All The Details",Toast.LENGTH_SHORT).show();
                }else{
                    number=Long.parseLong(a);
                    b2=b1+"/"+b2;
                    bal=Double.parseDouble(e);
                    cvv=Integer.parseInt(c);

                    //Condition for checking unique constraint for card number addition
                    boolean flag=db.checkDuplicateCard(number);
                    if(flag){
                        long id=db.addBankData(number,bal,mobileNumber,b2,cvv);

                        //Code for adding the new card to the Shared Preferences
                        sm.addCardDetails(""+number,b2,""+cvv);

                        if(id<0){
                            System.out.println("Row NOT added in Card table");
                        }else{
                            System.out.println("Row added in Card table");
                        }

                        Intent i=new Intent(getApplicationContext(),CardPayment.class);
                        Bundle bundle=getIntent().getExtras();

                        amount = bundle.getDouble("amount");
                        mobile = bundle.getLong("mobile");
                        providerString = bundle.getString("provider");
                        TAG=bundle.getString("tag");

                        i.putExtras(bundle);

                        startActivity(i);
                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(),"Card Already exist. Try adding another",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
