package de.die_bartmanns.daniel.mymeal.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.MealType;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseService;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseServiceFactory;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.FirestoreDataReceivingCallback;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.GetMealsResult;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.MealToOnlineMealConverter;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineDatabaseService;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineDatabaseServiceFactory;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineMeal;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.ChooseCategoryDialog;

/**
 * Created by Daniel on 07.08.2018.
 */
public class ImportDialog extends DialogFragment {

    private DatabaseService service;
    private OnlineDatabaseService onlineService;

    private EditText importEditText;

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog dialog = (AlertDialog) getDialog();
        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputStr = importEditText.getText().toString().trim();

                if(inputStr.isEmpty())
                    return;

                List<String> list = new ArrayList<>(Arrays.asList(inputStr.split(" , ")));
                onlineService.downloadMeals(list, new FirestoreDataReceivingCallback<GetMealsResult>() {
                    @Override
                    public void onCallback(GetMealsResult data) {
                        List<OnlineMeal> meals = data.getMeals();
                        for(OnlineMeal onlineMeal : meals){
                            Meal meal = MealToOnlineMealConverter.toMeal(onlineMeal);
                            if(onlineMeal.getType() != MealType.OTHER)
                                service.insertMeal(meal);
                            else{
                                showChooseCategoryDialog(meal);
                            }
                        }
                        dismiss();
                    }
                });
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_import_meal, null);

        importEditText =  view.findViewById(R.id.importEditText);
        service = DatabaseServiceFactory.getDatabaseService(getContext());
        onlineService = OnlineDatabaseServiceFactory.getOnlineDatabaseService(getActivity());

        builder.setView(view)
                .setTitle(R.string.import_title)
                .setPositiveButton(R.string.import_button, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    private void showChooseCategoryDialog(Meal meal){
        final ChooseCategoryDialog chooseCategoryDialog = new ChooseCategoryDialog(meal.getName());
        chooseCategoryDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                String selectedCategoryName = chooseCategoryDialog.getSelectedCategoryName();
                if(selectedCategoryName != null){
                    meal.setOwnCategory(selectedCategoryName);
                    service.insertMeal(meal);
                }
            }
        });
        chooseCategoryDialog.show(getActivity().getFragmentManager(), "TEST");
    }
}
