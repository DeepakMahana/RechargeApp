package pooja_pawar.wipro.com.quadplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Account extends AppCompatActivity {

    TextView username,email,mobile;
    ListView listView;
    Button updateBalance;

    long mobileSession;
    SessionManager sm;
    DbHandlersUsers db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        username=(TextView)findViewById(R.id.userName);
        mobile=(TextView)findViewById(R.id.mobile1);
        email=(TextView)findViewById(R.id.email1);
      //  updateBalance=(Button)findViewById(R.id.updateBalance);
       // listView=(ListView)findViewById(R.id.listviewCard);

        db=new DbHandlersUsers(this);
        //Cursor cursor=db.getAllColumnsBank1(mobileSession);

        sm=new SessionManager(this);
        mobileSession=sm.getMobile();

        String []values=db.getData(mobileSession);

        //Code for getting Users Data from db and setting in the required field
        if(values.equals("")){
            Toast.makeText(this,"Doesnt return anything",Toast.LENGTH_SHORT).show();
        }else{
            username.setText("Welcome "+values[1]);
            mobile.setText(""+mobileSession);
            email.setText(values[0]);
            //username.setAnimation(AnimationUtils.loadAnimation(this,R.anim.text_animation));
        }

        String [][]values1=sm.getCard();

/*
        System.out.println(Arrays.toString(values1[0]));
        System.out.println(Arrays.toString(values1[1]));
        System.out.println(Arrays.toString(values1[2]));
*/

        if(values[1].equals("None")){
            Toast.makeText(this,"No card Available",Toast.LENGTH_SHORT).show();
        }else{
            listView.setAdapter(new ListAdapterCard(this,values1[0],values1[1],values1[2]));
        }

        updateBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),UpdateBalance.class);
                startActivity(i);
            }
        });
    }
}
