package ua.mainacad.maintest.maintest.workers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ImageFilterWorker extends Worker {

    public ImageFilterWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("ImageWorker", "Applying filter to image " + hashCode());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.retry();
        }
        return Result.success();
    }
}
