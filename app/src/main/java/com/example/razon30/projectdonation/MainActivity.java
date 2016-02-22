package com.example.razon30.projectdonation;

import android.content.Intent;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.github.aakira.expandablelayout.ExpandableLayout;


import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Page primary elements
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;


    //registration
    ExpandableLayout expandableLayout;
    BootstrapButton btnRegister,btnCancel,btnAgree;

    ArrayList<Integer> imageList;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;



    private AccountHeader headerResult;
    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        worksOnToolbar();
        worksOnFAB();
        worksOnDrawer(savedInstanceState);
        worksOnregister();
        worksOnRecycler();

        AwesomeTextView textView = (AwesomeTextView) findViewById(R.id.loginBtn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });



    }

    private void worksOnRecycler() {
        imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.cover);
        imageList.add(R.drawable.madrasa1);
        imageList.add(R.drawable.madrasa2);
        imageList.add(R.drawable.madrasa3);
        imageList.add(R.drawable.madrasa4);

        adapter = new RecyclerAdapter(imageList,MainActivity.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager
                .HORIZONTAL, false);

        recyclerView = (RecyclerView) findViewById(R.id.cover);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void worksOnregister() {

        expandableLayout = (ExpandableLayout) findViewById(R.id.expandableLayout);
        btnRegister = (BootstrapButton) findViewById(R.id.moreorless);
        btnCancel = (BootstrapButton) findViewById(R.id.cancel);
        btnAgree = (BootstrapButton) findViewById(R.id.agree);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.expand();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.collapse();
            }
        });

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });


    }

    private void worksOnDrawer(Bundle savedInstanceState) {


        IProfile profile = new ProfileDrawerItem().withName("Md. Razon Hossain").withEmail
                ("01671032440");

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.header)
                .withSavedInstance(savedInstanceState)
                .addProfiles(profile)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)

                .withToolbar(toolbar)
                .withFullscreen(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new SectionDrawerItem().withName(""),
                        new PrimaryDrawerItem().withName("Md. Razon Hossain").withIcon
                                (GoogleMaterial.Icon.gmd_person),
                        new SectionDrawerItem().withName("Activities"),
                        new PrimaryDrawerItem().withName("My Activity").withIcon
                                (GoogleMaterial.Icon.gmd_home),
                        new PrimaryDrawerItem().withName("Institute Activity").withIcon
                                (GoogleMaterial.Icon.gmd_school).withDescription("Only For " +
                                "Core Members"),
                        new PrimaryDrawerItem().withName("Donator Activity").withIcon
                                (GoogleMaterial.Icon.gmd_work),
                        new SectionDrawerItem().withName("About Donations"),
                        new SecondaryDrawerItem().withName("Recent Transaction")
                                .withIcon(GoogleMaterial.Icon.gmd_account_balance_wallet),
                        new SecondaryDrawerItem().withName("Monthly Report").withIcon
                                (GoogleMaterial.Icon.gmd_report),
                        new SecondaryDrawerItem().withName("My Donation History")
                                .withIcon(GoogleMaterial.Icon.gmd_history),
                        new SecondaryDrawerItem().withName("Donate").withIcon
                                (GoogleMaterial.Icon.gmd_attach_money)
                )
                .withSavedInstance(savedInstanceState)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (position == 1) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }else if (position == 2){
                            startActivity(new Intent(MainActivity.this, SignUp.class));
                        }

                        return false;
                    }
                })
                .build();




//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private void worksOnFAB() {



    }

    private void worksOnToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

//    @Override
//    public void onBackPressed() {
//
////        if (drawer.isDrawerOpen(GravityCompat.START)) {
////            drawer.closeDrawer(GravityCompat.START);
////        } else {
//            super.onBackPressed();
//        //}
//    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
