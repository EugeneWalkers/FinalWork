package ew.finalwork.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ew.finalwork.R;
import ew.finalwork.model.TestResult;
import ew.finalwork.model.User;
import ew.finalwork.receivers.NetworkChangeReceiver;
import ew.finalwork.utilities.DataUtility;
import ew.finalwork.viewmodel.MainViewModel;
import ew.finalwork.viewmodel.ViewModelFactory;


public class MainActivity extends AppCompatActivity {

    MainViewModel viewModel;


    public static final int REQUEST_OK = 1;
    public static final int REQUEST_RESULT = 2;

    private TestResult result;
    private FragmentTransaction fTrans;
    private FragmentManager fManager;
    private ProfileFragment profile;
    private TestsFragment tests;
    private int selectedItem;
    private User user;
    private Bundle userBundle;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = getIntent().getParcelableExtra(DataUtility.USER_INFO);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.setUser(user);
        userBundle = new Bundle();
        userBundle.putParcelable(DataUtility.USER_INFO, user);
        fManager = getSupportFragmentManager();
        fTrans = fManager.beginTransaction();
        setNavigationView();
        Observer<MenuItem> observerButtons = menuItem -> {
            int id = menuItem.getItemId();
            selectedItem = id;
            Fragment fragment = null;
            Class fragmentClass = null;
            switch (id) {
                default:
                case R.id.item_1:
                    fragmentClass = ProfileFragment.class;
                    viewModel.refreshResults();
                    break;

                case R.id.item_2:
                    fragmentClass = TestsFragment.class;
                    viewModel.refreshTestsList();
                    break;

                case R.id.item_3:
                    //fragmentClass = AdminPanelFragment.class;
                    break;

                case R.id.item_4:
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    FirebaseAuth.getInstance().signOut();
                    unregisterReceiver(NetworkChangeReceiver.getInstance());
                    break;
            }

            if (fragmentClass != null) {
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    fragment.setArguments(userBundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                fManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                ((Toolbar) findViewById(R.id.toolbar)).setTitle(menuItem.getTitle());
            }
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        };
        viewModel.getItemLiveData().observe(this, observerButtons);
        setToolbar();
        setToggle();
        profile = new ProfileFragment();
        tests = new TestsFragment();
        if (savedInstanceState == null) {
            fTrans.add(R.id.content_frame, profile);
            fTrans.commit();
            toolbar.setTitle(getResources().getString(R.string.profile));
            selectedItem = R.id.item_1;
        }

    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
//        MenuItem refresh = toolbar.findViewById(R.id.action_refresh);
//        refresh.setOnMenuItemClickListener(menuItem -> {
//            switch (selectedItem){
//                default:
//                case R.id.item_1:
//                    viewModel.refreshResults();
//                    break;
//                case R.id.item_2:
//                    viewModel.refreshTestsList();
//                    break;
//            }
//            return true;
//        });
    }

    private void setToggle() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (!user.getType().equals("admin")) {
            navigationView.getMenu().getItem(2).setEnabled(false);
        }
        View headerView = navigationView.getHeaderView(0);
        TextView navText = headerView.findViewById(R.id.header_text);
        navText.setText("Welcome, " + user.getUser().getEmail() + "!");
        navigationView.setNavigationItemSelectedListener(viewModel);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toolbar toolbar = findViewById(R.id.toolbar);
        switch (selectedItem) {
            default:
            case R.id.item_1:
                profile.setArguments(userBundle);
                fTrans.add(R.id.content_frame, profile);
                toolbar.setTitle(getResources().getString(R.string.profile));
                break;
            case R.id.item_2:
                fTrans.add(R.id.content_frame, tests);
                toolbar.setTitle(getResources().getString(R.string.tests));
                break;
        }
        fTrans.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_OK) {
            switch (requestCode) {
                case REQUEST_RESULT:
                    result = data.getParcelableExtra(TestViewActivity.RESULT);
                    viewModel.addResults(result);
                    Toast.makeText(this, "Your result: " + Double.valueOf(result.getTestResult()) * 100 + "%", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Toast.makeText(this, "Wrong result!", Toast.LENGTH_SHORT).show();
        }
    }

    public static MainViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MainViewModel.class);
    }

}
