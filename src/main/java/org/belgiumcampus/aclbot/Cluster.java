package org.belgiumcampus.aclbot;

import java.util.ArrayList;
import org.telegram.telegrambots.api.objects.User;

public class Cluster {

    private int clusterID;
    private String clusterName;
    private String dropboxLink;

    public Cluster(int clusterID, String clusterName, String dropboxLink) {
        this.clusterID = clusterID;
        this.clusterName = clusterName;
        this.dropboxLink = dropboxLink;
    }

    public Cluster(int clusterID, String clusterName) {
        this.clusterID = clusterID;
        this.clusterName = clusterName;
    }

    public int getClusterID() {
        return clusterID;
    }

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getDropboxLink() {
        return dropboxLink;
    }

    public void setDropboxLink(String dropboxLink) {
        this.dropboxLink = dropboxLink;
    }

}
