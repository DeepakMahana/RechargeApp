package pooja_pawar.wipro.com.quadplay;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterUsers extends AppCompatActivity {

    EditText name, email, mobile, password, city;
    Button submit;
    String mobileST, emailS, passwordS, nameS, cityS, date, time;
    DbHandlersUsers db;
    SessionManager sessionManager;
    AlertDialogManager alertManager;
    String m_Text = "";
    long mobileS;
    TimeToDate dateObj;
    OtpSend otp;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_users);

        db = new DbHandlersUsers(this);
        mProgress = new ProgressDialog(this);
        name = (EditText) findViewById(R.id.nameUser);
        email = (EditText) findViewById(R.id.emailUser);
        mobile = (EditText) findViewById(R.id.mobileUser);
        password = (EditText) findViewById(R.id.passwordUser);
        city = (EditText) findViewById(R.id.cityUser);
        submit = (Button) findViewById(R.id.submit);

        dateObj = new TimeToDate(this);
        otp = new OtpSend();

        date = dateObj.getDate();
        time = dateObj.getTime();

        sessionManager = new SessionManager(this);
        alertManager = new AlertDialogManager();

        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setTitle("Registering");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgress.show();

                mobileST = mobile.getText().toString();
                emailS = email.getText().toString();
                passwordS = password.getText().toString();
                nameS = name.getText().toString();
                cityS = city.getText().toString();

                //basic Error handling code,
                if (mobileST.equals("") || emailS.equals("") || passwordS.equals("") || nameS.equals("") || cityS.equals("")) {
                    mProgress.dismiss();
                    alertManager.showAlertDialog(RegisterUsers.this, "Registration Failed", "Please enter all the fields", false);
                } else if (mobileST.length() < 10) {
                    mProgress.dismiss();
                    alertManager.showAlertDialog(RegisterUsers.this, "Registration Failed", "Mobile Number too short. Please enter 10-digit Valid Mobile Number", false);
                } else {

                    mobileS = Long.parseLong(mobileST);

                    if (db.statusOfUserTable() == false) {
                        mProgress.setTitle("Sending OTP");
                        boolean res = otp.sendSMS(mobileS);
                        mProgress.dismiss();
                        if (res) {
                            AlertDialog();
                        } else {
                            alertManager.showAlertDialog(RegisterUsers.this, "Registration Failed", "Something Went Wrong ! OTP Not Sent", false);
                        }
                    } else

                    if (db.checkMobileExist(mobileS)) {
                        mProgress.dismiss();
                        alertManager.showAlertDialog(RegisterUsers.this, "Registration Failed", "Mobile No Already Exists ! Try Another Mobile No", false);
                    } else {
                        mProgress.setTitle("Sending OTP");
                        boolean res = otp.sendSMS(mobileS);
                        mProgress.dismiss();
                        if (res) {
                            AlertDialog();
                        } else {
                            alertManager.showAlertDialog(RegisterUsers.this, "Registration Failed", "Something Went Wrong ! OTP Not Sent", false);
                        }
                    }

                }
            }

        });

    }


    public void AlertDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUsers.this);
        builder.setTitle("Enter OTP");

        // Set up the input
        final EditText input = new EditText(RegisterUsers.this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                m_Text = input.getText().toString();

                mProgress.setTitle("Verifying OTP !");

                if (m_Text.equals(Integer.toString(otp.rno))) {

                    sessionManager.createLoginSession(mobileS);

                    long id = db.addUser(mobileS, emailS, passwordS, nameS, cityS, date, time);

                    if (id < 0) {
                        mProgress.setTitle("User Not Created !");
                        mProgress.dismiss();
                        System.out.println("User NOT added successfully");
                    } else {
                        mProgress.setTitle("User Created !");
                        mProgress.dismiss();
                        System.out.println("User added successfully");
                    }

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);

                    dialog.dismiss();

                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();

                } else {
                    builder.setTitle("Wrong OTP !! Try Again");
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(RegisterUsers.this, "Sorry ! Registration Failed !!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

}