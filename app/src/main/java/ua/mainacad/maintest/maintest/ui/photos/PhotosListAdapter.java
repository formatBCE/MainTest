package ua.mainacad.maintest.maintest.ui.photos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import ua.mainacad.maintest.maintest.R;
import ua.mainacad.maintest.maintest.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotosListAdapter extends RecyclerView.Adapter<PhotosListAdapter.ViewHolder> {
    private ArrayList<Photo> mPhotos = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_photo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Picasso.get()
                .load(mPhotos.get(i).getUrl())
                .into(viewHolder.imageViewPhoto);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public void setPhotos(List<Photo> photos) {
        mPhotos.clear();
        mPhotos.addAll(photos);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPhoto = itemView.findViewById(R.id.image_view_photo);
        }
    }

}
