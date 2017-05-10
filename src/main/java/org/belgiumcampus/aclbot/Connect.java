package org.belgiumcampus.aclbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connect {

    private Connection conn;

    public Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/belgiumcampusacl", "campus", "\"L8t09908G{3O0X");
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
    }

    public void Disconnect() {
        try {
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
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
            System.err.println("ERROR: " + ex);
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
            System.err.println("ERROR: " + ex);
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
            System.err.println("ERROR: " + ex);
        }
        return result;
    }
}
