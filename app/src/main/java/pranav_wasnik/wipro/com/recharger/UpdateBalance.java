package pranav_wasnik.wipro.com.recharger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Boolean.TRUE;

public class UpdateBalance extends AppCompatActivity {

    EditText number,balance;
    Button update;

    long cardNumber;
    double cardBalance;
    String num,bal;

    DbHandlersUsers db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_balance);

        number=(EditText)findViewById(R.id.number);
        balance=(EditText)findViewById(R.id.balance);
        update=(Button)findViewById(R.id.submit);

        db=new DbHandlersUsers(this);

        //This class takes the debit card no of the user whose balance is to be updated,
        //The new balance will directly be overwritten instead of adding it to the old balance value,
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num=number.getText().toString();
                bal=balance.getText().toString();

                if(num.equals("") || bal.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter All The Details",Toast.LENGTH_SHORT).show();
                }else{
                    cardNumber=Long.parseLong(num);
                    cardBalance=Double.parseDouble(bal);

                    //Updating the balance
                    boolean flag=db.updateBalance(cardNumber,cardBalance);

                    if(flag==TRUE){
                        Toast.makeText(getApplicationContext(),"Balance Updated",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }
}
