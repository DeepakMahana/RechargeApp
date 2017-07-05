package pooja_pawar.wipro.com.quadplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BankAccount extends AppCompatActivity {

    Button continueToPayment;
    EditText number;
    String num;
    long numLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankaccount);

       /* continueToPayment=(Button)findViewById(R.id.buttonContinue);
        number=(EditText)findViewById(R.id.datacardNumber);

        continueToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num=number.getText().toString();

                if(num.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter 10-digit valid Mobile Number",Toast.LENGTH_SHORT).show();
                }else{
                    Intent i=new Intent(getApplicationContext(),MobilePaymentS1.class);
                    Bundle args=new Bundle();

                    numLong=Long.parseLong(num);

                    args.putLong("mobile",numLong);
                    args.putString("uniqueID","From Datacard");//unique Id is send from here to MobilePaymentS1
                    i.putExtras(args);
                    startActivity(i);
                }
            }
        });  */

    }
}
