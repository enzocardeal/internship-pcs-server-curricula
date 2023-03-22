package com.poli.internship.api.context;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestHeaderInterceptor implements WebGraphQlInterceptor {
    @Autowired
    private JWTService jwtService;
    @Override
    public Mono<WebGraphQlResponse> intercept (WebGraphQlRequest request, Chain chain) {
        Map<String, Object> context = new HashMap<>();
        String auth = request.getHeaders().getFirst("Authorization");

        DecodedJWT decodedToken = null;
        if (auth != null) {
            decodedToken = this.jwtService.decodeAuthorizationToken(auth);
        }

        if (decodedToken != null) {
            context.put("userId", decodedToken.getClaim("userId").asString());
            context.put("email", decodedToken.getClaim("email").asString());
            context.put("userType", decodedToken.getClaim("userType").asString());
        }

        request.configureExecutionInput(((executionInput, builder) ->
                builder.graphQLContext(context).build()));

        return chain.next(request);
    }

}
