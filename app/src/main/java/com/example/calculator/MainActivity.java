package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.calculator.ui.calculator.CalculatorFragment;
import com.example.calculator.ui.currency.CurrencyFragment;
import com.example.calculator.ui.date.DateFragment;
import com.example.calculator.ui.length.LengthFragment;
import com.example.calculator.ui.temperature.TemperatureFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public ActionBar actionBar;
    private CalculatorFragment calculatorFragment;
    private CurrencyFragment currencyFragment;
    private DateFragment dateFragment;
    private LengthFragment lengthFragment;
    private TemperatureFragment temperatureFragment;

    public boolean onSelectItem(@NonNull MenuItem item){

        Fragment fragment = new CalculatorFragment();

        switch (item.getItemId()){
            case (R.id.nav_calculator):{
                if(calculatorFragment == null){
                    calculatorFragment = new CalculatorFragment();
                }

                fragment = calculatorFragment;
                break;
            }
            case (R.id.nav_currency):{
                if(currencyFragment == null){
                    currencyFragment = new CurrencyFragment();
                }

                fragment = currencyFragment;
                break;
            }
            case (R.id.nav_date):{
                if(dateFragment == null){
                    dateFragment = new DateFragment();
                }

                fragment = dateFragment;
                break;
            }
            case (R.id.nav_length):{
                if(lengthFragment == null){
                    lengthFragment = new LengthFragment();
                }

                fragment = lengthFragment;
                break;
            }
            case (R.id.nav_temperature):{
                if(temperatureFragment == null){
                    temperatureFragment = new TemperatureFragment();
                }

                fragment = temperatureFragment;
                break;
            }
            default: break;
        }

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.nav_host_fragment_container, fragment).
                commit();


        drawerLayout.closeDrawer(GravityCompat.START);

        if(actionBar != null){
            actionBar.setTitle(item.getTitle());
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupWithNavController(navigationView, navController);

        drawerLayout.openDrawer(GravityCompat.START);

        actionBar = getSupportActionBar();

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item -> onSelectItem(item));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}