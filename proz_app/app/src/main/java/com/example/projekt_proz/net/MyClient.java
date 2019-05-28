package com.example.projekt_proz.net;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MyClient {
    private static final String baseUrl = "http://10.42.0.1:8080";
    private Retrofit retrofit;

    public MyClient() {
        retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    }

    public TestsAPI tests() {
        return retrofit.create(TestsAPI.class);
    }
    public QuestionsAPI questions() {
        return retrofit.create(QuestionsAPI.class);
    }
    public UsersAPI users() {
        return retrofit.create(UsersAPI.class);
    }
}
