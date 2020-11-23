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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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
        //getdata();
        performQueries();
    }

    private void performQueries() {
        CollectionReference cities = db.collection("cities");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "San Francisco");
        data1.put("state", "CA");
        data1.put("country", "USA");
        data1.put("capital", false);
        data1.put("population", 860000);
        data1.put("regions", Arrays.asList("west_coast", "norcal"));
        cities.document("SF").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "Los Angeles");
        data2.put("state", "CA");
        data2.put("country", "USA");
        data2.put("capital", false);
        data2.put("population", 3900000);
        data2.put("regions", Arrays.asList("west_coast", "socal"));
        cities.document("LA").set(data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("name", "Washington D.C.");
        data3.put("state", null);
        data3.put("country", "USA");
        data3.put("capital", true);
        data3.put("population", 680000);
        data3.put("regions", Arrays.asList("east_coast"));
        cities.document("DC").set(data3);

        Map<String, Object> data4 = new HashMap<>();
        data4.put("name", "Tokyo");
        data4.put("state", null);
        data4.put("country", "Japan");
        data4.put("capital", true);
        data4.put("population", 9000000);
        data4.put("regions", Arrays.asList("kanto", "honshu"));
        cities.document("TOK").set(data4);

        Map<String, Object> data5 = new HashMap<>();
        data5.put("name", "Beijing");
        data5.put("state", null);
        data5.put("country", "China");
        data5.put("capital", true);
        data5.put("population", 21500000);
        data5.put("regions", Arrays.asList("jingjinji", "hebei"));
        cities.document("BJ").set(data5);

        // Create a reference to the cities collection
        CollectionReference citiesRef = db.collection("cities");

        // Create a query against the collection.
        Query query = citiesRef.whereEqualTo("state", "CA");

        Query capitalCities = db.collection("cities").whereEqualTo("capital", true);

        db.collection("cities")
            .whereEqualTo("capital", true)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Toast.makeText(MainActivity.this, document.getId() + " => " + document.getData(),Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error getting documents: "+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
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