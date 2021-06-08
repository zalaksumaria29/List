package com.example.anew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.anew.Activity.Detail;
import com.example.anew.Activity.UserModal;
import com.example.anew.R;

import java.util.ArrayList;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.ViewHolder> {

    // variable for our array list and context.
    private ArrayList<UserModal> userModalArrayList;
    private Context context;

    // creating a constructor.
    public UserRVAdapter(ArrayList<UserModal> userModalArrayList, Context context) {
        this.userModalArrayList = userModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.user_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // getting data from our array list in our modal class.
        UserModal userModal = userModalArrayList.get(position);

        // on below line we are setting data to our text view.
        holder.personID.setText(userModal.getId());
        holder.firstNameTV.setText(userModal.getFirst_name());
        holder.lastNameTV.setText(userModal.getLast_name());
        holder.emailTV.setText(userModal.getEmail());

        Glide.with(context)
                .load(userModal.getAvatar())
                .into(holder.userIV);

        String id = userModal.getId();
        String firstname = userModal.getFirst_name();
        String lastname = userModal.getLast_name();
        String email = userModal.getEmail();
        String image = userModal.getAvatar();



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "ID : "+ id, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, Detail.class);
                i.putExtra("id", id);
                i.putExtra("firstname", firstname);
                i.putExtra("lastname", lastname);
                i.putExtra("email", email);
                i.putExtra("image", image);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return userModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView personID, firstNameTV, lastNameTV, emailTV;
        private ImageView userIV;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our variables.
            personID = itemView.findViewById(R.id.idPersonID);
            firstNameTV = itemView.findViewById(R.id.idTVFirstName);
            lastNameTV = itemView.findViewById(R.id.idTVLastName);
            emailTV = itemView.findViewById(R.id.idTVEmail);
            userIV = itemView.findViewById(R.id.idIVUser);
            cardView = itemView.findViewById(R.id.cardList);
        }
    }
}