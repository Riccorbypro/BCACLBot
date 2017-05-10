package org.belgiumcampus.aclbot;

import java.util.ArrayList;
import org.telegram.telegrambots.api.objects.User;

public class Cluster {

    private int clusterID;
    private String clusterName;
    private ArrayList<User> members;
    private String dropboxLink;

    public Cluster(int clusterID, String clusterName, ArrayList<User> members, String dropboxLink) {
        this.clusterID = clusterID;
        this.clusterName = clusterName;
        this.members = members;
        this.dropboxLink = dropboxLink;
    }

    public Cluster(int clusterID, String clusterName, ArrayList<User> members) {
        this.clusterID = clusterID;
        this.clusterName = clusterName;
        this.members = members;
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

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public String getDropboxLink() {
        return dropboxLink;
    }

    public void setDropboxLink(String dropboxLink) {
        this.dropboxLink = dropboxLink;
    }

    public void addMember(User member) {
        members.add(member);
    }

}
