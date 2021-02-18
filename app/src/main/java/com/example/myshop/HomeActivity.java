package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigationView121;
    private NavController navController121;

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        navigationView = (NavigationView) findViewById(R.id.navmenu);
//        drawerLayout  = (DrawerLayout) findViewById(R.id.drawer);

        bottomNavigationView121 = findViewById(R.id.bottomNavigationViewnew);
        navController121 = Navigation.findNavController(this,R.id.fragment_container);

        NavigationUI.setupWithNavController(bottomNavigationView121, navController121);

//        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
//            {
//                switch (menuItem.getItemId())
//                {
//                    case R.id.new_menu_home :
//                        Toast.makeText(HomeActivity.this, "home panel is open ", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawer(GravityCompat.START);
//
//                        break;
//
//
//                    case R.id.new_menu_add :
////                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
////                    drawerLayout.closeDrawer(GravityCompat.START);
//
//                        Toast.makeText(HomeActivity.this, "add pane is open ", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        break;
//
//                    case R.id.new_menu_view :
//
//                        try
//                        {
////                            Intent intent = new Intent(Intent.ACTION_SEND);
////                            intent.setType("text/plain");
////                            intent.putExtra(Intent.EXTRA_SUBJECT,"Share Demo");
////                            String shareMessage = "https://play.google.com/store/apps/details?=" + BuildConfig.APPLICATION_ID+"\n\n";
////                            intent.putExtra(Intent.EXTRA_TEXT,shareMessage);
////                            startActivity(Intent.createChooser(intent,"share by"));
//
//                        }catch (Exception e)
//                        {
//                            Toast.makeText(HomeActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
//                        }
////                        Toast.makeText(HomeActivity.this, "menu panel is open ", Toast.LENGTH_SHORT).show();
////                        drawerLayout.closeDrawer(GravityCompat.START);
//////                      startActivity(new Intent(getApplicationContext(), CartActivity.class));
//////                       drawerLayout.closeDrawer(GravityCompat.START);
//                        break;
//
//
//                    case R.id.new_menu_setting :
//                        Toast.makeText(HomeActivity.this, "setting panel s open ", Toast.LENGTH_SHORT).show();
////                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        break;
//
//
//
//                }
//
//
//                return true;
//            }
//        });



    }
}