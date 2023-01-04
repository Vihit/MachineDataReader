package com.digitedgy.model;

public class DB {
    private String driver;
    private String url;
    private String user;
    private String password;
    private String table;
    private String equipmentIdColumn;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getEquipmentIdColumn() {
        return equipmentIdColumn;
    }

    public void setEquipmentIdColumn(String equipmentIdColumn) {
        this.equipmentIdColumn = equipmentIdColumn;
    }
}
