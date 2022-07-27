package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import de.die_bartmanns.daniel.mymeal.R;

public class AddMealTimeFragment extends Fragment {

    private EditText timeEditText;
    private AddMealMainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_add_meal_time, container, false);
        activity = (AddMealMainActivity) getActivity();

        timeEditText = view.findViewById(R.id.timeEditText);
        timeEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);


        if(activity.getTime() != 0)
            timeEditText.setText(String.valueOf(activity.getTime()));

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        String timeString = timeEditText.getText().toString();
        if(!timeString.equals("")) {
            int time = Integer.parseInt(timeString);
            activity.setTime(time);
        }
    }
}
