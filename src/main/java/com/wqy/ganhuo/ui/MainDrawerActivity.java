package com.wqy.ganhuo.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wqy.ganhuo.R;
import com.wqy.ganhuo.adapter.FragmentPageAdapter;
import com.wqy.ganhuo.base.BaseActivity;
import com.wqy.ganhuo.base.BaseFragment;
import com.wqy.ganhuo.model.FragmentPageItem;
import com.wqy.ganhuo.ui.fragment.AndroidFragment;
import com.wqy.ganhuo.ui.fragment.IOSFragment;
import com.wqy.ganhuo.ui.fragment.MeiziFragment;
import com.wqy.ganhuo.utils.ShowToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    private List<FragmentPageItem> fragmentPageItemList;
    private FragmentPageAdapter adapter;

    private long clickBackTime = 0;

    private OnRefreshListener onRefreshListener;

    public interface OnRefreshListener {
        void onRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        toolbar.setTitle(getString(R.string.app_name));
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setPadding(0, getStatusBarHeight(), 0, 0);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onRefreshListener != null) {
                    int index = viewPager.getCurrentItem();
                    Fragment fragment = adapter.getItem(index);
                    if(fragment instanceof AndroidFragment) {
                        ((AndroidFragment)fragment).onRefresh();
                    }
                    if(fragment instanceof IOSFragment) {
                        ((IOSFragment)fragment).onRefresh();
                    }
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /**
         * 以下两个方法调用顺序不能错，否则会出现异常，因为setupWithViewPager(viewPager)必须在
         * viewpager.setAdapter(adapter)之后
         */
        initData();
        initTabs();
    }

    private void initTabs() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initData() {
        if (fragmentPageItemList == null) {
            fragmentPageItemList = new ArrayList<FragmentPageItem>();
        } else {
            fragmentPageItemList.clear();
        }

        fragmentPageItemList.add(
                new FragmentPageItem("Android", 0, 0, AndroidFragment.class, null)
        );
        fragmentPageItemList.add(
                new FragmentPageItem("IOS", 0, 0, IOSFragment.class, null)
        );
        fragmentPageItemList.add(
                new FragmentPageItem("妹子", 0, 0, MeiziFragment.class, null)
        );
        adapter = new FragmentPageAdapter(getSupportFragmentManager(), fragmentPageItemList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.input_key_words));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if(listener!=null){
//                    listener.onSearch();
//                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (System.currentTimeMillis() - clickBackTime < 2000) {
            finish();
        } else {
            ShowToast.toastLong("再按一次退出干货");
            clickBackTime = System.currentTimeMillis();
        }
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public CoordinatorLayout getMainContent() {
        return this.mainContent;
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    @Override
    public int provideContentLayout() {
        return R.layout.activity_main_drawer;
    }

    @Override
    public boolean canGoBack() {
        return false;
    }
}
