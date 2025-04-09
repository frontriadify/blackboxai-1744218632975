package com.example.petadoptionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProfileFragment extends Fragment {

    private TextView userName;
    private RecyclerView userPetsRecyclerView;
    private PetAdapter petAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        userName = view.findViewById(R.id.profile_user_name);
        userPetsRecyclerView = view.findViewById(R.id.profile_pets_recycler_view);
        userPetsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setupRecyclerView();
        loadUserData();

        return view;
    }

    private void setupRecyclerView() {
        String userId = auth.getCurrentUser().getUid();
        Query query = db.collection("pets")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Pet> options = new FirestoreRecyclerOptions.Builder<Pet>()
                .setQuery(query, Pet.class)
                .build();

        petAdapter = new PetAdapter(options);
        userPetsRecyclerView.setAdapter(petAdapter);
    }

    private void loadUserData() {
        String currentUser = auth.getCurrentUser().getEmail();
        userName.setText(currentUser);
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
