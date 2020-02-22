package com.example.kiranastoremanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.security.cert.Extension;

public class SignIn extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    // Variable Declaration

    Toolbar toolbar;
    DrawerLayout drawer;
    CardView cvPrice, cvTransaction, cvSupplier, cvCustomer, cvAddSale, cvAddPurchase;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Defining section
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        cvPrice = (CardView) findViewById(R.id.cvPriceList);
        cvCustomer = (CardView) findViewById(R.id.cvCustomerList);
        cvTransaction = (CardView) findViewById(R.id.cvTransactionList);
        cvSupplier = (CardView) findViewById(R.id.cvSupplierList);
        cvAddSale=findViewById(R.id.cvAddSale);
        cvAddPurchase=findViewById(R.id.cvAddPurchase);
        navigationView= findViewById(R.id.nav_view);

        //Add Click Listener to the CardView
        cvSupplier.setOnClickListener(this);
        cvTransaction.setOnClickListener(this);
        cvPrice.setOnClickListener(this);
        cvCustomer.setOnClickListener(this);
        cvAddSale.setOnClickListener(this);
        cvAddPurchase.setOnClickListener(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.cvPriceList:
                i = new Intent(this, PriceList.class);
                startActivity(i);
                break;

            case R.id.cvTransactionList:
                i = new Intent(this, TransactionList.class);
                startActivity(i);
                break;

            case R.id.cvCustomerList:
                i = new Intent(this, CustomerList.class);
                startActivity(i);
                break;

            case R.id.cvSupplierList:
                i = new Intent(this, SupplierList.class);
                startActivity(i);
                break;

            case R.id.cvAddSale:
                i = new Intent(this, AddSale.class);
                startActivity(i);
                break;

            case R.id.cvAddPurchase:
                i = new Intent(this, AddPurchase.class);
                startActivity(i);

            default:
                break;
        }

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
