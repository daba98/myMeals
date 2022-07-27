package de.die_bartmanns.daniel.mymeal.adapter;

import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.BitmapUtil;
import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseService;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseServiceFactory;

/**
 * Created by Daniel on 15.02.2019.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context mContext;
    private List<Long> mealIdList;
    private DatabaseService service;

    public RecyclerViewAdapter(Context context, List<Long> mealIdList){
        mContext = context;
        this.mealIdList = mealIdList;
        service = DatabaseServiceFactory.getDatabaseService(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Long mealId = mealIdList.get(position);
        Meal meal = service.getMeal(mealId);
        holder.name.setText( meal.getName());
        holder.time.setText(meal.getTimeToCook() + " min");
        holder.difficulty.setText(mContext.getString(meal.getDifficulty().getStringResId()));
        if(meal.getPhotoByteArray() == null)
            holder.photo.setImageResource(meal.getDefaultImageResId());
        else {
            holder.photo.setImageBitmap(BitmapUtil.byteArrayToBitmap(meal.getPhotoByteArray()));
        }

        int rating = meal.getRating();
        for(int i = 0; i < 5; i++) {
            if (rating > i)
                holder.stars[i].setImageResource(R.drawable.ic_full_star);
            else
                holder.stars[i].setImageResource(R.drawable.ic_empty_star);
        }

        holder.card.setCardBackgroundColor(mContext.getResources().getColor(meal.getType().getDarkColorResId()));
    }

    @Override
    public int getItemCount() {
        return mealIdList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        TextView name;
        TextView time;
        TextView difficulty;
        ImageView photo;
        ImageView[] stars;

        public MyViewHolder(View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.cardview);
            name = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.timeToCookTextView);
            photo = (ImageView) itemView.findViewById(R.id.image);
            stars = new ImageView[5];
            stars[0] = (ImageView) itemView.findViewById(R.id.star1);
            stars[1] = (ImageView) itemView.findViewById(R.id.star2);
            stars[2] = (ImageView) itemView.findViewById(R.id.star3);
            stars[3] = (ImageView) itemView.findViewById(R.id.star4);
            stars[4] = (ImageView) itemView.findViewById(R.id.star5);
            difficulty = (TextView) itemView.findViewById(R.id.difficultyView);
        }
    }
}
