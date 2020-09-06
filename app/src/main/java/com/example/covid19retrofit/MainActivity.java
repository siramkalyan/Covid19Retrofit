package com.example.covid19retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.Toast;

import com.example.covid19retrofit.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        EndpointInterface anInterface=Covid19Instance.getInstance().create(EndpointInterface.class);
        Call<String>c=anInterface.getData();
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONArray rootArray=new JSONArray(response.body());
                    for(int i=0;i< rootArray.length();i++) {
                        JSONObject rootObj = rootArray.getJSONObject(i);
                        String countryResult = rootObj.getString("Country");
                        String confirmedResult = rootObj.getString("Confirmed");
                        String deathsResult = rootObj.getString("Deaths");
                        String recoveredResult = rootObj.getString("Recovered");
                        String activeResult = rootObj.getString("Active");
                        String resultDate = rootObj.getString("Date");
                        binding.activeTv.setText("Active" + activeResult);
                        binding.confirmedTv.setText("Confirmed " + confirmedResult);
                        binding.countryTv.setText("Country " + countryResult);
                        binding.deathsTv.setText("Deaths " + deathsResult);
                        binding.recoveredTv.setText("Recoverd " + recoveredResult);
                        binding.dateTv.setText("Date " + resultDate);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this,"jfsfks"+response.body(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this,"text has failed",Toast.LENGTH_SHORT).show();

            }
        });

    }
}