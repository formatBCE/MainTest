package ua.mainacad.maintest.maintest.workers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ZipWorker extends Worker {

    public ZipWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("ZipWorker", "Zipping images");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.retry();
        }
        return Result.success();
    }
}
