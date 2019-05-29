package com.example.projekt_proz.net;

import com.example.projekt_proz.models.ResultsResponse;
import com.example.projekt_proz.models.prozTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TestsAPI {
    @GET("tests")
    Call<List<prozTest>> getAvailableTests(@Header("Authorization") String userCreds);

    @GET("tests/{testId}")
    Call<prozTest> getTest(@Path("testId") int testId, @Header("Authorization") String userCreds);

    @POST("tests/{testId}/submit")
    Call<ResultsResponse> submitTest(@Path("testId") int testId, @Body List<Integer> solutions, @Header("Authorization") String userCreds);

    @GET("tests/{testId}/results")
    Call<ResultsResponse> getResults(@Path("testId") int testId, @Header("Authorization") String userCreds);

    @POST("tests")
    Call<prozTest> addTest(@Body prozTest test, @Header("Authorization") String userCreds);

    @POST("tests/{testId}/users")
    Call<prozTest> addUsersToTest(@Path("testId") int testId, @Body List<Integer> users, @Header("Authorization") String userCreds);
}
