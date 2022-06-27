package com.unitechApi.Payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LoginRequest {
    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "enter valid Number ")
    private String phoneno;

    @NotBlank
    private String password;

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
