package de.die_bartmanns.daniel.mymeal.SideMenu;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.MealType;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseServiceFactory;
import de.die_bartmanns.daniel.mymeal.ui.ImportDialog;
import de.die_bartmanns.daniel.mymeal.ui.MainActivity;
import de.die_bartmanns.daniel.mymeal.ui.OnlineActivity;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.AddMealMainActivity;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.ChooseCategoryDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 07.01.2017.
 */
public abstract class ActivityWithSideMenu extends AppCompatActivity {

    private ExpandableListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    protected DrawerLayout mDrawerLayout;

    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private Map<NavItem, List<SubNavItem>> subItemMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sidebar_layout);
        createSideMenu();
    }

    private void createSideMenu() {
        // More info: http://codetheory.in/difference-between-setdisplayhomeasupenabled-sethomebuttonenabled-and-setdisplayshowhomeenabled/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.app_name));
        mNavItems.clear();
        subItemMap.clear();

        NavItem addItem = new NavItem("Neues Gericht", "Gericht hinzufügen", R.drawable.plus);
        mNavItems.add(addItem);
        subItemMap.put(addItem, new ArrayList<>());


        NavItem overviewItem = new NavItem("Meine Gerichte", "Übersicht über die Gerichte", R.drawable.ic_list_black_24dp);
        mNavItems.add(overviewItem);
        List<SubNavItem> overViewSubItems = new ArrayList<>();
        overViewSubItems.add(new SubNavItem(getResources().getString(R.string.all), R.drawable.default_photo));

        for (MealType type : MealType.values()) {
            if(!type.equals(MealType.OTHER))
                overViewSubItems.add(new SubNavItem(getResources().getString(type.getStringResId()), type.getPhotoResId()));
        }

        for(String categoryName : DatabaseServiceFactory.getDatabaseService(getApplicationContext()).getAllCategoryNames()){
            overViewSubItems.add(new SubNavItem(categoryName, MealType.OTHER.getPhotoResId()));
        }

        subItemMap.put(overviewItem, overViewSubItems);


        NavItem importItem = new NavItem("Gericht importieren", "Gericht aus Nachricht importieren", R.drawable.ic_drafts_black_24dp);
        mNavItems.add(importItem);
        subItemMap.put(importItem, new ArrayList<>());

        NavItem onlineItem = new NavItem("Online Bibliothek", "Neue Rezepte entdecken", R.drawable.ic_baseline_cloud_circle_24);
        mNavItems.add(onlineItem);
        subItemMap.put(onlineItem, new ArrayList<>());


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = findViewById(R.id.drawerPane);
        mDrawerList = findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems, subItemMap);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                expandableListView.collapseGroup(groupPosition);
                SubNavItem subNavItem =  (SubNavItem) adapter.getChild(groupPosition, childPosition);
                selectSubItemFromDrawer(groupPosition, childPosition, subNavItem.getmTitle());
                return false;
            }
        });
        mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                List<SubNavItem> subNavItems = subItemMap.get(mNavItems.get(groupPosition));
                if(subNavItems.size() == 0){
                    selectItemFromDrawer(groupPosition);
                }

                return false;
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
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

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void setLayout(View view){
        FrameLayout frame = findViewById(R.id.content_frame);
        frame.removeAllViews();
        frame.addView(view);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void selectItemFromDrawer(int position) {
        mDrawerLayout.closeDrawer(mDrawerPane);

        switch (position) {
            case 0: {
                Intent intent = new Intent(getApplicationContext(), AddMealMainActivity.class);
                startActivity(intent);

                break;
            }

            case 2: {
                mDrawerLayout.closeDrawer(mDrawerPane);
                showImportDialog();
                break;
            }

            case 3: {
                mDrawerLayout.closeDrawer(mDrawerPane);
                Intent intent = new Intent(getApplicationContext(), OnlineActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
        }
    }

    private void showImportDialog(){
        final ImportDialog importDialog = new ImportDialog();
        importDialog.show(getSupportFragmentManager(), "TEST");
    }

    private void selectSubItemFromDrawer(int groupPosition, int childPosition, String title){
        if(groupPosition != 1)
            return;

        mDrawerLayout.closeDrawer(mDrawerPane);

        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String className = am.getRunningTasks(1).get(0).topActivity.getClassName();


        if (className.equals("de.die_bartmanns.daniel.mymeal.ui.MainActivity")) {
            clickedSubItem(childPosition - 1, title);
        }

        else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("type", childPosition - 1);
            intent.putExtra("category", title);
            startActivity(intent);
        }
    }

    public void clickedSubItem(int childPosition, String title){};
}
