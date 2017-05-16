package org.belgiumcampus.aclbot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class GroupBackend {

    private ArrayList<Group> groups;
    private ArrayList<Cluster> clusters;
    private Connect conn;

    public GroupBackend() {
        try {
            conn = new Connect();
            groups = new ArrayList<>();
            clusters = new ArrayList<>();
            ResultSet rs = conn.query("SELECT * FROM `clusters`");
            Cluster c = null;
            while (rs.next()) {
                c = new Cluster(rs.getInt("clusterID"), rs.getString("clusterName"));
                clusters.add(c);
            }
            rs = conn.query("SELECT * FROM `groups`");
            Group g = null;
            while (rs.next()) {
                g = new Group(rs.getInt("groupID"), Integer.parseInt(rs.getString("groupName").split(" ")[0]), rs.getString("groupName").split(" ")[1], new ArrayList<Student>(), getCluster(rs.getInt("clusterID")));
                ResultSet rss = conn.query("SELECT * FROM `students` WHERE `groupID`=?", g.getGroupID());
                while (rss.next()) {
                    g.addMember(new Student(rss.getInt("telegramID"), rss.getLong("chatID"), rss.getString("firstName"), rss.getString("surname"), rss.getString("username"), g));
                }
                groups.add(g);
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

    public String[] getClusters() {
        ArrayList<String> list = new ArrayList<>();
        for (Cluster cluster : clusters) {
            list.add(cluster.getClusterName());
        }
        String[] listArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            listArr[i] = list.get(i);
        }
        return listArr;
    }

    public int deleteGroup(String groupID) {
        int result = -1;

        result = conn.update("DELETE * FROM `groups` WHERE `groupID`=?", groupID);

        return result;
    }

    public int deleteCluster(String clusterID) {
        int result = -1;

        result = conn.update("DELETE * FROM `clusters` WHERE `clusterID`=?", clusterID);

        return result;
    }

    public void addGroup(Group group) {
        conn.insert("INSERT INTO `groups`(`groupName`, `clusterID`) VALUES (?, ?)", group.getGroupNo() + " " + group.getTimeslot(), group.getCluster().getClusterID());
    }

    public void addCluster(Cluster cluster) {
        conn.insert("INSERT INTO `clusters`(`clusterName`, `dropboxLink`) VALUES (?, ?);", cluster.getClusterName(), cluster.getDropboxLink());
    }

    public void showGroup(String groupID) {
        for (Group group : groups) {
            if ((group.getGroupNo()+" "+group.getTimeslot()).equalsIgnoreCase(groupID)) {
                MembersGUI gui = new MembersGUI(group);
                gui.setVisible(true);
            }
        }
    }

    public void showCluster(String clusterID) {
    }
}
