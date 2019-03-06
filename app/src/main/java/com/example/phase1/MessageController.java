package com.example.phase1;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageController {
    private static MessageController messageController;
    public ArrayList<Integer> list = new ArrayList<>();
    private ExecutorService cloudExecutorService = Executors.newSingleThreadExecutor();
    private ExecutorService storageExecutorService = Executors.newSingleThreadExecutor();

    public static MessageController getMessageController() {
        if (messageController == null)
            messageController = new MessageController();
        return messageController;
    }

    public void fetch(Boolean fetch, final Context context){
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
                }

            });

        }
        else {
            cloudExecutorService.submit(new Runnable() {

                @Override
                public void run(){
                    int[] res = ConnectionManager.getConnectionManager().load(max);
                    int max = -1;
                    Log.i("lllllllll", "1");

                    for (int i : res) {
                        list.add(i);
                        max = Math.max(max, i);
                    }

                    NotificationCenter.getNotificationCenter().data_loaded();
                    StorageManager.getStorageManager().save(max, context);
                }

            });
        }

    }

    private MessageController(){}
}
