package de.uniks.pmws2122.chat.server.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Watchdog<T> {
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private final long timeout;
    private final Runnable action;

    private ScheduledFuture<?> future;

    public Watchdog(T watchObject, long timeout, Consumer<T> finishCallback) {
        this.timeout = timeout;
        this.action = () -> finishCallback.accept(watchObject);
    }

    public void start() {
        future = service.schedule(this.action, this.timeout, TimeUnit.MILLISECONDS);
    }

    public void recalculateEndTime() {
        this.cancel();
        this.start();
    }

    public void cancel() {
        if (this.future != null) {
            this.future.cancel(false);
            this.future = null;
        }
    }
}
