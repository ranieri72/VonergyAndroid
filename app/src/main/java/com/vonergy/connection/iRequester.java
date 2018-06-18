package com.vonergy.connection;

public interface iRequester {
    void onTaskCompleted(Object o);

    void onTaskStarted();

    void onTaskFailed(String errorMessage);
}
