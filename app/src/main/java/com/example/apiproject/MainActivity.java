package com.example.apiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    static String base_url_age = "https://api.agify.io";
    static String base_url_gen = "https://api.genderize.io";
    static String base_url_nat = "https://api.nationalize.io";

    EditText input;
    TextView Age;
    TextView Gender;
    TextView Nationality;

    static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.inputName);
        Age = findViewById(R.id.Age);
        Gender = findViewById(R.id.Gender);
        Nationality = findViewById(R.id.Nationality);
    }

    public void makeRequests(View view){
        String in = input.getText().toString();
        RequestParams input = new RequestParams();
        input.add("name", in);
        client.get(base_url_age, input, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    String ageOut = response.getString("age");
                    Age.setText("Age: " + ageOut);
                } catch (JSONException e) {
                    Age.setText("Age: No value");
                }
            }
        });
        client.get(base_url_gen, input, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    String ageOut = response.getString("gender");
                    Gender.setText("Gender: " + ageOut);
                } catch (JSONException e) {
                    Gender.setText("Gender: No value");
                }
            }
        });
        client.get(base_url_nat, input, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    JSONArray area = response.getJSONArray("country");
                    String ageOut = area.getJSONObject(0).getString("country_id");
                    String probOut = String.valueOf(area.getJSONObject(0).getDouble("probability"));
                    Nationality.setText("Nationality: " + ageOut + ", " + probOut + " probability");
                } catch (JSONException e) {
                    Nationality.setText("Nationality: No value");
                }
            }
        });
    }
}