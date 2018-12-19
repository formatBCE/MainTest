package ua.mainacad.maintest.maintest.workers;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.List;

public class MyWorkerManager {

    private static final String POSTS_TAG = "posts_tag";

    public static String scheduleJob() {
        List<OneTimeWorkRequest> filterWorkers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            filterWorkers.add(new OneTimeWorkRequest
                    .Builder(ImageFilterWorker.class)
                    .addTag(POSTS_TAG)
                    .build());
        }
        final OneTimeWorkRequest zipWorker = new OneTimeWorkRequest
                .Builder(ZipWorker.class)
                .addTag(POSTS_TAG)
                .build();
        Constraints myConstraints = new Constraints
                .Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        final OneTimeWorkRequest uploadWorker = new OneTimeWorkRequest
                .Builder(UploadWorker.class)
                .setConstraints(myConstraints)
                .addTag(POSTS_TAG)
                .build();
        WorkManager.getInstance()
                .beginWith(filterWorkers)
                .then(zipWorker)
                .then(uploadWorker)
                .enqueue();
        return POSTS_TAG;
    }

}
