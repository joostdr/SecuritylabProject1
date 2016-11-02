package com.hr.securitylab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by joost on 2-11-2016.
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    @Autowired
    private MessageSource messages;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception.getMessage().equalsIgnoreCase("blocked")) {
            System.out.println("fak");
        }
    }
}
