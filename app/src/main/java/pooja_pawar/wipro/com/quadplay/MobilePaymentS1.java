package pooja_pawar.wipro.com.quadplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MobilePaymentS1 extends AppCompatActivity {

    EditText mobileNumber;
    Button submit;
    Spinner spinnerCompany,spinnerState;
    RadioButton radioPrepaid,radioPostpaid;

    long mobile;
    String mode="",provider="",state="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_pay1);

        mobileNumber=(EditText)findViewById(R.id.mobileNumber);

        //Getting the Unique Id to identify from which Activity this one is opened, and to carry actions accordingly,
        Bundle args=getIntent().getExtras();
        String id=args.getString("uniqueID");
        if(id.equals("From Datacard")){
            mobile=args.getLong("mobile");
            mobileNumber.setText(""+mobile);
        }

        radioPrepaid=(RadioButton)findViewById(R.id.prepaidRadioButton);
        radioPostpaid=(RadioButton)findViewById(R.id.postpaidRadioButton);

        //Handling Spinner events
        spinnerCompany=(Spinner)findViewById(R.id.spinnerCompanyName);
        spinnerState=(Spinner)findViewById(R.id.spinnerStateName);

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.providers,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapter1);

        spinnerCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Value for provider variable is obtained here
                provider=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.states,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter2);

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Value for state variable is obtained here
                state=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit=(Button)findViewById(R.id.buttonDone);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),MobilePaymentS2.class);

                Bundle bundle=new Bundle();
                //Value for mobile variable is obtained here
                String mobileS=mobileNumber.getText().toString();

                if(mobileS.equals("") || mode.equals("") || provider.equals("") || state.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter All The Details",Toast.LENGTH_SHORT).show();
                }else if(mobileS.length()<10){
                    Toast.makeText(getApplicationContext(),"Enter Correct Mobile Number",Toast.LENGTH_SHORT).show();
                }else{
                    //This condition is for checking if the mobile number is already set from another activity
                    if(mobile==0){
                        mobile=Long.parseLong(mobileS);
                    }

                    bundle.putLong("mobile",mobile);
                    bundle.putString("mode",mode);
                    bundle.putString("provider",provider);
                    bundle.putString("state",state);

                    i.putExtras(bundle);
                    startActivity(i);
                }
            }
        });
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
}
