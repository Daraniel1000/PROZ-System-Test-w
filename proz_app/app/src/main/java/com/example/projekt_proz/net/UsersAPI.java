package com.example.projekt_proz.net;

import com.example.projekt_proz.models.prozUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UsersAPI {
    @GET("me")
    Call<prozUser> getMe(@Header("Authorization") String userCreds);

    @GET("users")
    Call<List<prozUser>> getAllUsers(@Header("Authorization") String userCreds);
}
