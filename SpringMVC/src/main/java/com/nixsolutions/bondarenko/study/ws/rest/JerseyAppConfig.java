package com.nixsolutions.bondarenko.study.ws.rest;

import com.nixsolutions.bondarenko.study.ws.rest.errorhandling.*;
import com.nixsolutions.bondarenko.study.ws.rest.resource.UsersResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyAppConfig extends ResourceConfig {

    public JerseyAppConfig() {
        register(UsersResource.class);
        register(JacksonFeature.class);

        register(ValidationExceptionMapper.class);
        register(UserNotFoundExceptionMapper.class);
        register(NotUniqueEmailExceptionMapper.class);
        register(NotUniqueLoginExceptionMapper.class);
        register(GenericExceptionMapper.class);
        register(WebApplicationExceptionMapper.class);
    }
}
