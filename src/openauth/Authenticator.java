package openauth;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import openauth.model.AuthAgent;
import openauth.model.AuthError;
import openauth.model.request.AuthRequest;
import openauth.model.request.InvalidateRequest;
import openauth.model.request.RefreshRequest;
import openauth.model.request.SignoutRequest;
import openauth.model.request.ValidateRequest;
import openauth.model.response.AuthResponse;
import openauth.model.response.RefreshResponse;

public class Authenticator {

    public static final String MOJANG_AUTH_URL = "https://authserver.mojang.com/";

    private String authURL;

    private AuthPoints authPoints;

    public Authenticator(String authURL, AuthPoints authPoints) {
        this.authURL = authURL;
        this.authPoints = authPoints;
    }

    public AuthResponse authenticate(AuthAgent agent, String username, String password, String clientToken) throws AuthenticationException {
        AuthRequest request = new AuthRequest(agent, username, password, clientToken);
        return (AuthResponse) sendRequest(request, AuthResponse.class, authPoints.getAuthenticatePoint());
    }

    public RefreshResponse refresh(String accessToken, String clientToken) throws AuthenticationException {
        RefreshRequest request = new RefreshRequest(accessToken, clientToken);
        return (RefreshResponse) sendRequest(request, RefreshResponse.class, authPoints.getRefreshPoint());
    }

    public void validate(String accessToken) throws AuthenticationException {
        ValidateRequest request = new ValidateRequest(accessToken);
        sendRequest(request, null, authPoints.getValidatePoint());
    }

    public void signout(String username, String password) throws AuthenticationException {
        SignoutRequest request = new SignoutRequest(username, password);
        sendRequest(request, null, authPoints.getSignoutPoint());
    }

    public void invalidate(String accessToken, String clientToken) throws AuthenticationException {
        InvalidateRequest request = new InvalidateRequest(accessToken, clientToken);
        sendRequest(request, null, authPoints.getInvalidatePoint());
    }

    private Object sendRequest(Object request, Class<?> model, String authPoint) throws AuthenticationException {
        Gson gson = new Gson();
        String response;

        try {
            response = sendPostRequest(this.authURL + authPoint, gson.toJson(request));
        } catch (IOException e) {
            throw new AuthenticationException(new AuthError("Can't send the request : " + e.getClass().getName(), e.getMessage(), "Unknown"));
        }

        if(model != null)
            return gson.fromJson(response, model);
        else
            return null;
    }

    private String sendPostRequest(String url, String json) throws AuthenticationException, IOException {
        URL serverURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) serverURL.openConnection();
        connection.setRequestMethod("POST");

        // Sending post request
        connection.setDoOutput(true);
        connection.addRequestProperty("Content-Type", "application/json;charset=utf-8");
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        connection.connect();

        int responseCode = connection.getResponseCode();

        if(responseCode == 204) {
            connection.disconnect();
            return null;
        }

        InputStream is;
        if(responseCode == 200)
            is = connection.getInputStream();
        else
            is = connection.getErrorStream();

        String response;

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        response = br.readLine();
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.disconnect();

        while (response != null && response.startsWith("\uFEFF"))
            response = response.substring(1);

        if (responseCode != 200) {
            Gson gson = new Gson();

            if (response != null && !response.startsWith("{"))
                throw new AuthenticationException(new AuthError("Internal server error", response, "Remote"));

            throw new AuthenticationException(gson.fromJson(response, AuthError.class));
        }

        return response;
    }

}
