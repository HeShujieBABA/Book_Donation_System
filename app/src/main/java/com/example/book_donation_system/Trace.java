package com.example.book_donation_system;

/**
 * Created by 何书杰 on 2017/10/13.
 */
public class Trace {
    /** 时间 */
    private String acceptTime;
    /** 描述 */
    private String acceptStation;

    public Trace() {
    }

    public Trace(String acceptTime, String acceptStation) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }
}