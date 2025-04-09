package com.example.petadoptionapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PostFragment extends Fragment {

    private EditText petName, petType, petDescription;
    private ImageView petImage;
    private Button postButton;
    private Uri imageUri;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private final ActivityResultLauncher<String> pickImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    imageUri = uri;
                    petImage.setImageURI(uri);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        petName = view.findViewById(R.id.post_pet_name);
        petType = view.findViewById(R.id.post_pet_type);
        petDescription = view.findViewById(R.id.post_pet_description);
        petImage = view.findViewById(R.id.post_pet_image);
        postButton = view.findViewById(R.id.post_button);

        petImage.setOnClickListener(v -> pickImage.launch("image/*"));
        postButton.setOnClickListener(v -> uploadPet());

        return view;
    }

    private void uploadPet() {
        String name = petName.getText().toString().trim();
        String type = petType.getText().toString().trim();
        String description = petDescription.getText().toString().trim();

        if (name.isEmpty() || type.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageRef = storage.getReference().child("pet_images/" + UUID.randomUUID().toString());
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        String userId = auth.getCurrentUser().getUid();
                        Pet pet = new Pet(name, type, description, imageUrl, null, userId);
                        db.collection("pets").add(pet)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(getContext(), "Pet posted successfully", Toast.LENGTH_SHORT).show();
                                    clearForm();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Failed to post pet", Toast.LENGTH_SHORT).show();
                                });
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearForm() {
        petName.setText("");
        petType.setText("");
        petDescription.setText("");
        petImage.setImageResource(R.drawable.placeholder_pet);
        imageUri = null;
    }
}
