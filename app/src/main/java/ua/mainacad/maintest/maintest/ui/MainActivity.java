package ua.mainacad.maintest.maintest.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import ua.mainacad.maintest.maintest.R;
import ua.mainacad.maintest.maintest.ui.login.LoginActivity;
import ua.mainacad.maintest.maintest.ui.photos.PhotoListFragment;
import ua.mainacad.maintest.maintest.ui.posts.PostsListFragment;
import ua.mainacad.maintest.maintest.ui.users.UsersListFragment;
import ua.mainacad.maintest.maintest.workers.MyWorkerManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_LOGIN = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupInitialScreen();
    }

    private void setupInitialScreen() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            setFragment(new UsersListFragment());
        } else {
            login();
        }
    }

    private void login() {
        startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                setupInitialScreen();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            setFragment(new PreferenceFragment());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        View fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        if (id == R.id.nav_users) {
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            if (prefs.getBoolean(
                    getString(R.string.pref_key_auto_lock),
                    getResources().getBoolean(R.bool.auto_lock))) {
                Toast.makeText(
                        this,
                        prefs.getString(
                                getString(R.string.pref_key_change_password_hint),
                                getString(R.string.default_hint)),
                        Toast.LENGTH_SHORT)
                        .show();
            }
            final String tag = MyWorkerManager.scheduleJob();
            setFragment(new UsersListFragment());
        } else if (id == R.id.nav_posts) {
            setFragment(new PostsListFragment());
            fab.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_gallery) {
            setFragment(new PhotoListFragment());
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(this, MyLayoutActivity.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            recreate();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, fragment)
                .commit();
    }
}
