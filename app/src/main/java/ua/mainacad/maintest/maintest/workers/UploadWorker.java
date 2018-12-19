package ua.mainacad.maintest.maintest.workers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {

    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("UploadWorker", "Uploading zip file");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.retry();
        }
        Log.e("UploadWorker", "Done!");
        return Result.success();
    }
}
