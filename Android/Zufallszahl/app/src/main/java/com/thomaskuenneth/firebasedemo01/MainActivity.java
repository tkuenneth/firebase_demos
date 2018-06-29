package com.thomaskuenneth.firebasedemo01;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FirebaseFunctions mFunctions;
    private TextView randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomNumber = findViewById(R.id.randomNumber);
        final Button newRandomNumber = findViewById(R.id.newRandomNumber);
        newRandomNumber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                newRandomNumber();
            }
        });
        final Button crash = findViewById(R.id.crash);
        crash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                crash();
            }
        });
        mFunctions = FirebaseFunctions.getInstance();
    }

    private void newRandomNumber() {
        final Map<String, Integer> data = new HashMap<>();
        data.put("max", 10);
        mFunctions
                .getHttpsCallable("zufallszahl")
                .call(data)
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure()", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<HttpsCallableResult>() {

                    @Override
                    public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                        Log.d(TAG, "onComplete()");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {

                    @Override
                    public void onSuccess(HttpsCallableResult httpsCallableResult) {
                        handleResult((Map<String, Integer>) httpsCallableResult.getData());
                    }
                });
    }

    private String crash() {
        String a = null;
        String b = a.toString();
        return b;
    }

    private void handleResult(Map<String, Integer> data) {
        if (data != null) {
            int num = data.get("result");
            randomNumber.setText(Integer.toString(num));
        }
    }
}
