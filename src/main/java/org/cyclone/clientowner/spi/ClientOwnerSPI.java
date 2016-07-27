package org.cyclone.clientowner.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

/**
 * Created by sturgelose on 27/07/2016.
 */
public class ClientOwnerSPI implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "client-owner";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ClientOwnerService.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ClientOwnerServiceProviderFactory.class;
    }
}
