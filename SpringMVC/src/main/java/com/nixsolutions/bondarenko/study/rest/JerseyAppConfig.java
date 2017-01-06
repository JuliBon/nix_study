package com.nixsolutions.bondarenko.study.rest;

import com.nixsolutions.bondarenko.study.rest.resource.UsersResource;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyAppConfig extends ResourceConfig {

    public JerseyAppConfig() {
        register(UsersResource.class);
    }
}
