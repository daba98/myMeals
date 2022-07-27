package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.adapter.IngredientListAdapter;
import de.die_bartmanns.daniel.mymeal.adapter.InstructionsListAdapter;
import de.die_bartmanns.daniel.mymeal.data.Ingredient;

public class AddMealIngredientsFragment extends Fragment {

    private AddMealMainActivity activity;

    private List<Ingredient> ingredients;
    private ListView ingrListview;
    private IngredientListAdapter adapter;
    private TextView nboPersonsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        activity = (AddMealMainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_add_meal_ingredients, container, false);
        FloatingActionButton addButton = view.findViewById(R.id.add_ingredient_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddIngredientDialog();
            }
        });

        nboPersonsView = view.findViewById(R.id.nbo_persons_textview);
        nboPersonsView.setText(String.valueOf(activity.getNboPersons()));
        ImageButton decreaseButton = view.findViewById(R.id.decrease_button);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nboPersons = Integer.parseInt(nboPersonsView.getText().toString());
                if(nboPersons != 1) {
                    nboPersonsView.setText(String.valueOf(nboPersons - 1));
                    activity.setNboPersons(nboPersons - 1);
                }
            }
        });
        ImageButton increaseButton = view.findViewById(R.id.increase_button);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nboPersons = Integer.parseInt(nboPersonsView.getText().toString());
                nboPersonsView.setText(String.valueOf(nboPersons + 1));
                activity.setNboPersons(nboPersons + 1);
            }
        });

        ingrListview = view.findViewById(R.id.ingredients_listview);
        ingredients = activity.getIngredients();
        adapter = new IngredientListAdapter(ingredients, this);
        ingrListview.setAdapter(adapter);

        return view;
    }

    private void showAddIngredientDialog(){
        final AddIngredientDialog addIngredientDialog = new AddIngredientDialog();
        addIngredientDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                List<Ingredient> addedIngredients = addIngredientDialog.getIngredients();
                if(!addedIngredients.isEmpty()) {
                    ingredients.addAll(addedIngredients);
                    adapter.notifyDataSetChanged();
                    activity.setIngredients(ingredients);
                }

            }
        });
        addIngredientDialog.show(getActivity().getFragmentManager(), "TEST");
    }

    public void clickedDelete(View view){
        int idx = ingrListview.getPositionForView(view);
        ingredients.remove(idx);
        activity.setIngredients(ingredients);
        adapter.notifyDataSetChanged();
    }
}
