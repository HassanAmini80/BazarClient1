package com.example.hassan.bazarclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hassan.bazarclient.Adapter.GoodAdapter;
import com.example.hassan.bazarclient.LoginActivity;
import com.example.hassan.bazarclient.Network.UserModelProvider;
import com.example.hassan.bazarclient.Network.UserModelService;
import com.example.hassan.bazarclient.R;
import com.example.hassan.bazarclient.models.ErrorModel;
import com.example.hassan.bazarclient.models.GoodModel;
import com.example.hassan.bazarclient.Network.FakeGoodProvider;
import com.example.hassan.bazarclient.Network.FakeGoodService;
import com.example.hassan.bazarclient.utility.AppPreferenceTools;
import com.example.hassan.bazarclient.utility.ErrorUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppPreferenceTools mAppPreferenceTools;
    private ImageView mImImageProfile;

    private GoodAdapter rvAdapter;
    private GoodAdapter mAdapter;
    private FakeGoodService mGService;
    private RecyclerView mRyGoods;
    SwipeRefreshLayout mySwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_list);
        mAppPreferenceTools = new AppPreferenceTools(this);

        if (mAppPreferenceTools.isAuthorized()) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAppPreferenceTools = new AppPreferenceTools(this);

        //load user image with Picasso
      /*  Picasso.with(this).load(mAppPreferenceTools.getImageProfileUrl())
                .transform(new CropCircleTransformation()).into(mImImageProfile);
*/
        //get the provider
        FakeGoodProvider provider = new FakeGoodProvider();
        mGService = provider.getGService();

        //config recycler view
        mRyGoods = (RecyclerView) findViewById(R.id.ry_goods);
        mRyGoods.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodAdapter(this, new GoodAdapter.GoodEventHandler() {
            @Override
            public void onGoodInfo(String goodId, int position) {
                Intent goodInfoIntent = new Intent(getBaseContext(), GoodInfoActivity.class);
                goodInfoIntent.putExtra(GoodConstants.ACTION_TO_DO_KEY,GoodConstants.GOOD_INFO);
                goodInfoIntent.putExtra(GoodConstants.GOOD_ID_KEY,goodId.toString());
                startActivityForResult(goodInfoIntent,GoodConstants.GOOD_INFO_REQUEST_CODE);
            }
        });


        mRyGoods.setAdapter(mAdapter);
        this.registerForContextMenu(mRyGoods);
        getGoodsFromServer();


        mySwipe = (SwipeRefreshLayout) findViewById(R.id.mySwipe);

        mySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getGoodsFromServer();

                mySwipe.setRefreshing(false);
            }
        });

        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        if(v.getId()==R.id.ry_goods){
            this.getMenuInflater().inflate(R.menu.menu_popup, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void getGoodsFromServer(){
        Call<List<GoodModel>> call = mGService.GetGoods();
        call.enqueue(new Callback<List<GoodModel>>() {
            @Override
            public void onResponse(Call<List<GoodModel>> call, Response<List<GoodModel>> response) {

                if (response.isSuccessful()){
                    mAdapter.updateAdapterData(response.body());
                    mAdapter.notifyDataSetChanged();
                }else {
                    ErrorModel errorModel = ErrorUtils.parseError(response);
                    Toast.makeText(getBaseContext(), "Error type is " + errorModel.type + " , description " + errorModel.description, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GoodModel>> call, Throwable t) {

                Toast.makeText(getBaseContext(), "Fail it >>" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


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
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.menu_popup, menu);
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

        if (id == R.drawable.ic_home_white_24dp) {
            // Handle the camera action

            setContentView(R.layout.nav_header_main);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id == R.id.nav_log_out) {
//send request to server to terminate this application
            UserModelProvider userProvider = new UserModelProvider();
            //Call api route
            UserModelService userService = userProvider.getUService();
            Call<Boolean> call = userService.terminateApp();
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    //remove all authentication information such as accessToken and others
                    mAppPreferenceTools.removeAllPrefs();
                    //navigate to sign in activity
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    //finish this
                    finish();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
