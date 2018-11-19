package ua.mainacad.maintest.maintest.ui.users;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ua.mainacad.maintest.maintest.R;
import ua.mainacad.maintest.maintest.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private List<User> data = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext())
                        .inflate(R.layout.item_user, viewGroup, false)
        );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        User user = data.get(position);
        viewHolder.userName.setText(user.getName());
        viewHolder.companyName.setText(user.getCompany().getName());
    }

    public void setUsers(List<User> users) {
        data.clear();
        data.addAll(users);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView userName;
        private final TextView companyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_user_name);
            companyName = itemView.findViewById(R.id.tv_user_company);
        }
    }
}
