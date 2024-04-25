package com.winfred.themovieapp.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.winfred.themovieapp.R;
import com.winfred.themovieapp.serviceapi.MovieApiService;
import com.winfred.themovieapp.serviceapi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private ArrayList<Movie> moviesArrayList = new ArrayList<>();

    private MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();

    private Application application;


    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData() {
        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<Result> call = movieApiService.getPopularMovies(application.getApplicationContext().getString(R.string.api_key));

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();

                if (result != null && result.getResults() != null){
                    moviesArrayList= (ArrayList<Movie>) result.getResults();

                    mutableLiveData.setValue(moviesArrayList);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });


        return mutableLiveData;
    }
}
