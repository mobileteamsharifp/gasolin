package com.example.phase1;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageController {
    private static MessageController messageController;
    ArrayList<Integer> list = new ArrayList<>();
    private ExecutorService cloudExecutorService =  Executors.newSingleThreadExecutor();
    private ExecutorService storageExecutorService = Executors.newSingleThreadExecutor();
    private Boolean isWorking = false;

    public static MessageController getMessageController() {
        if (messageController == null)
            messageController = new MessageController();
        return messageController;
    }

    public void fetch(Boolean fetch, final Context context){
        if (isWorking)
            return;
        isWorking = true;
        final int max = list.size();
        if (fetch){
            storageExecutorService.submit(new Runnable() {

                @Override
                public void run(){
                    Log.i("uuuuuuuuuuuuuuuuuuu", "6");
                    int[] res = StorageManager.getStorageManager().load(max, context);
                    Log.i("uuuuuuuuuuuuuuuuuuu", "7");
                    for (int i : res) {
                        list.add(i);
                    }
                    Log.i("uuuuuuuuuuuuuuuuuuu", "8");
                    NotificationCenter.getNotificationCenter().data_loaded();
                    Log.i("uuuuuuuuuuuuuuuuuuu", "2");
                    isWorking = false;
                }

            });

        }
        else {
            cloudExecutorService.submit(new Runnable() {

                @Override
                public void run(){
                    Log.i("uuuuuuuuuuuuuuuuuuu", "9");
                    int[] res = ConnectionManager.getConnectionManager().load(max);
                    int max = -1;
                    Log.i("uuuuuuuuuuuuuuuuuuu", "10");

                    for (int i : res) {
                        list.add(i);
                        max = Math.max(max, i);
                    }

                    Log.i("uuuuuuuuuuuuuuuuuuu", "11");
                    NotificationCenter.getNotificationCenter().data_loaded();
                    Log.i("uuuuuuuuuuuuuuuuuuu", "12");
                    StorageManager.getStorageManager().save(max, context);
                    Log.i("uuuuuuuuuuuuuuuuuuu", "13");
                    isWorking = false;
                }

            });
        }

    }

    private MessageController(){}

    public void stop() {
        this.cloudExecutorService.shutdown();
        this.storageExecutorService.shutdown();
    }
}
