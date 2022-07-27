package de.die_bartmanns.daniel.mymeal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.Ingredient;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.AddMealIngredientsFragment;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.AddMealInstructionFragment;

public class IngredientListAdapter extends BaseAdapter {

    private final List<Ingredient> ingredients;
    private final LayoutInflater layoutInflater;
    private final AddMealIngredientsFragment fragment;

    public IngredientListAdapter(List<Ingredient> ingredients, AddMealIngredientsFragment fragment){
        this.ingredients = ingredients;
        this.fragment = fragment;
        layoutInflater = LayoutInflater.from(fragment.getContext());
    }


    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        return ingredients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IngredientViewHolder holder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.ingredient_item, null);
            holder = new IngredientViewHolder();
            holder.ingredientNameView = view.findViewById(R.id.ingredient_name_textview);
            holder.amountView = view.findViewById(R.id.ingredient_amount_textview);
            holder.deleteButton = view.findViewById(R.id.delete_button);
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.clickedDelete(view);
                }
            });

            view.setTag(holder);
        }
        else {
            holder = (IngredientViewHolder) view.getTag();
        }

        Ingredient ingredient = ingredients.get(i);
        holder.ingredientNameView.setText(ingredient.getName());
        if(isInteger(ingredient.getAmount()))
            holder.amountView.setText((int) ingredient.getAmount() + "\t" + fragment.getString(ingredient.getUnit().getStringResId()));
        else
            holder.amountView.setText(ingredient.getAmount() + "\t" + fragment.getString(ingredient.getUnit().getStringResId()));

        return view;
    }

    private boolean isInteger(float f){
        return (int)f == f;
    }


    private static class IngredientViewHolder {

        TextView ingredientNameView;
        TextView amountView;
        ImageButton deleteButton;
    }
}
