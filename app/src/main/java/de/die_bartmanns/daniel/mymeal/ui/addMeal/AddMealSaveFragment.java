package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import de.die_bartmanns.daniel.mymeal.R;

public class AddMealSaveFragment extends Fragment {

    private AddMealMainActivity activity;

    private ImageButton meatButton;
    private ImageButton fishButton;
    private ImageButton vegetableButton;
    private ImageButton potatoButton;
    private ImageButton noodleButton;
    private ImageButton riceButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_add_meal_save, container, false);
        activity = (AddMealMainActivity) getActivity();

        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.saveMeal();
            }
        });

        meatButton = view.findViewById(R.id.meatImageButton);
        meatButton.setBackgroundResource(activity.isWithMeat() ? R.drawable.fleisch : R.drawable.fleisch_unchecked);
        meatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isWithMeat = activity.isWithMeat();
                activity.setIsWithMeat(!isWithMeat);
                meatButton.setBackgroundResource(isWithMeat ? R.drawable.fleisch_unchecked : R.drawable.fleisch);
            }
        });

        fishButton = view.findViewById(R.id.fishImageButton);
        fishButton.setBackgroundResource(activity.isWithFish() ? R.drawable.fisch : R.drawable.fisch_unchecked);
        fishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isWithFish = activity.isWithFish();
                activity.setIsWithFish(!isWithFish);
                fishButton.setBackgroundResource(isWithFish ? R.drawable.fisch_unchecked : R.drawable.fisch);
            }
        });

        vegetableButton = view.findViewById(R.id.vegetableImageButton);
        vegetableButton.setBackgroundResource(activity.isWithVegetable() ? R.drawable.gemuese : R.drawable.gemuese_unchecked);
        vegetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isWithVeg = activity.isWithVegetable();
                activity.setIsWithVegetable(!isWithVeg);
                vegetableButton.setBackgroundResource(isWithVeg ? R.drawable.gemuese_unchecked : R.drawable.gemuese);
            }
        });

        potatoButton = view.findViewById(R.id.potatoImageButton);
        potatoButton.setBackgroundResource(activity.isWithPotato() ? R.drawable.kartoffel : R.drawable.kartoffel_unchecked);
        potatoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isWithPotato = activity.isWithPotato();
                activity.setIsWithPotato(!isWithPotato);
                potatoButton.setBackgroundResource(isWithPotato ? R.drawable.kartoffel_unchecked : R.drawable.kartoffel);
            }
        });

        noodleButton = view.findViewById(R.id.noodlesImageButton);
        noodleButton.setBackgroundResource(activity.isWithNoodle() ? R.drawable.nudeln : R.drawable.nudeln_unchecked);
        noodleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isWithNoodle = activity.isWithNoodle();
                activity.setIsWithNoodle(!isWithNoodle);
                noodleButton.setBackgroundResource(isWithNoodle ? R.drawable.nudeln_unchecked : R.drawable.nudeln);
            }
        });

        riceButton = view.findViewById(R.id.riceImageButton);
        riceButton.setBackgroundResource(activity.isWithRice() ? R.drawable.reis : R.drawable.reis_unchecked);
        riceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isWithRice = activity.isWithRice();
                activity.setIsWithRice(!isWithRice);
                riceButton.setBackgroundResource(isWithRice ? R.drawable.reis_unchecked : R.drawable.reis);
            }
        });


        if(!activity.getType().hasFilter())
            hideFilter();


        return view;
    }

    private void hideFilter(){
        meatButton.setVisibility(View.GONE);
        fishButton.setVisibility(View.GONE);
        vegetableButton.setVisibility(View.GONE);
        potatoButton.setVisibility(View.GONE);
        noodleButton.setVisibility(View.GONE);
        riceButton.setVisibility(View.GONE);
    }
}
