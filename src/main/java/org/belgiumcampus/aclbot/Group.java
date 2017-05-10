package org.belgiumcampus.aclbot;

import java.util.ArrayList;
import org.telegram.telegrambots.api.objects.User;

public class Group {

    private int groupID;
    private int groupNo;
    private String timeslot;
    private ArrayList<Student> members;
    private Cluster cluster;

    public Group(int groupID, int groupNo, String timeslot, ArrayList<Student> members, Cluster cluster) {
        this.groupID = groupID;
        this.groupNo = groupNo;
        this.timeslot = timeslot;
        this.members = members;
        this.cluster = cluster;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public ArrayList<Student> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Student> members) {
        this.members = members;
    }

    public void addMember(Student user) {
        members.add(user);
    }

}
