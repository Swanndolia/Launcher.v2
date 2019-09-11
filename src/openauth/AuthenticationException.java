package openauth;

import openauth.model.AuthError;

@SuppressWarnings("serial")
public class AuthenticationException extends Exception {

    private AuthError model;

    public AuthenticationException(AuthError model) {
        super(model.getErrorMessage());
        this.model = model;
    }

    public AuthError getErrorModel() {
        return model;
    }
}