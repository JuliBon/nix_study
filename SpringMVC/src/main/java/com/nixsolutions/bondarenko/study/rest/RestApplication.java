package com.nixsolutions.bondarenko.study.rest;

import com.nixsolutions.bondarenko.study.rest.resource.UsersResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {

    public RestApplication() {
        register(UsersResource.class);
        register(JacksonFeature.class);
    }
}
