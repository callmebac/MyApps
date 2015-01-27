package com.test.myotherapplications;

/**
 * Created by tian on 2014/11/5.
 */
public interface Probe {

    public void sendData();

    public interface DataListener {
        public void onDataReceived(String data);
    }

    public void registerListener(DataListener... listener);

    public void unRegisterListener();
}
