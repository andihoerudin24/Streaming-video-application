package com.tutorkomputer.stremingapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorkomputer.stremingapp.adapter.MainAdapter;
import com.tutorkomputer.stremingapp.data.model.CallResponse;
import com.tutorkomputer.stremingapp.data.model.Video;
import com.tutorkomputer.stremingapp.rest.Api;
import com.tutorkomputer.stremingapp.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private GridView gridView;
    private EditText editText;
    private ProgressBar progressBar;
    private TextView textView;
    private Menu menu;
    private boolean grid = true;

    public  static  String VIDEO_CATEGORY= "";

    private void getVideo(String title, String category)
    {
        ApiInterface apiInterface= Api.getUrl().create(ApiInterface.class);
        Call<CallResponse> call;
        if(category == ""){
            call = apiInterface.getVideo(title);

        }else{
            call = apiInterface.postCategory(category);
        }

        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                final List<Video> videoList= response.body().getVideos();
                for (int i=0;i< videoList.size(); i++ ){
                    if (videoList.size()>0){
                          textView.setVisibility(View.GONE);
                          progressBar.setVisibility(View.GONE);
                          gridView.setVisibility(View.VISIBLE);
                          gridView.setAdapter(new MainAdapter(MainActivity.this, videoList));
                          gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                              @Override
                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                  Intent intent =new Intent(MainActivity.this,ListActivity.class);
                                  intent.putExtra("VIDEO_TITLE",videoList.get(position).getTitle());
                                  intent.putExtra("VIDEO_ID",videoList.get(position).getVideo_id());
                                  startActivity(intent);
                              }
                          });
                    }else{

                        progressBar.setVisibility(View.GONE);
                        gridView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                    Log.e("VIDEO_SUMMARY",videoList.get(i).getSummary());

                }
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();

        if (!VIDEO_CATEGORY.equals("")){
            editText.setText("");
            getVideo("",VIDEO_CATEGORY);
            VIDEO_CATEGORY="";
        }
    }
    public static String ANDROID_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ANDROID_ID= Settings.Secure.getString(this.getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        Log.e("log_androidid",ANDROID_ID);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        gridView=findViewById(R.id.gridview);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textview);
        progressBar=findViewById(R.id.progressbar);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                   getVideo(editText.getText().toString(),"");
                   return true;
                }
                return false;
            }
        });


        getVideo("","");
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
        getMenuInflater().inflate(R.menu.menu_custom, menu);
        this.menu =menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list) {
              if (grid){
                gridView.setNumColumns(1);
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_menu_grid));
                grid = false;
              }else{
                  gridView.setNumColumns(2);
                  menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_menu_list));
                  grid = true;
              }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            editText.setText("");
            getVideo("","");
        } else if (id == R.id.nav_categori) {
            //Toast.makeText(getApplicationContext(),"Categori",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this,CategoryActivity.class));

        } else if (id == R.id.nav_like) {
//          Toast.makeText(getApplicationContext(),"Likes",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this,LikeActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
