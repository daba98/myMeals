package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.Ingredient;
import de.die_bartmanns.daniel.mymeal.data.Unit;

public class AddInstructionDialog extends DialogFragment {

    private final String TAG_INSTRUCTION= "instruction";

    private EditText instructionEditText;
    private String instructionToUpdate;

    private List<String> instructions = new ArrayList<>();

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public List<String> getInstructions() {
        return instructions;
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
                String instruction = instructionEditText.getText().toString();
                if(!instruction.equals(""))
                    instructions.add(instruction);
                if(instructionToUpdate == null)
                    clearInputs();
                else
                    dismiss();
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_instruction, null);

        instructionEditText =  view.findViewById(R.id.instructionEditText);

        instructionToUpdate = getArguments().getString(TAG_INSTRUCTION);
        if(instructionToUpdate != null)
            instructionEditText.setText(instructionToUpdate);

        builder.setView(view)
                .setTitle(R.string.add_instruction)
                .setPositiveButton(instructionToUpdate == null ? R.string.add : R.string.update, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    private void clearInputs(){
        instructionEditText.setText("");
    }
}
