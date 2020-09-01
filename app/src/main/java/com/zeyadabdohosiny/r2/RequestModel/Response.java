package com.zeyadabdohosiny.r2.RequestModel;

public class Response {
    boolean Done;

    public Response(boolean done) {
        Done = done;
    }

    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }
}
