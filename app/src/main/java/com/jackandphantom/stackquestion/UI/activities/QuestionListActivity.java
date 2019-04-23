package com.jackandphantom.stackquestion.UI.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import com.google.android.material.tabs.TabLayout;
import com.jackandphantom.stackquestion.R;
import com.jackandphantom.stackquestion.UI.fragment.QuestionFragment;
import com.jackandphantom.stackquestion.adapter.TabAdapter;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
/*
* Activity shows the list of questions on tab view which inflated viewpager
* added other componenet also for changing the tag
* Call the api regularly for different section.
* */
public class QuestionListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private String selectedTag;
    private ViewPager viewPager;
    //Added the custom category which will the stackoverflow api for different section
    private String category[] = {"Activity", "Hot", "Week", "Month"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        final String[] tags = getIntent().getStringArrayExtra("SELECTED_TAG");
        selectedTag = tags[0];
        setUpViewPager();
        tabLayout.setupWithViewPager(viewPager);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView t1 = navigationView.findViewById(R.id.textView);
        TextView t2 = navigationView.findViewById(R.id.textView2);
        TextView t3 = navigationView.findViewById(R.id.textView3);
        TextView t4 = navigationView.findViewById(R.id.textView4);
        t1.setText(tags[0]);
        t2.setText(tags[1]);
        t3.setText(tags[2]);
        t4.setText(tags[3]);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTag = tags[0];
                drawer.closeDrawer(GravityCompat.START);
                setUpViewPager();

            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTag = tags[1];
                drawer.closeDrawer(GravityCompat.START);
                setUpViewPager();
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTag = tags[2];
                drawer.closeDrawer(GravityCompat.START);
                setUpViewPager();
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTag = tags[3];
                drawer.closeDrawer(GravityCompat.START);
                setUpViewPager();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Added the three fragment in the viewpager
    private void setUpViewPager() {
        adapter.clearFragments();
        adapter.addFragment(new QuestionFragment(), category[0], selectedTag);
        adapter.addFragment(new QuestionFragment(), category[1], selectedTag);
        adapter.addFragment(new QuestionFragment(), category[2], selectedTag);
        adapter.addFragment(new QuestionFragment(), category[3], selectedTag);
        viewPager.setAdapter(adapter);
    }

    //Added the condition when user have opend navigation then it should first closed that and then
    //call the on backPressd().
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question_list, menu);
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
