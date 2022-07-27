package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.MealType;

public class AddMealTypeFragment extends Fragment {

    private AddMealMainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        activity = (AddMealMainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_add_meal_type, container, false);

        LinearLayout mainLayout = view.findViewById(R.id.main_layout);
        LinearLayout saladLayout = view.findViewById(R.id.salad_layout);
        LinearLayout soupLayout = view.findViewById(R.id.soup_layout);
        LinearLayout cakeLayout = view.findViewById(R.id.cake_layout);
        LinearLayout dessertLayout = view.findViewById(R.id.dessert_layout);
        LinearLayout otherLayout = view.findViewById(R.id.other_layout);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setType(MealType.MAIN);
            }
        });

        saladLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setType(MealType.SALAD);
            }
        });

        soupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setType(MealType.SOUP);
            }
        });

        cakeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setType(MealType.CAKE);
            }
        });

        dessertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setType(MealType.DESSERT);
            }
        });

        otherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseCategoryDialog();
            }
        });

        return view;
    }

    private void showChooseCategoryDialog(){
        final ChooseCategoryDialog chooseCategoryDialog = new ChooseCategoryDialog(null);
        chooseCategoryDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                String selectedCategoryName = chooseCategoryDialog.getSelectedCategoryName();
                if(selectedCategoryName != null){
                    activity.setType(MealType.OTHER);
                    activity.setOwnCategory(selectedCategoryName);
                }
            }
        });
        chooseCategoryDialog.show(getActivity().getFragmentManager(), "TEST");
    }
}
