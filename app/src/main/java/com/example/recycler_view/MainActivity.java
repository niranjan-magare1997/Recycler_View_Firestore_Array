package com.example.recycler_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Dream_Team | MainActivity";
    private FirebaseFirestore db;
    private RecyclerView myRecyclerView;
    private String[] DATA = {"1", "2"};
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myRecyclerView = findViewById(R.id.myRecyclerView);
//        myRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        myRecyclerView.setAdapter(new recyclerAdapter(DATA));

        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("WHOLE_DATA").document("Categories");
        //getCategoriesFirestore();

        documentReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists() && !documentSnapshot.getData().isEmpty()) {
                            Log.d(TAG, "onSuccess: Success => " + documentSnapshot.getData());
                            Log.d(TAG, "onSuccess: Values => " + documentSnapshot.getData().values());
                            List<String> list = (List<String>) documentSnapshot.get("Category");
                            Log.d(TAG, "onSuccess: List => " + list);

                            String[] itemsArray = new String[list.size()];
                            itemsArray = list.toArray(itemsArray);

                            Log.d(TAG, "onSuccess: Array "+itemsArray);

                            for (int i = 0; i<itemsArray.length; i++){
                                Log.d(TAG, "onSuccess: Index "+i+" :: Value => "+itemsArray[i]);
                            }

                            myRecyclerView = findViewById(R.id.myRecyclerView);
                            myRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            myRecyclerView.setAdapter(new recyclerAdapter(itemsArray));
                        } else {
                            Log.d(TAG, "onSuccess: Failed => ");
                        }
                    }
                });
    }
}