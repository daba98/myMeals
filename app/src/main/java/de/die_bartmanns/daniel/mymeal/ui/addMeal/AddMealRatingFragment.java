package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;

import de.die_bartmanns.daniel.mymeal.R;

public class AddMealRatingFragment extends Fragment {

    private RatingBar ratingBar;
    private AddMealMainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_add_meal_rating, container, false);

        activity = (AddMealMainActivity) getActivity();

        ratingBar = view.findViewById(R.id.ratingBar);

        if(activity.getRating() != 0){
            ratingBar.setRating(activity.getRating());
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.setRating((int) ratingBar.getRating());
    }
}
