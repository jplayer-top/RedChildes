package com.oblivion.redchildpuls.event;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/9.
 */
public class EventMessage {
    public String addressArea;
    public String addressDetail;
    public int id;
    public String name;
    public int price;

    public EventMessage(String addressArea, String addressDetail, int id, String name, int price, int stype) {
        this.addressArea = addressArea;
        this.addressDetail = addressDetail;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stype = stype;
    }

    public int stype;
}
