package it.idm.alpinebits.middleware.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dummy server - deleteable.
 */
public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    public void run() {
        SimpleContainer container = new SimpleContainer();

        container.addMiddleware((ctx, chain) -> {
            LOG.info("Middleware 1 executing");
            chain.next();
            LOG.info("Middleware 1 and some else");
        });
        container.addMiddleware((ctx, chain) -> {
            LOG.info("Middleware 2 executing");
            chain.next();
        });
        container.addMiddleware((ctx, chain) -> {
            LOG.info("Middleware 3 executing");
            chain.next();
        });

        container.invokeChain();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

}
