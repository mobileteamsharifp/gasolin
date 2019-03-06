package com.example.phase1;

public class ConnectionManager {
    private static ConnectionManager connectionManager;

    public static ConnectionManager getConnectionManager() {
        if (connectionManager == null)
            connectionManager = new ConnectionManager();
        return connectionManager;
    }

    public synchronized int[] load(int num){
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int[] res = new int[10];
        for (int i = 0; i < 10; i++) {
            res[i] = num + i + 1;
        }
        return res;

    }

    private ConnectionManager(){}
}
