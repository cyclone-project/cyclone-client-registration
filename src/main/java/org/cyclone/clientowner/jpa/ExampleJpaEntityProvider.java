package org.cyclone.clientowner.jpa;

import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

import java.util.Collections;
import java.util.List;


public class ExampleJpaEntityProvider implements JpaEntityProvider {

    @Override
    public List<Class<?>> getEntities() {
        return Collections.<Class<?>>singletonList(ClientOwner.class);
    }

    @Override
    public String getChangelogLocation() {
    	return "META-INF/client-owner-changelog.xml";
    }
    
    @Override
    public void close() {
    }

    @Override
    public String getFactoryId() {
        return ExampleJpaEntityProviderFactory.ID;
    }
}
