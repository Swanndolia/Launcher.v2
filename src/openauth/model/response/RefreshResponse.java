package openauth.model.response;

import openauth.model.AuthProfile;

public class RefreshResponse {

    private String accessToken;

    private String clientToken;

    private AuthProfile selectedProfile;

    public RefreshResponse(String accessToken, String clientToken, AuthProfile selectedProfile) {
        this.accessToken = accessToken;
        this.clientToken = clientToken;
        this.selectedProfile = selectedProfile;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getClientToken() {
        return clientToken;
    }

    public AuthProfile getSelectedProfile() {
        return selectedProfile;
    }

}
