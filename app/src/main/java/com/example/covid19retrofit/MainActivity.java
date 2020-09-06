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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                        binding.activeTv.setText("Active " + activeResult);
                        binding.confirmedTv.setText("Confirmed " + confirmedResult);
                        binding.countryTv.setText("Country " + countryResult);
                        binding.deathsTv.setText("Deaths " + deathsResult);
                        binding.recoveredTv.setText("Recoverd " + recoveredResult);
                        binding.dateTv.setText("Date " + properDateformat(resultDate));
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

    private String proper(String result)
    {
        String[] a=result.split("T");
        return a[0];
    }

    private String properDateformat(String resultDate) {
        String inputFormat="yyyy-mm-dd";
        String outputFormat="dd-mm-yyyy";
        SimpleDateFormat inputForm=new SimpleDateFormat(inputFormat);
        SimpleDateFormat outputForm=new SimpleDateFormat(outputFormat);
         String s=null;
         Date d=null;
         try {

                 d=inputForm.parse(resultDate);
                 s=outputForm.format(d);

         }
         catch  (ParseException e) {
             e.printStackTrace();
         }
      return s;
    }
}