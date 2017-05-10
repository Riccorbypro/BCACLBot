package org.belgiumcampus.aclbot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Dropbox implements Runnable {

    private String fileUrl, fileName;
    private Cluster cluster;

    public Dropbox(String fileUrl, String fileName, Cluster cluster) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.cluster = cluster;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();

            File file = new File("Files/" + fileName);
            OutputStream outputStream = new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
