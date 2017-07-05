package pranav_wasnik.wipro.com.recharger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Testing extends AppCompatActivity {

    EditText editInt;
    Button button;

    DbHandlersUsers db;

    //TimeToDate tod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        editInt = (EditText) findViewById(R.id.intTesting);
        //tod=new TimeToDate(this);

        //Code to take database values;
        db = new DbHandlersUsers(this);

        button = (Button) findViewById(R.id.buttonTesting);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        /*
        Test Passed
        button = (Button) findViewById(R.id.buttonTesting);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                AddingValues add=new AddingValues(getApplication(),db);
                String s=add.addAllUsers();
                if(s.equals("User Added")){
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getApplicationContext(),add.getBankUsers(),Toast.LENGTH_SHORT).show();
            }
        });
*/

        /*
        Test passed
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=editInt.getText().toString();
                try {
                    long b=Long.parseLong(a);
                    b=b+1;
                    Toast.makeText(getApplicationContext(),""+b,Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }
}
