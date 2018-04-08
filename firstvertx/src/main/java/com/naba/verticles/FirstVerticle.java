package com.naba.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;

/**
 * A simple program to
 * > set a timer & send a message to worker
 * > On worker, recieve the message complete the task and send acknowledge
 */
public class FirstVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> fut) {
        vertx.deployVerticle("com.naba.verticles.WorkerVerticle",
                new DeploymentOptions().setWorker(true));

        this.sendAndRecieveMessage();

    }

    private void sendAndRecieveMessage(){
        vertx.setTimer(3000, id -> {
            System.out.println("Timer fired");
            vertx.eventBus().send("sample.data", "message from master", res -> {
                if(res.succeeded()) {
                    System.out.println("response recieved");
                    vertx.cancelTimer(id);
                    System.out.println("canceled timer");
                    this.sendAndRecieveMessage();
                }
            });

        });


    }
}
