package com.digitedgy.poller;

import com.digitedgy.model.DB;
import com.digitedgy.model.Equipment;
import com.digitedgy.util.ConfigReader;

import java.sql.*;

public class DBStore {

    private static DBStore dbStore = null;
    private Connection connection;
    private String table = "";
    private String equipmentIdColumn = "";

    private DBStore() {
        DB db = ConfigReader.get().getPoll().getDb();
        table = db.getTable();
        equipmentIdColumn = db.getEquipmentIdColumn();
        try {
            Class.forName(db.getDriver());
            connection = DriverManager.getConnection(db.getUrl(),db.getUser(),db.getPassword());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static DBStore getInstance() {
        if(dbStore==null)
            dbStore = new DBStore();
        return dbStore;
    }

    public boolean poll(Equipment equipment) {
        boolean pollSuccess = false;
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT do_fetch from " + table + " WHERE " + equipmentIdColumn + " = '" + equipment.getId() + "'";
            ResultSet resultSet = stmt.executeQuery(query);
            System.out.println("Result set size for query "+query+" is "+resultSet.getFetchSize());
            if(resultSet.next()) {
                pollSuccess = resultSet.getBoolean("do_fetch");
                System.out.println("Poll result is "+pollSuccess);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pollSuccess;
    }

    public int pushToStore(Equipment equipment, String data) {
        PreparedStatement stmt = null;
        String query = "UPDATE "+table+" SET do_fetch = ? , last_fetched_value = ? WHERE "+equipmentIdColumn+" = ?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setBoolean(1,false);
            stmt.setString(2,data);
            stmt.setInt(3,equipment.getId());
//            System.out.println("Update query : "+stmt.);
            int status = stmt.executeUpdate();
            if(status > 0) {
                return status;
            } else {
                return -1;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
}
