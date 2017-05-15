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
            }
            rs = conn.query("SELECT * FROM `students` WHERE `groupID`=?", g.getGroupID());
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

}
