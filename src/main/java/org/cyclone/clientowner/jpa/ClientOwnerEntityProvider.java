package org.cyclone.clientowner.jpa;

import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

import java.util.Collections;
import java.util.List;


public class ClientOwnerEntityProvider implements JpaEntityProvider {

    @Override
    public List<Class<?>> getEntities() {
        return Collections.<Class<?>>singletonList(ClientOwnerEntity.class);
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
        return ClientOwnerEntityProviderFactory.ID;
    }
}
