package bphc.sih.demo.sihdemoapp.WiFiDirect;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

public class Scheduler {

    private static final int JOB_CHECK_MESSAGES = 1;

    public static void scheduleJobCheckMessages(Context context) {
        ComponentName serviceComponent = new ComponentName(context, JobCheckMessages.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_CHECK_MESSAGES, serviceComponent);
        builder.setMinimumLatency(2 * 60 * 1000);                 //TODO: Find optimum updatedAt for this
        builder.setOverrideDeadline(5 * 60 * 1000);    //TODO: Find optimum updatedAt for this

        JobScheduler jobScheduler;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            jobScheduler = context.getSystemService(JobScheduler.class);
        } else {
            jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        }
        if (jobScheduler != null) {
            for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
                if (jobInfo.getId() == 1) {
                    return;
                }
            }
            jobScheduler.cancel(JOB_CHECK_MESSAGES);
            jobScheduler.schedule(builder.build());
            Log.d("Job Scheduled", "Yes");
        }
    }

    public static class JobCheckMessages extends JobService {

        WiFiDirectService wiFiDirectService;

        @Override
        public boolean onStartJob(JobParameters params) {
            //TODO: Start relay
            //Setup WiFiDirect
            wiFiDirectService = new WiFiDirectService(this);
            wiFiDirectService.setup();
            Log.d("Job Start", "Yes");

            return true;
        }

        @Override
        public boolean onStopJob(JobParameters params) {
            wiFiDirectService.unregisterReceiver();
            return true;
        }
    }
}
