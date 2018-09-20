package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Container;

/**
 * Builder for AlpineBits middleware {@link Container}.
 */
public class ContainerBuilder {

    /**
     * Build and return a {@link SimpleContainer}.
     *
     * @return a {@link SimpleContainer}
     */
    public static SimpleContainer buildSimpleContainer() {
        return new SimpleContainer();
    }

}
