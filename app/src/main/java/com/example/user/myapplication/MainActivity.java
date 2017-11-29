package com.example.user.myapplication;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.user.myapplication.adapter.ThreeLevelListAdapter;
import com.example.user.myapplication.model.ParentCatData;
import com.example.user.myapplication.model.ParentResponse;
import com.example.user.myapplication.model.SubChildCatData;
import com.example.user.myapplication.model.childCatData;
import com.example.user.myapplication.rest.ApiClient;
import com.example.user.myapplication.rest.ApiInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String[] parent = new String[]{"What is View?", "What is  Layout?", "What is Dynamic Views?"};
    String[] q1 = new String[]{"List View", "Grid View"};
    String[] q2 = new String[]{"Linear Layout", "Relative Layout"};
    String[] q3 = new String[]{"Recycle View"};
    String[] des1 = new String[]{"A layout that organizes its children into a single horizontal or vertical row. It creates a scrollbar if the length of the window exceeds the length of the screen."};
    String[] des2 = new String[]{"Enables you to specify the location of child objects relative to each other (child A to the left of child B) or to the parent (aligned to the top of the parent)."};
    String[] des3 = new String[]{"This list contains linear layout information"};
    String[] des4 = new String[]{"This list contains relative layout information,Displays a scrolling grid of columns and rows"};
    String[] des5 = new String[]{"Under the RecyclerView model, several different components work together to display your data. Some of these components can be used in their unmodified form; for example, your app is likely to use the RecyclerView class directly. In other cases, we provide an abstract class, and your app is expected to extend it; for example, every app that uses RecyclerView needs to define its own view holder, which it does by extending the abstract RecyclerView.ViewHolder class."};

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    /**
     * Second level array list
     */
    List<List<childCatData>> secondLevel = new ArrayList<>();
    /**
     * Inner level data
     */
    List<LinkedHashMap<String, List<String>>> data = new ArrayList<>();
        ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("JD");
       /* actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableListView = (ExpandableListView) findViewById(R.id.expandible_listview);
        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header_main, null, false);
        expandableListView.addHeaderView(listHeaderView);


        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }




    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        ConnectionDetector cd=new ConnectionDetector(MainActivity.this);
        if(cd.isConnectingToInternet())
        {
            getData();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No Internet Connection.Please Turn on WIFI/MobileData",Toast.LENGTH_LONG).show();
        }
    }
    private void getData()
    {
        // using retrofit fetching data from server.
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ParentResponse> call = apiService.getResult();
        call.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                //parent array contains list of categories.



                List<ParentCatData> parentarray = response.body().getNavparent();
                for (int i = 0; i < parentarray.size(); i++)

                {
                    Log.d("parent", parentarray.get(i).getNavName());
                    //childarray contains list of children for a perticular category
                    List<childCatData> childarray = response.body().getNavparent().get(i).getChildcatarray();
                    //for third level.
                    LinkedHashMap<String, List<String>> thirdLevel = new LinkedHashMap<>();
                    ;
                    for (int j = 0; j < childarray.size(); j++) {
                        List<String> inner = new ArrayList<String>();
                        Log.d("child", childarray.get(j).getNavNamechild());
                        //childarray contains list of children for a perticular category
                        List<SubChildCatData> subchildarray = response.body().getNavparent().get(i).getChildcatarray().get(j).getSubchildcatarray();
                        //checking the condition wheather the sub child object is present or not.
                        if (subchildarray == null) {
                            thirdLevel.put(childarray.get(j).getNavNamechild(), inner);
                        } else {
                            for (int k = 0; k < subchildarray.size(); k++) {
                                Log.d("subchild", subchildarray.get(k).getNavnamesubchild());
                                //adding the subchild name to an array.
                                inner.add(subchildarray.get(k).getNavnamesubchild());
                            }
                            //add the set of third level objects with key of second level particular child.
                            thirdLevel.put(childarray.get(j).getNavNamechild(), inner);
                        }
                    }
                    //adding the set of third level object with key of second leve particluar child to array list.
                    data.add(thirdLevel);

                    secondLevel.add(childarray);


                }
                ThreeLevelListAdapter threeLevelListAdapterAdapter = new ThreeLevelListAdapter(MainActivity.this, parentarray, secondLevel, data);
                expandableListView.setAdapter(threeLevelListAdapterAdapter);
                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)
                            expandableListView.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }
                });

            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });
    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                Log.d("pressed","presseed");
                super.onDrawerOpened(drawerView);
                //  getSupportActionBar().setTitle(R.string.film_genres);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }



      @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



//    private void setupDrawer() {
//        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//              //  getSupportActionBar().setTitle(R.string.film_genres);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//              //  getSupportActionBar().setTitle(mActivityTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//
//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//      //  mDrawerToggle.syncState();
//        drawer.setDrawerListener(mDrawerToggle);
//    }
    //@Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }

}
