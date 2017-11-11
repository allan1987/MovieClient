package com.mnidersoft.movieclient.core.modules;

import com.mnidersoft.movieclient.core.qualifiers.SchedulerIO;
import com.mnidersoft.movieclient.restservice.MovieDBService;
import com.mnidersoft.movieclient.restservice.RestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Allan.Menezes on 11/10/17.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    static RestClient restClient(MovieDBService service, @SchedulerIO Scheduler ioScheduler) {
        return new RestClient(service, ioScheduler);
    }

    @Provides
    @Singleton
    static MovieDBService service(OkHttpClient httpClient) {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(MovieDBService.BASE_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return adapter.create(MovieDBService.class);
    }

    @Provides
    @Singleton
    static OkHttpClient httpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    static Interceptor interceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}