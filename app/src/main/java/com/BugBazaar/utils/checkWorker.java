package com.BugBazaar.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.BugBazaar.ui.NavigationDrawer_Dashboard;

import java.io.File;

import dalvik.system.DexClassLoader;

public class checkWorker {

    private final Context context;

    public interface DiscountCallback {
        void onDiscountCalculated(double discountedPrice);
    }

    public checkWorker(Context context) {
        this.context = context;
    }

    public void filesendtodownload(NavigationDrawer_Dashboard callback, Uri data) {

        if (NetworkUtils.isNetworkAvailable(context)) {

            String fileUrl = String.valueOf(data); // Replace with the actual file URL
            String fileName = "abcd.apk"; // Specify the desired file name

            Data inputData = new Data.Builder()
                    .putString("FILE_URL", fileUrl)
                    .putString("FILE_NAME", fileName)
                    .build();

            OneTimeWorkRequest fileDownloadWork =
                    new OneTimeWorkRequest.Builder(FileDownloadWorker.class)
                            .setInputData(inputData)
                            .build();
            WorkManager.getInstance().enqueue(fileDownloadWork);
            WorkManager.getInstance().getWorkInfoByIdLiveData(fileDownloadWork.getId())
                    .observe((LifecycleOwner) context, workInfo -> {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            double discountedPrice = executeDynamicallyLoadedCode(fileName);
                            DiscountDataManager.getInstance().setDiscountPrice(discountedPrice);
                            callback.onDiscountCalculated(discountedPrice);
                        } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                            // The download failed
                        }
                    });
        }

        else
        {

            NetworkUtils.showNoInternetDialog(context);
        }
    }



    private double executeDynamicallyLoadedCode(String fileName) {
        String apkPath = context.getFilesDir() + File.separator + fileName;

        double discountedPrice = 0;
        try {
            ClassLoader classLoader = new DexClassLoader(
                    apkPath,
                    context.getDir("dex", 0).getAbsolutePath(),
                    null,
                    context.getClassLoader()
            );

            Class<?> discountModuleClass = classLoader.loadClass("jakhar.aseem.dynamic_code_load.DiscountModule");
            Object discountModule = discountModuleClass.getDeclaredConstructor().newInstance();
            double totalPrice = 100.0; // Replace with your actual price
            discountedPrice = (double) discountModuleClass.getMethod("applyDiscount", double.class).invoke(discountModule, totalPrice);

            Toast.makeText(context, "discountedPrice price" + discountedPrice, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return discountedPrice;
    }
}
