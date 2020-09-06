package com.example.covid19retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EndpointInterface {

    @GET("dayone/country/IN")
    Call <String> getData();

}
