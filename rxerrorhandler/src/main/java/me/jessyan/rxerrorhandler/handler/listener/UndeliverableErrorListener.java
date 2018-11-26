package me.jessyan.rxerrorhandler.handler.listener;

import io.reactivex.annotations.NonNull;

public interface UndeliverableErrorListener {

    void handleError(@NonNull Throwable throwable);
}
