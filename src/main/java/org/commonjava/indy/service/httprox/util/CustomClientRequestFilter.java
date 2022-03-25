package org.commonjava.indy.service.httprox.util;


import org.commonjava.indy.service.httprox.handler.Tokens;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class CustomClientRequestFilter implements ClientRequestFilter
{

    @Inject
    Tokens tokens;

    @Override
    public void filter( ClientRequestContext requestContext )
    {
        requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.getToken());
    }
}
