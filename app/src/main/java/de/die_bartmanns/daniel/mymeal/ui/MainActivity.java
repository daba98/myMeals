package de.die_bartmanns.daniel.mymeal.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import de.die_bartmanns.daniel.mymeal.ItemClickSupport;
import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.adapter.RecyclerViewAdapter;
import de.die_bartmanns.daniel.mymeal.SideMenu.ActivityWithSideMenu;
import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.data.MealType;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseService;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseServiceFactory;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.FirestoreDataReceivingCallback;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineDatabaseService;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineDatabaseServiceFactory;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.AddMealMainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActivityWithSideMenu{

    private RecyclerView mealRecyclerView;
    private MenuItem deleteItem;
    private MenuItem editItem;
    private MenuItem emailItem;
    private boolean isOnLongClick = false;
    private List<View> selectedViewsList = new ArrayList<>();
    private List<Integer> selectedMealPositionsList = new ArrayList<>();

    private List<Long> mealIdList = new ArrayList<>();
    private RecyclerViewAdapter mealAdapter;

    private DatabaseService service;
    private int typeIdx;
    private String category;

    private OnlineDatabaseService onlineService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        setLayout(contentView);

        typeIdx = getIntent().getIntExtra("type", -1);
        category = getIntent().getStringExtra("category");

        service = DatabaseServiceFactory.getDatabaseService(getApplicationContext());
        onlineService = OnlineDatabaseServiceFactory.getOnlineDatabaseService(this);

        initRecyclerView();
        animateFab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mealIdList.clear();
        if(typeIdx < 0)
            mealIdList.addAll(service.getAllMealIds());
        else if(typeIdx < MealType.OTHER.ordinal())
            mealIdList.addAll(service.getAllMealIdsWithType(MealType.values()[typeIdx]));
        else
            mealIdList.addAll(service.getAllMealIdsWithCategory(category));

        mealAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        mealRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mealAdapter = new RecyclerViewAdapter(this, mealIdList);
        mealRecyclerView.setAdapter(mealAdapter);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemClickSupport.addTo(mealRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if(isOnLongClick){
                    hideToolbarIcons();
                    clearSelectedLists();
                }
                else {
                    Intent intent = new Intent(getBaseContext(), MealDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("mealId", mealIdList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        ItemClickSupport.addTo(mealRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                View longClickView = v.findViewById(R.id.longClickView);
                if(!selectedMealPositionsList.contains(position)) {
                    longClickView.setBackgroundColor(getResources().getColor(R.color.longClickBlack));
                    selectedViewsList.add(v);
                    deleteItem.setVisible(true);
                    editItem.setVisible(true);
                    emailItem.setVisible(true);
                    isOnLongClick = true;
                    selectedMealPositionsList.add(position);
                }
                else{
                    deleteItem.setVisible(false);
                    editItem.setVisible(false);
                    emailItem.setVisible(false);
                    longClickView.setBackgroundColor(Color.TRANSPARENT);
                    selectedMealPositionsList.remove((Integer) position);
                    selectedViewsList.remove(v);
                }
                return true;
            }
        });
    }

    private void hideToolbarIcons() {
        deleteItem.setVisible(false);
        editItem.setVisible(false);
        emailItem.setVisible(false);
        for(View view : selectedViewsList)
            view.setBackgroundColor(Color.WHITE);
        isOnLongClick = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        deleteItem = menu.findItem(R.id.action_delete);
        deleteItem.setVisible(false);
        editItem = menu.findItem(R.id.action_edit);
        editItem.setVisible(false);
        emailItem = menu.findItem(R.id.action_email);
        emailItem.setVisible(false);
        return true;
    }

    public void clickedDelete(){
        for(Integer i : selectedMealPositionsList) {
            long mealId = mealIdList.get(i);
            service.deleteMeal(mealId);
            mealIdList.remove(i.intValue());
        }
        hideToolbarIcons();
        clearSelectedLists();
        mealAdapter.notifyDataSetChanged();
    }

    public void clickedEdit(){
        if(selectedMealPositionsList.size() != 1){
            Toast.makeText(MainActivity.this, "You can only edit a single meal at a time", Toast.LENGTH_LONG).show();
            return;
        }
        hideToolbarIcons();
        Intent intent = new Intent(getApplicationContext(), AddMealMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("mealId", mealIdList.get(selectedMealPositionsList.get(0)));
        intent.putExtras(bundle);
        clearSelectedLists();
        startActivity(intent);
    }

    public void clickedEmail(){

        List<Meal> selectedMeals = new ArrayList<>();
        for(Integer i : selectedMealPositionsList)
            selectedMeals.add(service.getMeal(mealIdList.get(i)));

        onlineService.uploadMeals(selectedMeals, new FirestoreDataReceivingCallback<List<String>>() {
            @Override
            public void onCallback(List<String> docRefs) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_pre_text) + "\n\n" + TextUtils.join(",", docRefs));

                try {
                    startActivity(Intent.createChooser(emailIntent, "Sende Nachricht..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "Es sind keine passenden Clients installiert, weshalb die Nachricht nicht versendet werden kann.", Toast.LENGTH_LONG).show();
                }
                hideToolbarIcons();
                clearSelectedLists();
            }
        });
    }

    private void clearSelectedLists(){
        for(View v : selectedViewsList){
            View view = v.findViewById(R.id.longClickView);
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        selectedMealPositionsList.clear();
        selectedViewsList.clear();
    }

    private void animateFab() {
        ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1, .5f, .5f);
        anim.setDuration(300);
        anim.setFillAfter(true);
        anim.setFillBefore(true);
        anim.setFillEnabled(true);
        anim.setStartOffset(300);


        FloatingActionButton fab = findViewById(R.id.floatingButton);
        OvershootInterpolator interpolator = new OvershootInterpolator();
        anim.setInterpolator(interpolator);
        fab.startAnimation(anim);
    }

    public void clickedFloatingButton(View view){
        Intent intent = new Intent(getApplicationContext(), AddMealMainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete)
            clickedDelete();
        else if(item.getItemId() == R.id.action_edit)
            clickedEdit();
        else if(item.getItemId() == R.id.action_email)
            clickedEmail();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickedSubItem(int childPosition, String category) {
        typeIdx = childPosition;
        this.category = category;
        onResume();
    }

    @Override
    public void onBackPressed() {
        if(isOnLongClick){
            hideToolbarIcons();
            clearSelectedLists();
        }
        else
            super.onBackPressed();
    }
}
