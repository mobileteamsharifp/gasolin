package com.example.phase1;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class StorageManager {
    private static StorageManager storageManager;
    private String filename = "myfile.txt";
    FileOutputStream outputStream;
    FileInputStream fileInputStream;

    public static StorageManager getStorageManager() {
        if (storageManager == null)
            storageManager = new StorageManager();
        return storageManager;
    }

    public int[] load(final int number,final Context context){
        int max = 0;
        try {
            fileInputStream = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineData;
            do {
                lineData = bufferedReader.readLine();
            } while (lineData != null && Integer.valueOf(lineData) <= number);
            max = Integer.valueOf(lineData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (max == 0)
            return new int[0];
        int[] res = new int[10];
        for (int i = 0; i < 10; i++) {
            res[i] = max + i -9;
        }
        return res;

    }

    public boolean createFileDir(Context context, boolean clear){
        File file = new File(context.getFilesDir(), filename);
        boolean made = true;
        if(!file.exists()){
            made = file.mkdir();
        }

        if (clear){
            try {
                outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(outputStream);
                osw.write("");
                osw.flush();
                osw.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return made;
    }

    public void save(final int number, final Context context){
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputStreamWriter osw = new OutputStreamWriter(outputStream);

        try {
            osw.write(Integer.toString(number) + "\n");
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StorageManager(){

    }
}
