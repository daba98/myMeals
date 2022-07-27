package de.die_bartmanns.daniel.mymeal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.AddMealInstructionFragment;

public class InstructionsListAdapter extends BaseAdapter {

    private final List<String> instructions;
    private final LayoutInflater layoutInflater;
    private final AddMealInstructionFragment fragment;

    public InstructionsListAdapter(List<String> instructions, AddMealInstructionFragment fragment){
        this.instructions = instructions;
        this.fragment = fragment;
        layoutInflater = LayoutInflater.from(fragment.getContext());
    }


    @Override
    public int getCount() {
        return instructions.size();
    }

    @Override
    public Object getItem(int i) {
        return instructions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        InstructionViewHolder holder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.instruction_item, null);
            holder = new InstructionViewHolder();
            holder.instructionTextView = view.findViewById(R.id.instructionsTextView);
            holder.changeButton = view.findViewById(R.id.changeButton);
            holder.changeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.clickedUpdateInstruction(view);
                }
            });

            view.setTag(holder);
        }
        else {
            holder = (InstructionViewHolder) view.getTag();
        }

        String instruction = instructions.get(i);
        holder.instructionTextView.setText((i + 1) + ". " + instruction);

        return view;
    }


    private static class InstructionViewHolder {

        TextView instructionTextView;
        ImageButton changeButton;
    }
}
