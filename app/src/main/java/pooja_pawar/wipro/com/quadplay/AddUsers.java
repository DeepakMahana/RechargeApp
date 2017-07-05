package pooja_pawar.wipro.com.quadplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AddUsers extends AppCompatActivity {

    Button userDetails,bankDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        userDetails=(Button)findViewById(R.id.userDetails);
        bankDetails=(Button)findViewById(R.id.bankDetails);

        userDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AllUsersData.class);
                startActivity(i);
            }
        });

        bankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AllBankData.class);
                startActivity(i);
            }
        });
    }
}
