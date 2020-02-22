package com.example.kiranastoremanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DatePickerDialog;

import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.util.Calendar;

public class TransactionList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener{
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    Button btnFrom, btnTo;
    TextView tvFrom, tvTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        btnFrom=findViewById(R.id.btnFrom);
        btnTo=findViewById(R.id.btnTo);
        tvFrom=findViewById(R.id.tvFrom);
        tvTo=findViewById(R.id.tvTo);

        //set default time and date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        tvTo.setText(currentDate);
        tvFrom.setText(currentDate);

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerHelper();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerHelper();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        tvFrom.setText(currentDateString);
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                i = new Intent(this, SignIn.class);
                startActivity(i);
                break;

            case R.id.nav_price_list:
                i = new Intent(this, PriceList.class);
                startActivity(i);
                break;

            case R.id.nav_customer_list:
                i = new Intent(this, TransactionList.class);
                startActivity(i);
                break;

            case R.id.nav_profile: i= new Intent(this, Profile.class);
                startActivity(i);
                break;

            case R.id.nav_transaction_details:
                i = new Intent(this, CustomerList.class);
                startActivity(i);
                break;

            case R.id.nav_supplier:
                i = new Intent(this, SupplierList.class);
                startActivity(i);
            default:
                break;
        }
        return true;
    }
}
