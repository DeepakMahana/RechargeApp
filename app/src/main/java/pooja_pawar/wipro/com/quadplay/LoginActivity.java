
package pooja_pawar.wipro.com.quadplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {

    EditText txtMobile, txtPassword;
    Button btnLogin,btnRegister;

    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    DbHandlersUsers handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManager(getApplicationContext());
        handler = new DbHandlersUsers(this);


        txtMobile= (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String mobile = txtMobile.getText().toString();
                String password = txtPassword.getText().toString();

                if (mobile.trim().length() > 0 && password.trim().length() > 0) {

                    long a = Long.parseLong(mobile);
                    String b = handler.checkMobile(a);

                    if(password.equals(b)) {

                        session.createLoginSession(a); //Change to shared preferences is made after the user is logged in,

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    } else {
                        // username / password doesn't match
                        alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
                    }
                } else {
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
                }
            }
        });

        btnRegister=(Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),RegisterUsers.class);
                startActivity(i);
            }
        });
    }

    //There where some anomalies when the user pressed back button, this is to handle the same,
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

