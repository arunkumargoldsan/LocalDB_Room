package com.example.scriflare_iq2.room_db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scriflare_iq2.R;

public class UserRVAdapter extends ListAdapter<UserModal, UserRVAdapter.ViewHolder> {
    private OnItemClickListener listener;

    public UserRVAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<UserModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserModal>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserModal oldItem, @NonNull UserModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserModal oldItem, @NonNull UserModal newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getGender().equals(newItem.getGender()) &&
                    oldItem.getEmail().equals(newItem.getEmail()) &&
                    oldItem.getMobile().equals(newItem.getMobile());
        }
    };

    @NonNull
    @Override
    public UserRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserRVAdapter.ViewHolder holder, int position) {
        UserModal model = getUserAt(position);
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.mobile.setText(model.getMobile());
        holder.gender.setText(model.getGender());
    }

    public UserModal getUserAt(int position) {
        return getItem(position);
    }

    public interface OnItemClickListener {
        void onItemClick(UserModal modal);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, mobile, gender;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tv_name);
            email = (TextView) itemView.findViewById(R.id.tv_email);
            mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            gender = (TextView) itemView.findViewById(R.id.tv_gender);
        }
    }

}
