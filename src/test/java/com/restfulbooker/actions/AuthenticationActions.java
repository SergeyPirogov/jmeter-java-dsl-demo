package com.restfulbooker.actions;

import org.apache.http.entity.ContentType;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;

import static us.abstracta.jmeter.javadsl.JmeterDsl.httpSampler;
import static us.abstracta.jmeter.javadsl.JmeterDsl.jsonExtractor;

/**
 * AuthenticationActions provides authentication-related operations.
 */
public class AuthenticationActions {

    private final String authUrl;

    public AuthenticationActions(String authUrl) {
        this.authUrl = authUrl;
    }

    /**
     * Authenticate and extract token for future requests
     */
    public DslHttpSampler authenticate(String username, String password) {
        String authBody = String.format("""
                {
                    "username" : "%s",
                    "password" : "%s"
                }
                """, username, password);

        return httpSampler("Authenticate", authUrl + "/auth")
                .post(authBody, ContentType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .children(
                        jsonExtractor("token", "token")
                );
    }
}
