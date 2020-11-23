package com.atul.android.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //addUserData();
        //setDocument();
        //dataTypes();
        getdata();
    }

    //Fetch Data from Cloud
    private void getdata() {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(MainActivity.this, document.getId() + " => " + document.getData(),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(BATTERY_SERVICE, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    //Set document into a document
    private void setDocument() {
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("first", "Alan");
        newUser.put("middle", "Mathison");
        newUser.put("last", "Turing");
        newUser.put("born", 1912);

        String s = "UsersDetails";
        db.collection(s).document("Programmer")
                .set(newUser, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Data Added ",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failed!",Toast.LENGTH_SHORT).show();

                    }
                });
    }

    // Add user details to a collection
    private void addUserData() {
        Map<String,Object> userDetails = new HashMap<>();
        userDetails.put("firstName", "Atul");
        userDetails.put("lastName", "Sharma");
        userDetails.put("born", "2000");
        userDetails.put("nativePlace", "Bihar");

        db.collection("UsersDetails")
                .add(userDetails)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Data Added with id: "+ documentReference.getId(),Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error Adding Document",Toast.LENGTH_SHORT).show();

                    }
                });
    }

    // Use of all dataTypes
    private void dataTypes() {
        HashMap<String, Object> dataTypesHashMap = new HashMap<>();
        dataTypesHashMap.put("string", "Yo Wassup");
        dataTypesHashMap.put("number", 2002);
        dataTypesHashMap.put("boolean", true);
        dataTypesHashMap.put("time stamp", new Timestamp(new Date()));
        dataTypesHashMap.put("list", Arrays.asList(10, 20, 30, 40, 50));

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 1);
        map.put("d", 4);
        map.put("e", 5);

        dataTypesHashMap.put("map", map);

        db.collection("users").document("dataTypes")
                .set(dataTypesHashMap, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}