package com.example.kiranastoremanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kiranastoremanagement.AdapterClass.SharedPreManager;
import com.example.kiranastoremanagement.Model.User;

public class UserDetail extends AppCompatActivity {
    TextView tvUserId, tvfirstName,tvlastName,tvstoreName,tvemailId,tvmobileNumber,tvlocation;
    Button btnEditUser,btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        if(SharedPreManager.getInstance(this).isLoggedIn()){
            tvUserId=findViewById(R.id.tvUserId);
            tvfirstName=findViewById(R.id.tvFName);
            tvlastName=findViewById(R.id.tvLName);
            tvstoreName=findViewById(R.id.tvUserStoreName);
            tvemailId=findViewById(R.id.tvEmailId);
            tvmobileNumber=findViewById(R.id.tvMobileNumber);
            tvlocation=findViewById(R.id.tvStoreLocation);
            btnEditUser=findViewById(R.id.btnEditUser);
            btnChangePassword=findViewById(R.id.btnChangePassword);

            User user = SharedPreManager.getInstance(UserDetail.this).getUser();

            tvUserId.setText(String.valueOf(user.getId()));
            tvfirstName.setText(user.getFirst_name());
            tvlastName.setText(user.getLast_name());
            tvstoreName.setText(user.getStore_name());
            tvemailId.setText(user.getEmail_id());
            tvmobileNumber.setText(user.getMobile_number());
            tvlocation.setText(user.getLocation());

        }
        else{
            Intent  intent = new Intent(UserDetail.this,MainActivity.class);

            startActivity(intent);
            finish();
        }

        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(UserDetail.this,UpdateUser.class);
              startActivity(intent);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserDetail.this,ChangePassword.class);
                startActivity(intent);
            }
        });


    }
}
