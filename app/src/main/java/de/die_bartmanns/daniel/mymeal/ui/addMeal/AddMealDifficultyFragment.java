package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.Difficulty;

public class AddMealDifficultyFragment extends Fragment {

    private TextView diffTextView;
    private AddMealMainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_add_meal_difficulty, container, false);
        activity = (AddMealMainActivity) getActivity();

        diffTextView = view.findViewById(R.id.diff_textview);
        ImageButton previous = view.findViewById(R.id.button_previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Difficulty diff = Difficulty.previous(activity.getDiff());
                activity.setDiff(diff);
                diffTextView.setText(activity.getString(diff.getStringResId()));
            }
        });

        ImageButton next = view.findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Difficulty diff = Difficulty.next(activity.getDiff());
                activity.setDiff(diff);
                diffTextView.setText(activity.getString(diff.getStringResId()));
            }
        });

        if(activity.getDiff() != null)
            diffTextView.setText(activity.getString(activity.getDiff().getStringResId()));
        else{
            activity.setDiff(Difficulty.Easy);
            diffTextView.setText(activity.getString(Difficulty.Easy.getStringResId()));
        }

        return view;
    }
}
