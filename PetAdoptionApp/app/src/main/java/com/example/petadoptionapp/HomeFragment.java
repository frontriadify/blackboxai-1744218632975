package com.example.petadoptionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {

    private RecyclerView petsRecyclerView;
    private PetAdapter petAdapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        petsRecyclerView = view.findViewById(R.id.pets_recycler_view);
        petsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        Query query = db.collection("pets")
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Pet> options = new FirestoreRecyclerOptions.Builder<Pet>()
                .setQuery(query, Pet.class)
                .build();

        petAdapter = new PetAdapter(options);
        petsRecyclerView.setAdapter(petAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        petAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        petAdapter.stopListening();
    }
}
