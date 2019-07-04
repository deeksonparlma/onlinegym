package com.epicodus.gym.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.epicodus.gym.R;
import com.epicodus.gym.model.PostModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolder extends RecyclerView.ViewHolder{
    View  mView;
    Context mContext;
    public viewHolder(@NonNull View itemView) {
        super(itemView);
        mView =itemView;
        mContext = itemView.getContext();
    }
    public void bindpost(PostModel post){
        TextView username = mView.findViewById(R.id.usernamePost);
        username.setText(post.getmUsername());
    }
}
