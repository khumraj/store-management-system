package com.example.kiranastoremanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class SupplierList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolBar;
    FloatingActionButton btnAdd;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_list);

        //Defining section
        drawer = findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        toolBar = findViewById(R.id.toolbar1);
        btnAdd=findViewById(R.id.btnadd);
        setSupportActionBar(toolBar);

        setSupportActionBar(toolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Add Click Listener to the Button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(SupplierList.this, SuppilerAdd.class);
                startActivity(intent);
            }
        });
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
    //create onclick method of add button
    public void add(View view) {


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
