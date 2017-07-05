package pooja_pawar.wipro.com.quadplay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mobileButton,landlineButton,dataButton,transactionButton,bankaccountButton,dthButton;
    Button addUsers,testing,account,addOtp;
    ImageView batman;
    TextView username;

    SessionManager session;
    CoordinatorLayout cl;
    DbHandlersUsers db;

    long mobileSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session=new SessionManager(this);
        session.checkLogin();
        db=new DbHandlersUsers(this);

        cl=(CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        batman=(ImageView)findViewById(R.id.imageBatman);
        username=(TextView)findViewById(R.id.userName);

        mobileSession=session.getMobile();//Getting mobile no of user which was saved in shared preferences during registration or login
        String []values=db.getData(mobileSession);

        username.setText("Welcome "+values[1]);

        batman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar=Snackbar.make(cl,"It's not who I am underneath, but what I do that defines me.",Snackbar.LENGTH_LONG);
                View sv=snackbar.getView();
                sv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.setActionTextColor(getResources().getColor(R.color.textColor));
                snackbar.show();
            }
        });

        //Unique identifier is sent in the Bundle because MobilePaymentS1 activity will be opened from two different activities,
        //To distinguish between them two, we are sending unique ID i.e. the name of Activity from which it is opened or accessed.
        mobileButton=(Button)findViewById(R.id.mobilepayment);
        mobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MobilePaymentS1.class);
                Bundle args=new Bundle();
                args.putString("uniqueID","From MainActivity");
                i.putExtras(args);
                startActivity(i);
            }
        });

        landlineButton=(Button)findViewById(R.id.landlinepayment);
        landlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),LandlinePaymentS1.class);
                startActivity(i);
            }
        });

        dataButton=(Button)findViewById(R.id.datacardpayment);
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),DatacardPaymentS1.class);
                startActivity(i);
            }
        });

        dthButton=(Button)findViewById(R.id.dthcardpayment);
        dthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Dth.class);
                startActivity(i);
            }
        });

        bankaccountButton=(Button)findViewById(R.id.bankaccount);
        bankaccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),BankAccount.class);
                startActivity(i);
            }
        });

        transactionButton=(Button)findViewById(R.id.transactions);
        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),TransactionHistory.class);
                startActivity(i);
            }
        });

        account=(Button)findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Account.class);
                startActivity(i);
            }
        });

        addUsers=(Button)findViewById(R.id.addUsers);
        addUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AddUsers.class);
                startActivity(i);
            }
        });

    }

    //Code for Overflow menu,
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.hello:
                Toast.makeText(this,"Work Harder And It Will",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                session.logoutUser();

                //Code for deleting all the transactions
                db.deleteAllData();
                Intent i=new Intent(this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return true;
    }


    //Code for clicking two times on HomeScreen to exit,
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Snackbar snackbar=Snackbar.make(cl,"Please click BACK again to exit",Snackbar.LENGTH_SHORT);
        View sv=snackbar.getView();
        sv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.setActionTextColor(getResources().getColor(R.color.textColor));
        snackbar.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
