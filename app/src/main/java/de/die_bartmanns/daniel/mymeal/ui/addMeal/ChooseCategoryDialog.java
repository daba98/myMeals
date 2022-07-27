package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.Ingredient;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseService;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseServiceFactory;

public class ChooseCategoryDialog extends DialogFragment {

    private Spinner categorySpinner;
    private DatabaseService service;
    private List<String> categoryNames;
    private ArrayAdapter adapter;
    private String selectedCategoryName;
    private String mealName;

    public ChooseCategoryDialog(String mealName) {
        this.mealName = mealName;
    }

    public ChooseCategoryDialog(){}

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public String getSelectedCategoryName() {
        return selectedCategoryName;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog dialog = (AlertDialog) getDialog();
        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectionIdx = categorySpinner.getSelectedItemPosition();
                if(selectionIdx >= 0 && selectionIdx < categoryNames.size()) {
                    selectedCategoryName = categoryNames.get(selectionIdx);
                    dismiss();
                }
            }
        });

        Button buttonNeutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        buttonNeutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateCategoryDialog();
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_choose_category, null);

        service = DatabaseServiceFactory.getDatabaseService(getActivity().getApplicationContext());
        categoryNames = service.getAllCategoryNames();
        categorySpinner =  view.findViewById(R.id.category_spinner);

        adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        builder.setView(view)
                .setTitle(R.string.choose_category)
                .setPositiveButton(R.string.choose, null)
                .setNeutralButton(R.string.add_category, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        if(mealName != null)
            builder.setMessage("Wähle die Kategorie für \"" + mealName + "\"");

        return builder.create();
    }

    private void showCreateCategoryDialog(){
        final AddCategoryDialog addCategoryDialog = new AddCategoryDialog();
        addCategoryDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                categoryNames.clear();
                categoryNames.addAll(service.getAllCategoryNames());
                adapter.notifyDataSetChanged();
            }
        });
        addCategoryDialog.show(getActivity().getFragmentManager(), "TEST");
    }
}
