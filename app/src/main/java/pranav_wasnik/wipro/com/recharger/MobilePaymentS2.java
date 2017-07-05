package pranav_wasnik.wipro.com.recharger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MobilePaymentS2 extends AppCompatActivity {

    EditText paymentAmount;
    Button submit;

    double amount;
    String value;
    Bundle bundle;

    long mobile;
    String mode="",provider="",state="";

    TextView textViewMode,textViewMobile,textViewProvider,textViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_pay2);

        bundle=getIntent().getExtras();
        mobile=bundle.getLong("mobile");
        mode=bundle.getString("mode");
        provider=bundle.getString("provider");
        state=bundle.getString("state");

        textViewMode=(TextView)findViewById(R.id.mode);
        textViewMobile=(TextView)findViewById(R.id.number);
        textViewProvider=(TextView)findViewById(R.id.provider);
        textViewState=(TextView)findViewById(R.id.state);

        textViewMode.setText(mode);
        textViewMobile.setText(""+mobile);
        textViewProvider.setText(provider);
        textViewState.setText(state);

        paymentAmount=(EditText)findViewById(R.id.paymentAmount);

        submit=(Button)findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=paymentAmount.getText().toString();

                if(value.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter All The Details",Toast.LENGTH_SHORT).show();
                }else{
                    //balance checking code can be included here itself but if the user wants to pay with some other debit card which
                    //havent have been added in advance, this could have caused anomalies. Hence didn't included it here.

                    Intent i=new Intent(getApplicationContext(),CardPayment.class);

                    Bundle bundle=new Bundle();
                    bundle.putLong("mobile",mobile);
                    bundle.putString("mode",mode);
                    bundle.putString("provider",provider);
                    bundle.putString("state",state);
                    bundle.putString("tag","Mobile");

                    amount=Double.parseDouble(value);
                    System.out.println(amount);

                    bundle.putDouble("amount",amount);
                    i.putExtras(bundle);
                    startActivity(i);

                }
            }
        });
    }
}
