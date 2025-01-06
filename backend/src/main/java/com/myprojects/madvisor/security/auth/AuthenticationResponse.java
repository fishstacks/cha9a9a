package com.myprojects.madvisor.security.auth;





public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse() {
    }


    private AuthenticationResponse(Builder builder) {
        this.token = builder.token;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;


        public Builder token(String token) {
            this.token = token;
            return this;
        }


        public AuthenticationResponse build() {
            return new AuthenticationResponse(this.token);
        }
    }

    // Getter and Setter methods
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
