package de.die_bartmanns.daniel.mymeal.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import de.die_bartmanns.daniel.mymeal.BitmapUtil;
import de.die_bartmanns.daniel.mymeal.FullLengthListView;
import de.die_bartmanns.daniel.mymeal.Util;
import de.die_bartmanns.daniel.mymeal.adapter.InstructionsDetailsListAdapter;
import de.die_bartmanns.daniel.mymeal.data.Ingredient;
import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.MealType;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseService;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseServiceFactory;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.MealToOnlineMealConverter;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineDatabaseService;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineDatabaseServiceFactory;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineMeal;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.AddMealMainActivity;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.ChooseCategoryDialog;

import java.util.List;

/**
 * Created by Daniel on 07.01.2017.
 */
public class MealDetailsActivity extends AppCompatActivity{

    private TextView name;
    private TextView timeTextView;
    private TextView difficultyTextView;
    private TextView detailsBackground;
    private TextView personTextView;
    private TextView ingredientStaticTextView;
    private TextView instructionStaticTextView;

    private FullLengthListView instructionsListView;

    private ImageView[] stars = new ImageView[5];
    private ImageView meatView;
    private ImageView fishView;
    private ImageView vegetableView;
    private ImageView potatoView;
    private ImageView noodleView;
    private ImageView riceView;
    private ImageView photoView;
    private ImageView photoViewBlur;

    private ImageButton minusButton;
    private ImageButton plusButton;

    private LinearLayout symbolLayout;

    private Meal meal;
    private long mealId;
    private int nboPersons;
    private InstructionsDetailsListAdapter instructionsAdapter;

    private DatabaseService service;
    private OnlineDatabaseService onlineDatabaseService;

    private boolean onlineMode;

    @Override
    protected void onResume() {
        super.onResume();
        if(!onlineMode) {
            meal = service.getMeal(mealId);
        }

        Util.setStatusBarColor(this, meal.getColorDarkResId());
        initialiseUIComponents();

        fillMealInfo();
        fillIngredientsView();
        fillInstructionsView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        setTitle(getString(R.string.app_name));

        service = DatabaseServiceFactory.getDatabaseService(getApplicationContext());
        onlineDatabaseService = OnlineDatabaseServiceFactory.getOnlineDatabaseService(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        OnlineMeal onlineMeal = (OnlineMeal) bundle.getSerializable("onlineMeal");
        if(onlineMeal == null){
            mealId = bundle.getLong("mealId");
            onlineMode = false;
        }
        else {
            onlineMode = true;
            meal = MealToOnlineMealConverter.toMeal(onlineMeal);
        }
    }

    private void fillMealInfo() {
        name.setText(meal.getName());
        name.setBackgroundResource(meal.getColorResId());
        timeTextView.setText(String.valueOf(meal.getTimeToCook()));
        difficultyTextView.setText(getString(meal.getDifficulty().getStringResId()));
        stars[0].setImageResource(meal.getRating() >= 1 ? R.drawable.ic_full_star : R.drawable.ic_empty_star);
        stars[1].setImageResource(meal.getRating() >= 2 ? R.drawable.ic_full_star : R.drawable.ic_empty_star);
        stars[2].setImageResource(meal.getRating() >= 3 ? R.drawable.ic_full_star : R.drawable.ic_empty_star);
        stars[3].setImageResource(meal.getRating() >= 4 ? R.drawable.ic_full_star : R.drawable.ic_empty_star);
        stars[4].setImageResource(meal.getRating() >= 5 ? R.drawable.ic_full_star : R.drawable.ic_empty_star);
        detailsBackground.setBackgroundResource(meal.getColorDarkResId());
        personTextView.setText(meal.getRecipe().getPersons() +" " + getString(R.string.persons));
        nboPersons = meal.getRecipe().getPersons();

        if(meal.getPhotoByteArray() == null){
            photoView.setImageResource(meal.getDefaultImageResId());
            photoViewBlur.setMaxHeight(R.dimen.image_detail_height);
            photoViewBlur.setBackgroundColor(getResources().getColor(meal.getColorResId()));
        }
        else{
            photoView.setImageBitmap(BitmapUtil.byteArrayToBitmap(meal.getPhotoByteArray()));
            photoViewBlur.setImageBitmap(BitmapUtil.blur(BitmapUtil.byteArrayToBitmap(meal.getPhotoByteArray()), this));
        }

        if(meal.getType().hasFilter()) {
            meatView.setImageResource(meal.isWithMeat() ? R.drawable.fleisch : R.drawable.fleisch_unchecked);
            fishView.setImageResource(meal.isWithFish() ? R.drawable.fisch : R.drawable.fisch_unchecked);
            vegetableView.setImageResource(meal.isWithVegetables() ? R.drawable.gemuese : R.drawable.gemuese_unchecked);
            potatoView.setImageResource(meal.isWithPotatoes() ? R.drawable.kartoffel : R.drawable.kartoffel_unchecked);
            noodleView.setImageResource(meal.isWithNoodles() ? R.drawable.nudeln : R.drawable.nudeln_unchecked);
            riceView.setImageResource(meal.isWithRice() ? R.drawable.reis : R.drawable.reis_unchecked);
            symbolLayout.setBackgroundColor(getResources().getColor(meal.getColorDarkResId()));
        }
        else {
            ViewGroup.LayoutParams params = symbolLayout.getLayoutParams();
            params.height = 0;
            symbolLayout.setLayoutParams(params);
        }

        minusButton.setBackgroundTintList(getResources().getColorStateList(meal.getType().getColorResId()));
        plusButton.setBackgroundTintList(getResources().getColorStateList(meal.getType().getColorResId()));

        ingredientStaticTextView.setTextColor(getResources().getColor(meal.getType().getDarkColorResId()));
        instructionStaticTextView.setTextColor(getResources().getColor(meal.getType().getDarkColorResId()));
    }

    private void initialiseUIComponents() {
        name = (TextView) findViewById(R.id.mealDetailsNameView);
        timeTextView = (TextView) findViewById(R.id.timeToCookTextView);
        difficultyTextView = (TextView) findViewById(R.id.difficultyView);
        instructionsListView =  findViewById(R.id.instructionsListView);
        meatView = (ImageView) findViewById(R.id.meatImageView);
        fishView = (ImageView) findViewById(R.id.fishImageView);
        vegetableView = (ImageView) findViewById(R.id.vegetableImageView);
        potatoView = (ImageView) findViewById(R.id.potatoImageView);
        noodleView = (ImageView) findViewById(R.id.noodlesImageView);
        riceView = (ImageView) findViewById(R.id.riceImageView);
        stars[0] = (ImageView) findViewById(R.id.star1);
        stars[1] = (ImageView) findViewById(R.id.star2);
        stars[2] = (ImageView) findViewById(R.id.star3);
        stars[3] = (ImageView) findViewById(R.id.star4);
        stars[4] = (ImageView) findViewById(R.id.star5);
        photoView = (ImageView) findViewById(R.id.photoView);
        photoViewBlur = (ImageView) findViewById(R.id.photoViewBlur);
        symbolLayout = (LinearLayout) findViewById(R.id.symbolLayout);
        detailsBackground = (TextView) findViewById(R.id.detailsBackground);
        personTextView = (TextView) findViewById(R.id.personTextView);
        minusButton = findViewById(R.id.minusPersonButton);
        plusButton = findViewById(R.id.addPersonButton);
        ingredientStaticTextView = findViewById(R.id.ingredient_static_textview);
        instructionStaticTextView = findViewById(R.id.instruction_static_textview);
    }

    private void fillInstructionsView() {
        List<String> instructions = meal.getRecipe().getInstructions();
        instructionsAdapter = new InstructionsDetailsListAdapter(instructions, getApplicationContext(), meal.getType());
        instructionsListView.setAdapter(instructionsAdapter);
    }

    private void fillIngredientsView(){
        TableLayout table =(TableLayout)findViewById(R.id.ingredientsLayout);
        table.removeAllViews();
        List<Ingredient> ingredients = meal.getRecipe().getIngredients();
        float mealPersons = meal.getRecipe().getPersons();
        String ingredientsAmountText = "";
        for(Ingredient ingredient : ingredients){
            String ingredientsNameText = ingredient.getName();
            float amount = mealPersons != 0 ? ingredient.getAmount() * (nboPersons / mealPersons) : ingredient.getAmount();
            if(isInteger(amount))
                ingredientsAmountText = (int) amount + "\t" + getString(ingredient.getUnit().getStringResId());
            else
                ingredientsAmountText = round(amount) + "\t" + getString(ingredient.getUnit().getStringResId());


            addRowToTableLayout(ingredientsNameText, ingredientsAmountText);
        }
    }

    private float round(float f){
        int i = (int) (f * 100);
        return i / 100.0f;
    }

    private boolean isInteger(float f){
        return (int)f == f;
    }

    private void addRowToTableLayout(String ingredientText, String amountText){
        TableLayout table =(TableLayout)findViewById(R.id.ingredientsLayout);
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        TextView textview = new TextView(this);
        textview.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        textview.setText(ingredientText);
        textview.setTextColor(getResources().getColor(R.color.black));
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        row.addView(textview);

        TextView textview2 = new TextView(this);
        textview2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textview2.setText(amountText);
        textview2.setTextColor(getResources().getColor(R.color.black));
        textview2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        textview2.setGravity(Gravity.END);
        row.addView(textview2);

        table.addView(row);
    }

    private void clickedEdit(){
        Intent intent = new Intent(getApplicationContext(), AddMealMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("mealId", meal.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void clickedAddPerson(View view){
        if(nboPersons != 0) {
            nboPersons++;
            personTextView.setText(nboPersons + " " + getString(R.string.persons));
            TableLayout table =(TableLayout)findViewById(R.id.ingredientsLayout);
            table.removeAllViews();
            fillIngredientsView();
        }
    }

    public void clickedMinusPerson(View view){
        if(nboPersons > 1){
            nboPersons--;
            personTextView.setText(nboPersons + " " + getString(R.string.persons));
            TableLayout table =(TableLayout)findViewById(R.id.ingredientsLayout);
            table.removeAllViews();
            fillIngredientsView();
        }
    }

    private void clickedUpload(){
        if(meal.getDocumentId() == null)
            onlineDatabaseService.uploadMeal(meal);
        else
            Toast.makeText(getApplicationContext(), "Das Gericht ist schon veröffentlicht", Toast.LENGTH_LONG).show();
    }

    private void clickedDownload(){
        if(meal.getType() == MealType.OTHER)
            showChooseCategoryDialog();
        else
            onlineDatabaseService.downloadMeal(meal.getDocumentId(), null);
    }

    private void showChooseCategoryDialog(){
        final ChooseCategoryDialog chooseCategoryDialog = new ChooseCategoryDialog(meal.getName());
        chooseCategoryDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                String selectedCategoryName = chooseCategoryDialog.getSelectedCategoryName();
                if(selectedCategoryName != null){
                    onlineDatabaseService.downloadMeal(meal.getDocumentId(), selectedCategoryName);
                }
            }
        });
        chooseCategoryDialog.show(getFragmentManager(), "TEST");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(onlineMode)
            inflater.inflate(R.menu.detail_online_meanu, menu);
        else
            inflater.inflate(R.menu.detailmenu, menu);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(meal.getColorResId())));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_edit:
                clickedEdit();
                return true;

            case R.id.action_upload:
                clickedUpload();
                return true;

            case R.id.action_download:
                clickedDownload();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
