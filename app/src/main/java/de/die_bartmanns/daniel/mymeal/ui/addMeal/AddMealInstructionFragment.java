package de.die_bartmanns.daniel.mymeal.ui.addMeal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.adapter.InstructionsListAdapter;
import de.die_bartmanns.daniel.mymeal.data.Ingredient;

public class AddMealInstructionFragment extends Fragment {

    private final String TAG_INSTRUCTION= "instruction";

    private ListView instructionsListView;
    private InstructionsListAdapter adapter;
    private AddMealMainActivity activity;

    private List<String> instructions;
    private int insertIdx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_add_meal_instructions, container, false);

        activity = (AddMealMainActivity) getActivity();

        instructionsListView = view.findViewById(R.id.instructionsListView);
        instructions = activity.getInstructions();
        adapter = new InstructionsListAdapter(instructions, this);
        instructionsListView.setAdapter(adapter);


        FloatingActionButton addButton = view.findViewById(R.id.add_instruction_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddInstructionDialog(null);
            }
        });

        return view;
    }

    private void showAddInstructionDialog(String instructionToUpdtae){
        final AddInstructionDialog addInstructionDialog = new AddInstructionDialog();
        Bundle args = new Bundle();
        args.putString(TAG_INSTRUCTION, instructionToUpdtae);
        addInstructionDialog.setArguments(args);
        addInstructionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                List<String> addedInstructions = addInstructionDialog.getInstructions();
                if(!addedInstructions.isEmpty()) {
                    if(instructionToUpdtae == null)
                        instructions.addAll(addedInstructions);
                    else
                        instructions.set(insertIdx, addedInstructions.get(0));
                    adapter.notifyDataSetChanged();
                    activity.setInstructions(instructions);
                }

            }
        });
        addInstructionDialog.show(getActivity().getFragmentManager(), "TEST");
    }

    public void clickedUpdateInstruction(View view){
        int idx = instructionsListView.getPositionForView(view);
        insertIdx = idx;
        showAddInstructionDialog(instructions.get(idx));
    }
}
