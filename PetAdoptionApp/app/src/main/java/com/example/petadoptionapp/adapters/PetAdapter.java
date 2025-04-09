package com.example.petadoptionapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petadoptionapp.R;
import com.example.petadoptionapp.models.Pet;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PetAdapter extends FirestoreRecyclerAdapter<Pet, PetAdapter.PetViewHolder> {

    public PetAdapter(@NonNull FirestoreRecyclerOptions<Pet> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PetViewHolder holder, int position, @NonNull Pet model) {
        holder.petName.setText(model.getName());
        holder.petType.setText(model.getType());
        holder.petDescription.setText(model.getDescription());
        Glide.with(holder.itemView.getContext())
                .load(model.getImageUrl())
                .into(holder.petImage);
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petName, petType, petDescription;
        ImageView petImage;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.pet_name);
            petType = itemView.findViewById(R.id.pet_type);
            petDescription = itemView.findViewById(R.id.pet_description);
            petImage = itemView.findViewById(R.id.pet_image);
        }
    }
}
