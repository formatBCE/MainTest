package ua.mainacad.maintest.maintest.workers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import ua.mainacad.maintest.maintest.MyApp;

import java.util.concurrent.atomic.AtomicBoolean;

public class PostsInfoUpdateWorker extends Worker {

    private final SAV lock = new SAV();

    public PostsInfoUpdateWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("Worker", "Start");
        AtomicBoolean hasError = new AtomicBoolean(false);
        final DatabaseReference posts = MyApp.get().getFirebaseDb()
                .getReference("posts");
        posts.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final long childrenCount = dataSnapshot.getChildrenCount();
                        Log.e("Worker", "Posts number: " + childrenCount);
                        posts.removeEventListener(this);
                        lock.release();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        posts.removeEventListener(this);
                        hasError.set(true);
                        lock.release();
                    }
                }
        );
        try {
            lock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("Worker", "Retry");
            return Result.retry();
        }
        if (hasError.get()) {
            Log.e("Worker", "Failure");
            return Result.failure();
        }
        Log.e("Worker", "Success");
        return Result.success();
    }
}
