package org.belgiumcampus.aclbot;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Dropbox implements Runnable {

    private String filePath;

    public Dropbox(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            URL url = new URL("https://api.telegram.org/file/bot" + Config.BOT_TOKEN + "/" + filePath);
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
