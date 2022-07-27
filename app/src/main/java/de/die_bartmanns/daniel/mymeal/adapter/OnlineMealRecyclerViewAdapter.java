package de.die_bartmanns.daniel.mymeal.adapter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.BitmapUtil;
import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.onlineDatabase.OnlineMeal;

public class OnlineMealRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private Context mContext;
    private List<OnlineMeal> onlineMeals;

    public OnlineMealRecyclerViewAdapter(Context context, List<OnlineMeal> onlineMeals){
        mContext = context;
        this.onlineMeals = onlineMeals;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.card, parent, false);
            return new ItemViewholder(view);
        }
        else{
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.loading_list_item, parent, false);
            return new LoadingViewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewholder) {
            fillItemView((ItemViewholder) holder, position);
        }
        else if(holder instanceof LoadingViewholder){
            showLoadingView((LoadingViewholder) holder, position);
        }
    }

    private void fillItemView(ItemViewholder holder, int position){
        OnlineMeal onlineMeal = onlineMeals.get(position);
        holder.name.setText(onlineMeal.getName());
        holder.time.setText(onlineMeal.getTimeToCook() + " min");
        holder.difficulty.setText(mContext.getString(onlineMeal.getDifficulty().getStringResId()));
        if (onlineMeal.getPhoto() == null)
            holder.photo.setImageResource(onlineMeal.getType().getPhotoResId());
        else {
            holder.photo.setImageBitmap(BitmapUtil.byteArrayToBitmap(stringToArray(onlineMeal.getPhoto())));
        }

        int rating = (int) onlineMeal.getRating();
        for (int i = 0; i < 5; i++) {
            if (rating > i)
                holder.stars[i].setImageResource(R.drawable.ic_full_star);
            else
                holder.stars[i].setImageResource(R.drawable.ic_empty_star);
        }

        holder.card.setCardBackgroundColor(mContext.getResources().getColor(onlineMeal.getType().getColorResId()));
    }

    private byte[] stringToArray(String photo){
        if(photo == null)
            return null;

        return Base64.decode(photo, Base64.DEFAULT);
    }

    private void showLoadingView(LoadingViewholder holder, int position){}

    @Override
    public int getItemCount() {
        return onlineMeals.size();
    }

    @Override
    public int getItemViewType(int position) {
        return onlineMeals.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class ItemViewholder extends RecyclerView.ViewHolder{

        CardView card;
        TextView name;
        TextView time;
        TextView difficulty;
        ImageView photo;
        ImageView[] stars;

        public ItemViewholder(View itemView){
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

    private class LoadingViewholder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        public LoadingViewholder(View loadingview){
            super(loadingview);
            progressBar = loadingview.findViewById(R.id.progressBar);
        }
    }


}
