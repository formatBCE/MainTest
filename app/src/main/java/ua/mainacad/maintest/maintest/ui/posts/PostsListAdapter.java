package ua.mainacad.maintest.maintest.ui.posts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ua.mainacad.maintest.maintest.R;
import ua.mainacad.maintest.maintest.model.Post;
import java.util.ArrayList;
import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.ViewHolder> {
    private List<Post> mData = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PostsListAdapter.ViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext())
                        .inflate(R.layout.item_post, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Post post = mData.get(position);
        viewHolder.title.setText(post.getTitle());
        viewHolder.body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setPosts(List<Post> posts) {
        mData.clear();
        mData.addAll(posts);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView body;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_post_title);
            this.body = itemView.findViewById(R.id.tv_post_body);
        }
    }
}
