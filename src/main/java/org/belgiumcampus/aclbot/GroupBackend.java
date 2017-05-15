package org.belgiumcampus.aclbot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupBackend {

    private ArrayList<Group> groups;
    private ArrayList<Cluster> clusters;
    private Connect conn;

    public GroupBackend() {
        try {
            conn = new Connect();
            ResultSet rs = conn.query("SELECT * FROM `groups`");
            Group g = null;
            while (rs.next()) {
                g = new Group(rs.getInt("groupID"), Integer.parseInt(rs.getString("groupName").split(" ")[0]), rs.getString("groupName").split(" ")[1], new ArrayList<Student>(), getCluster(rs.getInt("clusterID")));
                ResultSet rss = conn.query("SELECT * FROM `students` WHERE `groupID`=?", g.getGroupID());
                while (rss.next()) {
                    g.addMember(new Student(rs.getInt("telegramID"), rs.getLong("chatID"), rs.getString("firstName"), rs.getString("surname"), rs.getString("username"), g));
                }
                groups.add(g);
            }
            rs = conn.query("SELECT * FROM `clusters`");
            Cluster c = null;
            while (rs.next()) {
                c = new Cluster(rs.getInt("clusterID"), rs.getString("clusterName"));
                clusters.add(c);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    private Cluster getCluster(int clusterID) throws SQLException {
        ResultSet rs = conn.query("SELECT * FROM `clusters` WHERE `clusterID`=?", clusterID);
        Cluster c = null;
        while (rs.next()) {
            c = new Cluster(rs.getInt("clusterID"), rs.getString("clusterName"));
        }
        return c;
    }

    public String[] getGroups() {
        ArrayList<String> list = new ArrayList<>();
        for (Group group : groups) {
            list.add(group.getGroupNo() + " " + group.getTimeslot() + " (Cluster " + group.getCluster().getClusterName() + ")");
        }
        String[] listArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            listArr[i] = list.get(i);
        }
        return listArr;
    }

}
