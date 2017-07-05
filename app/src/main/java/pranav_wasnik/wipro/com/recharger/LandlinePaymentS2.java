package pranav_wasnik.wipro.com.recharger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LandlinePaymentS2 extends AppCompatActivity {
    Bundle bundle;
    TextView textView;
    ImageView imageView;
    EditText telephone,amount;
    Button cont;

    String numberS,amountS,b;
    long number;
    double amountDouble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_pay2);

        //Code for getting the telephone and amount & sending the information to the payment page
        bundle=getIntent().getExtras();
        int a=bundle.getInt("Image");
        b=bundle.getString("provider");

        textView=(TextView)findViewById(R.id.textView1);
        imageView=(ImageView)findViewById(R.id.imageView1);
        telephone=(EditText)findViewById(R.id.telephoneNumber);
        amount=(EditText)findViewById(R.id.paymentAmount);
        cont=(Button)findViewById(R.id.buttonContinue);

        textView.setText(b);
        imageView.setImageResource(a);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberS=telephone.getText().toString();
                amountS=amount.getText().toString();

                //Basic error handling to check for valid inputs,
                if(numberS.equals("") || amountS.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter All The Details",Toast.LENGTH_SHORT).show();
                }else if(numberS.length()<11){
                    Toast.makeText(getApplicationContext(),"Enter Valid 11-digit Telephone Number",Toast.LENGTH_SHORT).show();
                }else if(!numberS.startsWith("0")){
                    Toast.makeText(getApplicationContext(),"Telephone Number should start with 0 as prefix",Toast.LENGTH_SHORT).show();
                }else if(amountS.equals("0") || amountS.startsWith("0")){
                    Toast.makeText(getApplicationContext(),"Enter Valid Amount",Toast.LENGTH_SHORT).show();
                }else{
                    Intent i=new Intent(getApplicationContext(),CardPayment.class);
                    Bundle arg=new Bundle();

                    number=Long.parseLong(numberS);
                    amountDouble=Double.parseDouble(amountS);

                    arg.putLong("mobile",number);
                    arg.putDouble("amount",amountDouble);
                    arg.putString("provider",b);
                    arg.putString("tag","Landline");

                    i.putExtras(arg);
                    startActivity(i);
                }

            }
        });
    }
}
