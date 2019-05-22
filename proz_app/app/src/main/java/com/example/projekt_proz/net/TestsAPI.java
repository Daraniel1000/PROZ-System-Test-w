package com.example.projekt_proz.net;

import com.example.projekt_proz.models.prozTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TestsAPI {
    @GET("tests")
    Call<List<prozTest>> getAvailableTests(@Header("Authorization") String userCreds);

    @GET("tests/{testId}")
    Call<prozTest> getTest(@Path("testId") int testId, @Header("Authorization") String userCreds);
}
