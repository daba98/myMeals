package de.die_bartmanns.daniel.mymeal.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.MealType;
import de.die_bartmanns.daniel.mymeal.ui.addMeal.AddMealInstructionFragment;

public class InstructionsDetailsListAdapter extends BaseAdapter {

    private final List<String> instructions;
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final MealType type;

    public InstructionsDetailsListAdapter(List<String> instructions, Context context, MealType type){
        this.instructions = instructions;
        this.context = context;
        this.type = type;
        layoutInflater = LayoutInflater.from(context);
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
            view = layoutInflater.inflate(R.layout.details_instruction_item, null);
            holder = new InstructionViewHolder();
            holder.instructionTextView = view.findViewById(R.id.instruction_textview);
            holder.numberView = view.findViewById(R.id.number_view);

            view.setTag(holder);
        }
        else {
            holder = (InstructionViewHolder) view.getTag();
        }

        String instruction = instructions.get(i);
        holder.instructionTextView.setText(instruction);
        holder.numberView.setText(String.valueOf(i + 1));
        holder.numberView.setBackgroundTintList(context.getResources().getColorStateList(type.getColorResId()));

        return view;
    }


    private static class InstructionViewHolder {

        TextView instructionTextView;
        TextView numberView;
    }
}
