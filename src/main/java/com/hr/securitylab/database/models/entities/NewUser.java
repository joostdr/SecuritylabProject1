package com.hr.securitylab.database.models.entities;

import com.hr.securitylab.services.validation.annotations.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Joost on 16-10-2016.
 */
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class NewUser {

    @Size(min=8, max=20)
    @UsernameNotInUse
    @NotEmpty
    @NotNull
    private String username;

    @Size(min=8, max=20)
    @NotEmpty
    @NotNull
    @ValidPassword
    private String password;

    private String matchingPassword;

    @NotEmpty
    @NotNull
    private String productKey;

    public NewUser() {
    }

    public NewUser(String username, String password, String matchingPassword, String productKey) {
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.productKey = productKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }
}
