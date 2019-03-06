package com.example.phase1;


import android.content.Context;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageController {
    private static MessageController messageController;
    ArrayList<Integer> list = new ArrayList<>();
    private ExecutorService cloudExecutorService = Executors.newSingleThreadExecutor();
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
                    int[] res = StorageManager.getStorageManager().load(max, context);
                    for (int i : res) {
                        list.add(i);
                    }
                    NotificationCenter.getNotificationCenter().data_loaded();
                    isWorking = false;
                }

            });

        }
        else {
            cloudExecutorService.submit(new Runnable() {

                @Override
                public void run(){
                    int[] res = ConnectionManager.getConnectionManager().load(max);
                    int max = -1;

                    for (int i : res) {
                        list.add(i);
                        max = Math.max(max, i);
                    }

                    NotificationCenter.getNotificationCenter().data_loaded();
                    StorageManager.getStorageManager().save(max, context);
                    isWorking = false;
                }

            });
        }

    }

    private MessageController(){}
}
