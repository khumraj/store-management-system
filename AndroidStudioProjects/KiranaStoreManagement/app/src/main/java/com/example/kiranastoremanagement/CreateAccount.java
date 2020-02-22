package com.example.kiranastoremanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputLayout;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;
import java.util.HashMap;
import java.util.Map;
import static android.widget.Toast.*;

public class CreateAccount extends AppCompatActivity {

    //Initialize or Declare all Variable

    ProgressDialog pDialog;
    Button btnRegister;
    TextView tvSignIn;
    EditText etlname, etfname, etemail, etnpass, etcpass, etMPhn;
    String first_name, last_name, email_id, mobile_number, new_password, conform_password;
    String REGISTERED_URL = "http://gurungonlineshopping.com/StoreManagement/create_account.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Define, set all the variable


        etfname = (EditText) findViewById(R.id.etFname);
        etlname = (EditText) findViewById(R.id.etLname);
        etemail = (EditText) findViewById(R.id.etID);
        etMPhn = (EditText) findViewById(R.id.etMobileNumber);
        etnpass = (EditText) findViewById(R.id.etNpassword);
        etcpass = (EditText) findViewById(R.id.etCpassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        tvSignIn = (TextView) findViewById(R.id.tvSignIn);

        // Adding onclick listener
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent);
                makeText(CreateAccount.this, "SignIn", LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterForm();

            }
        });
    }
    // Creating register form function.
        public void RegisterForm(){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            first_name = etfname.getText().toString().trim();
            last_name = etlname.getText().toString().trim();
            email_id = etemail.getText().toString().trim();
            mobile_number = etMPhn.getText().toString().trim();
            new_password = etnpass.getText().toString().trim();
            conform_password = etcpass.getText().toString().trim();

            boolean invalid = false;

            if (first_name.equals("")) {
                invalid = true;
                Toast.makeText(getApplicationContext(), "Enter your first name",
                        Toast.LENGTH_SHORT).show();
            } else

            if (last_name.equals("")) {
                invalid = true;
                Toast.makeText(getApplicationContext(), "Enter your last name",
                        Toast.LENGTH_SHORT).show();
            }else

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
                invalid = true;
                Toast.makeText(getApplicationContext(), "Enter your email id",
                        Toast.LENGTH_SHORT).show();
            }else

            if (!android.util.Patterns.PHONE.matcher(mobile_number).matches()) {
                invalid = true;
                Toast.makeText(getApplicationContext(), "Enter your mobile number",
                        Toast.LENGTH_SHORT).show();
            }else

            if (new_password.length() < 8) {
                invalid = true;
                Toast.makeText(getApplicationContext(), "Enter your new password at least 8 characters",
                        Toast.LENGTH_SHORT).show();
            }else

            if (conform_password.length() < 8) {
                invalid = true;
                Toast.makeText(getApplicationContext(), "Enter your conform password at least 8 characters",
                        Toast.LENGTH_SHORT).show();
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTERED_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    // Hiding the progress dialog after all task complete
                    progressDialog.dismiss();

                    // Matching server response message to our text.
                    if (response.equalsIgnoreCase("successfully registered")) {

                        // If response matched then show the toast.
                        Toast.makeText(CreateAccount.this, "Registered Successfully", Toast.LENGTH_LONG).show();

                        // Finish the current create account activity.
                        finish();

                        // Opening the main activity using intent.
                        Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                        startActivity(intent);
                    } else {

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(CreateAccount.this, response, Toast.LENGTH_LONG).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    makeText(CreateAccount.this, error.getMessage().toString(), LENGTH_SHORT).show();

                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("first_name", first_name);
                    params.put("last_name", last_name);
                    params.put("email_id", email_id);
                    params.put("mobile_number", mobile_number);
                    params.put("new_password", new_password);
                    params.put("conform_password", conform_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(CreateAccount.this);
            requestQueue.add(stringRequest);

        }

}


