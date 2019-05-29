package com.example.projekt_proz.net;

import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuestionsAPI {
    @GET("questions")
    Call<ArrayList<prozQuestion>> listQuestions(@Header("Authorization") String userCreds);

    @GET("questions/{questionId}")
    Call<prozQuestion> getQuestion(@Path("questionId") int questionId, @Header("Authorization") String userCreds);

    @POST("questions")
    Call<prozQuestion> addQuestion(@Body prozQuestion question, @Header("Authorization") String userCreds);
}
