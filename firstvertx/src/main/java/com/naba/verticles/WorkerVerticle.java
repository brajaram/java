package com.naba.verticles;

import io.vertx.core.AbstractVerticle;

/**
 * An example of worker verticle
 * that consumes a message & reply back
 */
public class WorkerVerticle extends AbstractVerticle {


    @Override
    public void start() throws Exception {
        System.out.println("[Worker] Starting in " + Thread.currentThread().getName());

        vertx.eventBus().<String>consumer("sample.data", message -> {
            System.out.println("[Worker] Consuming data in " + Thread.currentThread().getName());
            vertx.setTimer(15000, id -> {
                message.reply(200);
                vertx.cancelTimer(id);
            });
        });
    }
}