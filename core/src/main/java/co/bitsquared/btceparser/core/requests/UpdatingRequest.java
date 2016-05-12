package co.bitsquared.btceparser.core.requests;

import java.util.Timer;
import java.util.TimerTask;

public abstract class UpdatingRequest extends Request {

    private long milliSecondsInterval;
    private boolean canUpdate = true;
    private Timer timer = new Timer();

    protected UpdatingRequest(Request request, int interval) {
        super(request);
        updateMilliSeconds(interval);
    }

    public final void updateInterval(int secondsUpdateInterval, boolean cancelCurrentTask) {
        updateMilliSeconds(secondsUpdateInterval);
        if (cancelCurrentTask && cancelRequest()) {
            processRequest();
        }
    }

    public final void stopUpdating(boolean cancelCurrentTask) {
        if (cancelCurrentTask) {
            cancelRequest();
        }
        canUpdate = false;
    }

    public final void startUpdating() {
        canUpdate = true;
        if (task == null || isDone()) {
            processRequest();
        }
    }

    protected final void scheduleNextTask() {
        if (!canUpdate) return;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                processRequest();
            }
        }, milliSecondsInterval);
    }

    private void updateMilliSeconds(int secondsUpdateInterval) {
        if (secondsUpdateInterval < 2) {
            secondsUpdateInterval = 2;
        }
        milliSecondsInterval = secondsUpdateInterval * 1000;
    }

}
