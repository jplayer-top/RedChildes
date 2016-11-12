package com.oblivion.qqmessage.bean;

import android.util.Xml;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import java.io.Serializable;

/**
 * Created by oblivion on 2016/11/9.
 */
//在这里继承Serializable如果在子类上继承，会报不可描述的错误========================
public class ProtocolObj implements Serializable {

    //private XStream xStream;卧槽共有的也不能用。。。。。。。。。。。。。。。。

    //private XStream xStream;-------------不能抽取成私有变量，，子类没法用了
    public String toXML() {
        XStream xStream = new XStream();
        xStream.alias(this.getClass().getSimpleName(), this.getClass());
        return xStream.toXML(this);
    }

    public Object fromXML(String xml) {
        XStream xStream = new XStream();
        xStream.alias(this.getClass().getSimpleName(), this.getClass());
        return xStream.fromXML(xml);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Object fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, this.getClass());
    }

}
