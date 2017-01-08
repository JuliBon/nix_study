package com.nixsolutions.bondarenko.study.rest;

import com.nixsolutions.bondarenko.study.rest.errorhandling.*;
import com.nixsolutions.bondarenko.study.rest.resource.UsersResource;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyAppConfig extends ResourceConfig {

    public JerseyAppConfig() {
        register(UsersResource.class);

        register(ValidationExceptionMapping.class);
        register(UserNotFoundExceptionMapper.class);
        register(NotUniqueEmailExceptionMapper.class);
        register(NotUniqueLoginExceptionMapper.class);
        register(GenericExceptionMapper.class);
        register(NotFoundExceptionMapper.class);
    }
}
