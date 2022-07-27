package de.die_bartmanns.daniel.mymeal.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import de.die_bartmanns.daniel.mymeal.ItemClickSupport;
import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.SideMenu.ActivityWithSideMenu;
import de.die_bartmanns.daniel.mymeal.adapter.OnlineMealRecyclerViewAdapter;
import de.die_bartmanns.daniel.mymeal.adapter.RecyclerViewAdapter;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.FirestoreDataReceivingCallback;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.GetMealsResult;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineDatabaseService;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineDatabaseServiceFactory;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineDatabaseServiceImpl;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineMeal;

public class OnlineActivity extends ActivityWithSideMenu {

    private RecyclerView recyclerview;

    private List<OnlineMeal> onlineMeals = new ArrayList<>();
    private OnlineMealRecyclerViewAdapter onlineMealAdapter;

    private OnlineDatabaseService onlineService;

    private boolean isScrolling = false;
    private boolean isLastItemReached = false;
    private boolean isLoading = false;
    private DocumentSnapshot lastVisible = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_online, null, false);
        setLayout(contentView);

        recyclerview = findViewById(R.id.recyclerView);
        onlineMealAdapter = new OnlineMealRecyclerViewAdapter(this, onlineMeals);
        recyclerview.setAdapter(onlineMealAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        onlineService = OnlineDatabaseServiceFactory.getOnlineDatabaseService(this);
        loadMeals(null);

        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();

                if (isScrolling && (firstVisibleItemPosition + visibleItemCount == totalItemCount) && !isLastItemReached && !isLoading) {
                    isScrolling = false;
                    loadMeals(lastVisible);
                }
            }
        });

        ItemClickSupport.addTo(recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getBaseContext(), MealDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("onlineMeal", onlineMeals.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadMeals(DocumentSnapshot docSnap){
        isLoading = true;
        showLoading();
        onlineService.getMeals(docSnap, new FirestoreDataReceivingCallback<GetMealsResult>() {
            @Override
            public void onCallback(GetMealsResult result) {
                onlineMeals.addAll(result.getMeals());
                lastVisible = result.getLastVisible();
                stopLoading();
                onlineMealAdapter.notifyDataSetChanged();
                isLastItemReached = result.getMeals().size() < OnlineDatabaseServiceImpl.BUNCH_SIZE;
                isLoading = false;
            }
        });
    }

    private void showLoading(){
        onlineMeals.add(null);
        onlineMealAdapter.notifyDataSetChanged();
    }

    private void stopLoading(){
        onlineMeals.remove(null);
    }
}
