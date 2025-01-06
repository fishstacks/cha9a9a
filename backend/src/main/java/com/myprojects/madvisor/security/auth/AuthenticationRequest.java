package com.myprojects.madvisor.security.auth;

public class AuthenticationRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AuthenticationRequest() {
    }
    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private AuthenticationRequest(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
    }

    public static class Builder {
        private String email;
        private String password;

        public Builder(String email, String password) {
            this.email = email;
            this.password = password;
        }


        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public AuthenticationRequest build() {
            return new AuthenticationRequest(this.email, this.password);
        }
    }

}