package pooja_pawar.wipro.com.quadplay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

public class RegisterUsers extends AppCompatActivity {


    EditText name, email, mobile, password, city;
    Button submit;
    String mobileST, emailS, passwordS, nameS, cityS;
    DbHandlersUsers db;
    SessionManager sessionManager;
    AlertDialogManager alertManager;
    String m_Text = "";
    int rno;
    long mobileS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_users);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        db = new DbHandlersUsers(this);

        name = (EditText) findViewById(R.id.nameUser);
        email = (EditText) findViewById(R.id.emailUser);
        mobile = (EditText) findViewById(R.id.mobileUser);
        password = (EditText) findViewById(R.id.passwordUser);
        city = (EditText) findViewById(R.id.cityUser);
        submit = (Button) findViewById(R.id.submit);

        sessionManager = new SessionManager(this);
        alertManager = new AlertDialogManager();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobileST = mobile.getText().toString();
                emailS = email.getText().toString();
                passwordS = password.getText().toString();
                nameS = name.getText().toString();
                cityS = city.getText().toString();

                //basic Error handling code,
                if (mobileST.equals("") || emailS.equals("") || passwordS.equals("") || nameS.equals("") || cityS.equals("")) {
                    Toast.makeText(getApplicationContext(), "Try Again. This Time Enter all the fields", Toast.LENGTH_SHORT).show();
                    alertManager.showAlertDialog(RegisterUsers.this, "Registration Failed", "Please enter all the fields", false);
                } else if (mobileST.length() < 10) {
                    alertManager.showAlertDialog(RegisterUsers.this, "Registration Failed", "Mobile Number too short. Please enter 10-digit Valid Mobile Number", false);
                } else {

                    mobileS = Long.parseLong(mobileST);

                        if (sendSMS(mobileS)){
                            AlertDialog();
                        }
                    }

                }

        });

    }


    public void AlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUsers.this);
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

                if (db.checkMobileExist(mobileS) && m_Text.equals(Integer.toString(rno))) {

                        sessionManager.createLoginSession(mobileS);

                        long id = db.addUser(mobileS, emailS, passwordS, nameS, cityS);

                        if (id < 0) {
                            System.out.println("Row NOT added successfully");
                        }
                        else
                        {
                            System.out.println("Row added successfully");
                        }

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();

                    }
                    else
                    {
                        alertManager.showAlertDialog(RegisterUsers.this, "Registration Failed", "OTP Wrong !", false);
                    }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder.show();
    }


    public char[] RandomOtp() {
        int len = 4;
        String numbers = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        char[] otp = new char[len];

        for (int i = 0; i < len; i++)
            {
                // Use of charAt() method : to get character value
                // Use of nextInt() as it is scanning the value as int
                otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
            }
            return otp;
    }



    public boolean sendSMS(long mobileno) {

        rno = Integer.parseInt(new String(RandomOtp()));

        //Your authentication key
        String authkey = "163681AUNtWS1xjpU595bada7";
        //Multiple mobiles numbers separated by comma
        String mobiles = new Long(mobileno).toString();;
        //Sender ID,While using route4 sender id should be 6 characters long.
        String senderId = "102234";
        //Your message to send, Add URL encoding here.
        String message = "Verify Your Mobile No For RechargeApp By this OTP " + rno;
        //define route
        String route = "default";

        URLConnection myURLConnection = null;
        URL myURL = null;
        BufferedReader reader = null;

        //encoding message
        String encoded_message = URLEncoder.encode(message);

        //Send SMS API
        String mainUrl = "http://api.msg91.com/api/sendhttp.php?";

        //Prepare parameter string
        StringBuilder sbPostData = new StringBuilder(mainUrl);
        sbPostData.append("authkey=" + authkey);
        sbPostData.append("&mobiles=" + mobiles);
        sbPostData.append("&message=" + encoded_message);
        sbPostData.append("&route=" + route);
        sbPostData.append("&sender=" + senderId);


        mainUrl = sbPostData.toString();

        try {
            //prepare connection
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

            //reading response
            String response;
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", "" + response);

            //finally close connection
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }


}

