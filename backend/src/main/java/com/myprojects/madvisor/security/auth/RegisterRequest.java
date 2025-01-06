package com.myprojects.madvisor.security.auth;




public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    private RegisterRequest(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
    }

    public static class Builder {
        private String username;
        private String email;
        private String password;

        public Builder(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }


        public RegisterRequest build() {
            return new RegisterRequest(this.username, this.email, this.password);
        }
    }


}