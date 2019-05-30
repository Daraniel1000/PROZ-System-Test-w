package com.example.projekt_proz.net;

import com.example.projekt_proz.models.prozUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UsersAPI {
    @GET("me")
    Call<prozUser> getMe(@Header("Authorization") String userCreds);

    @GET("users")
    Call<ArrayList<prozUser>> getAllUsers(@Header("Authorization") String userCreds);
}
