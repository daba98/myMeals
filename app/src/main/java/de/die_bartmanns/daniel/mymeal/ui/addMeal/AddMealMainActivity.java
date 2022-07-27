package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.Util;
import de.die_bartmanns.daniel.mymeal.adapter.PagerAdapter;
import de.die_bartmanns.daniel.mymeal.data.Difficulty;
import de.die_bartmanns.daniel.mymeal.data.Ingredient;
import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.data.MealType;
import de.die_bartmanns.daniel.mymeal.data.Recipe;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseService;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseServiceFactory;

public class AddMealMainActivity extends FragmentActivity {

    private ViewPager pager;
    private PagerAdapter mPagerAdapter;
    private int dotCount;
    private ImageView[] dots;

    private Meal meal;
    private boolean inUpdateMode;

    private RelativeLayout background;

    private DatabaseService service;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(meal != null){
            Gson gson = new Gson();
            String mealString = gson.toJson(meal);
            outState.putString("mealGson", mealString);
            outState.putBoolean("inUpdateMode", inUpdateMode);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal_main);
        initialisePaging();

        service = DatabaseServiceFactory.getDatabaseService(getApplicationContext());

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(savedInstanceState != null){
            Gson gson = new Gson();
            meal = gson.fromJson(savedInstanceState.getString("mealGson"), Meal.class);
            inUpdateMode = savedInstanceState.getBoolean("inUpdateMode");
        }
        else if(bundle != null) {
            long mealId = bundle.getLong("mealId");
            inUpdateMode = true;
            meal = service.getMeal(mealId);
        }
        else{
            inUpdateMode = false;
            meal = new Meal(MealType.MAIN);
        }

        background = findViewById(R.id.activity_add_meal_main);
        if(meal.getType() != null) {
            background.setBackgroundColor(getResources().getColor(meal.getColorResId()));
            Util.setStatusBarColor(this, meal.getColorDarkResId());
        }
    }

    private void initialisePaging() {
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this,AddMealTypeFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,AddMealBasicFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,AddMealTimeFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,AddMealDifficultyFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,AddMealRatingFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,AddMealIngredientsFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,AddMealInstructionFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, AddMealSaveFragment.class.getName()));

        mPagerAdapter =new PagerAdapter(this.getSupportFragmentManager(), fragments);

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(mPagerAdapter);
        initDots();
    }

    private void initDots(){
        LinearLayout sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        dotCount = mPagerAdapter.getCount();
        dots = new ImageView[dotCount];

        for(int i = 0; i < dotCount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotCount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public void setType(MealType type){
        meal.setType(type);
        background.setBackgroundColor(getResources().getColor(meal.getColorResId()));
        Util.setStatusBarColor(this, meal.getColorDarkResId());
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }

    public void setOwnCategory(String category){
        meal.setOwnCategory(category);
    }

    public void setTime(int time){
        meal.setTimeToCook(time);
    }

    public int getTime(){
        return meal.getTimeToCook();
    }

    public void setDiff(Difficulty diff){
        meal.setDifficulty(diff);
    }

    public Difficulty getDiff(){
        return meal.getDifficulty();
    }

    public void setRating(int rating){
        meal.setRating(rating);
    }

    public int getRating(){
        return meal.getRating();
    }

    public void setName(String name){
        meal.setName(name);
    }

    public String getName(){
        return meal.getName();
    }

    public void setPhotoByteArray(byte[] photo){
        meal.setPhotoByteArray(photo);
    }

    public byte[] getPhotoByteArray(){
        return meal.getPhotoByteArray();
    }

    public List<String> getInstructions(){
        Recipe recipe = meal.getRecipe();
        if(recipe == null)
            return new ArrayList<>();
        else
            return recipe.getInstructions();
    }

    public void setInstructions(List<String> instructions){
        Recipe recipe = meal.getRecipe();
        if(recipe == null){
            recipe = new Recipe();
        }
        recipe.setInstructions(instructions);
        meal.setRecipe(recipe);
    }

    public List<Ingredient> getIngredients(){
        Recipe recipe = meal.getRecipe();
        if(recipe == null)
            return new ArrayList<>();
        else
            return recipe.getIngredients();
    }

    public void setIngredients(List<Ingredient> ingredients){
        Recipe recipe = meal.getRecipe();
        if(recipe == null){
            recipe = new Recipe();
        }
        recipe.setIngredients(ingredients);
        meal.setRecipe(recipe);
    }

    public int getNboPersons(){
        Recipe recipe = meal.getRecipe();
        if(recipe == null)
            return Recipe.DEFAULT_NBO_PERSONS;
        else
            return recipe.getPersons();
    }

    public void setNboPersons(int nboPersons){
        Recipe recipe = meal.getRecipe();
        if(recipe == null){
            recipe = new Recipe();
        }
        recipe.setPersons(nboPersons);
        meal.setRecipe(recipe);
    }

    public boolean isWithMeat(){
        return meal.isWithMeat();
    }

    public boolean isWithFish(){
        return meal.isWithFish();
    }

    public boolean isWithVegetable(){
        return meal.isWithVegetables();
    }

    public boolean isWithPotato(){
        return meal.isWithPotatoes();
    }

    public boolean isWithNoodle(){
        return meal.isWithNoodles();
    }

    public boolean isWithRice(){
        return meal.isWithRice();
    }

    public void setIsWithMeat(boolean b){
        meal.setWithMeat(b);
    }

    public void setIsWithFish(boolean b){
        meal.setWithFish(b);
    }

    public void setIsWithVegetable(boolean b){
        meal.setWithVegetables(b);
    }

    public void setIsWithPotato(boolean b){
        meal.setWithPotatoes(b);
    }

    public void setIsWithNoodle(boolean b){
        meal.setWithNoodles(b);
    }

    public void setIsWithRice(boolean b){
        meal.setWithRice(b);
    }

    public MealType getType(){return meal.getType();}

    public void saveMeal(){
        if(meal.getName() == null) {
            Toast.makeText(this, getString(R.string.toast_no_name), Toast.LENGTH_LONG).show();
            return;
        }
        if(meal.getTimeToCook() == 0) {
            Toast.makeText(this, getString(R.string.taost_no_time), Toast.LENGTH_LONG).show();
            return;
        }

        if(inUpdateMode)
            service.updateMeal(meal);
        else
            service.insertMeal(meal);

        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        showOnBackPressDialog();
    }

    private void showOnBackPressDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(AddMealMainActivity.this).create();
        alertDialog.setTitle(getString(R.string.quit_dialog_title));
        alertDialog.setMessage(getString(R.string.quit_dialog_message));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.quit_dialog_positive_button),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AddMealMainActivity.super.onBackPressed();
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.quit_dialog_negative_button),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
