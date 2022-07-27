package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.Ingredient;
import de.die_bartmanns.daniel.mymeal.data.Unit;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseService;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseServiceFactory;

public class AddIngredientDialog extends DialogFragment {

    private EditText ingredientEditText;
    private EditText amountEditText;
    private Spinner unitSpinner;

    private DatabaseService service;

    private List<Ingredient> ingredients = new ArrayList<>();

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
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
                String ingredientName = ingredientEditText.getText().toString();
                String amountString = amountEditText.getText().toString();
                Unit unit = Unit.getUnit(getActivity(),unitSpinner.getSelectedItem().toString());
                if(!ingredientName.equals("") && !amountString.equals("")){
                    float amount = Float.parseFloat(amountString);
                    Ingredient ingr = new Ingredient(ingredientName, amount, unit);
                    ingredients.add(ingr);
                    clearInputs();
                    service.addCount(ingredientName, unit);
                }
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        service = DatabaseServiceFactory.getDatabaseService(getActivity().getApplicationContext());

        View view = inflater.inflate(R.layout.dialog_add_ingredient, null);

        ingredientEditText =  view.findViewById(R.id.ingredient_edittext);
        ingredientEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        ingredientEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        ingredientEditText.addTextChangedListener(new MyTextWatcher(ingredientEditText));
        amountEditText = view.findViewById(R.id.ingr_amount_editText);
        amountEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        unitSpinner = view.findViewById(R.id.unit_spinner);

        ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line);
        for(int i = 0; i < Unit.values().length; i++)
            adapterUnit.add(getString(Unit.values()[i].getStringResId()));
        unitSpinner.setAdapter(adapterUnit);

        builder.setView(view)
                .setTitle(R.string.add_ingredient)
                .setPositiveButton(R.string.add, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    private void clearInputs(){
        ingredientEditText.setText("");
        amountEditText.setText("");
    }

    private void setMostLikelyUnit(){
        String ingredient = ingredientEditText.getText().toString();
        if(!ingredient.equals("")){
            Unit mostLikelyUnit = service.getMostLikelyUnit(ingredient);
            unitSpinner.setSelection(mostLikelyUnit.ordinal());
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.ingredient_edittext:
                    setMostLikelyUnit();
                    break;
            }
        }
    }
}
