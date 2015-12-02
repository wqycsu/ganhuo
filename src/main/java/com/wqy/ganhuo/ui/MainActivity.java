package com.wqy.ganhuo.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wqy.ganhuo.R;
import com.wqy.ganhuo.base.BaseActivity;
import com.wqy.ganhuo.base.ContentSearchable;
import com.wqy.ganhuo.ui.fragment.AndroidFragment;
import com.wqy.ganhuo.ui.fragment.MenuFragment;
import com.wqy.ganhuo.utils.ShowToast;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @deprecated
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private ContentSearchable listener;
    private long clickBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        toolbar = ButterKnife.findById(this, R.id.toolbar);
        toolbar.setTitle("Android");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        Fragment mMenuFragment = getSupportFragmentManager().findFragmentById(R.id.container_menu);
        Fragment mAndroidFragment = getSupportFragmentManager().findFragmentById(R.id.container_main);
        if(mMenuFragment==null){
            mMenuFragment = MenuFragment.newInstance();
        }
        if(mAndroidFragment==null){
            mAndroidFragment = AndroidFragment.newInstance();
        }
        replaceFragment(mMenuFragment, R.id.container_menu);
        replaceFragment(mAndroidFragment, R.id.container_main);
    }

    public void setContentSearchListener(ContentSearchable listener){
        this.listener = listener;
    }

    private void initData() {

    }

    public Toolbar getToolbar(){
        return this.toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.input_key_words));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if(listener!=null){
//                    listener.onSearch();
//                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(listener!=null){
                    listener.onSearch();
                }
                return true;
            }
        });
        return true;
    }

    public void replaceFragment(Fragment fragment, int fragmentId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(fragmentId, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void closeDrawer(){
        drawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            closeDrawer();
            return;
        }
        if(System.currentTimeMillis()-clickBackTime<2000){
            finish();
        }else{
            ShowToast.toastLong("再按一次退出干货");
            clickBackTime = System.currentTimeMillis();
        }
    }
}
