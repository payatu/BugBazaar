package com.BugBazaar.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloadWorker extends Worker {
    public FileDownloadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        String fileUrl = getInputData().getString("FILE_URL");
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            File outputFile = new File(getApplicationContext().getFilesDir(), "abcd.apk"); // Modify the file name and extension as needed
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            InputStream inputStream = connection.getInputStream();

            byte[] data = new byte[1024];
            int count;
            while ((count = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, count);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            return Result.success();
        } catch (Exception e) {
         //   Log.d("hello", String.valueOf(e));

            e.printStackTrace();
            return Result.failure();
        }
    }
}
