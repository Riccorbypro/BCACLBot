package org.belgiumcampus.aclbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Connect {

    private Connection conn;

    public Connect(String path) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + path, "username", "password");
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            JOptionPane.showMessageDialog(null, "ERROR:\nCode: LC20");
            System.exit(1);
        }
    }

    public void Disconnect() {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            JOptionPane.showMessageDialog(null, "ERROR:\nCode: LC29");
            System.exit(1);
        }
    }

    public ResultSet query(String sql, Object... o) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int i = 1;
            for (Object object : o) {
                ps.setObject(i, object);
                i++;
            }
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            JOptionPane.showMessageDialog(null, "ERROR:\nCode: LC40");
            System.exit(1);
        }
        return rs;
    }

    public boolean insert(String sql, Object... o) {
        boolean result = false;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int i = 1;
            for (Object object : o) {
                ps.setObject(i, object);
                i++;
            }
            result = ps.execute();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            JOptionPane.showMessageDialog(null, "ERROR:\nCode: LC52");
            System.exit(1);
        }
        return result;
    }

    public int update(String sql, Object... o) {
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int i = 1;
            for (Object object : o) {
                ps.setObject(i, object);
                i++;
            }
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            JOptionPane.showMessageDialog(null, "ERROR:\nCode: LC64");
            System.exit(1);
        }
        return result;
    }
}
