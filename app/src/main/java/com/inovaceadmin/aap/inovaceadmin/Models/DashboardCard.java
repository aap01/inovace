package com.inovaceadmin.aap.inovaceadmin.Models;

public class DashboardCard {
    String name,absent,present,total;

    public DashboardCard() {
    }

    public DashboardCard(String name, String absent, String present, String total) {
        this.name = name;
        this.absent = absent;
        this.present = present;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
