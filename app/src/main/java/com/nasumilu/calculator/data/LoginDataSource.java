package com.nasumilu.calculator.data;

import android.util.Log;

import com.nasumilu.calculator.data.model.LoggedInUser;
import com.nasumilu.calculator.service.LoginService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.jvm.internal.CallableReference;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            return service.submit(new TokenLogin(username, password)).get();
        } catch (ExecutionException e) {
            return new Result.Error(e);
        } catch (InterruptedException e) {
            return new Result.Error(e);
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private class TokenLogin implements Callable<Result> {

        private final String USERNAME;
        private final String PASSWORD;

        public TokenLogin(String username, String password) {
            this.USERNAME = username;
            this.PASSWORD = password;
        }

        @Override
        public Result call() throws Exception {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://nasumilu.io/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //.addConverterFactory(JaxbConverterFactory.create())
                    .build();

                var service = retrofit.create(LoginService.class);
                var call = service.token("password",
                        "[CLIENT SECRET HERE]",
                        "[CLIENT ID HERE]",
                        this.USERNAME,
                        this.PASSWORD);

                var response = call.execute();

            if(response.isSuccessful()) {
                Log.i("Token", response.body());
                return new Result.Success<>(new LoggedInUser("Test", "Test", "Test"));
            }
            return new Result.Error(new Exception("Not Authorized"));
        }
    }

}