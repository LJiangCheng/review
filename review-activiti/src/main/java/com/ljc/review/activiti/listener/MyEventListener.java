package com.ljc.review.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

public class MyEventListener implements ActivitiEventListener {

    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()) {
            case JOB_EXECUTION_SUCCESS:
                System.out.println("A job execute success!");
                break;
            case JOB_EXECUTION_FAILURE:
                System.out.println("A job execute failed...");
                break;
            case PROCESS_STARTED:
                System.out.println("A process started!");
                break;
            default:
                System.out.println("Event received: " + event.getType());
        }
    }

    @Override
    public boolean isFailOnException() {
        System.out.println("Event Handler failed on exception!");
        return false;
    }
}
