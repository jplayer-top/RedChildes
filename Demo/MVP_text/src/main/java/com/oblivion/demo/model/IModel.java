package com.oblivion.demo.model;

/**
 * Model 是数据源层。比如数据库接口或者远程服务器的api。
 * Created by oblivion on 2016/11/16.
 */
public interface IModel {
    /**
     * 显示是否连接上网络
     *
     * @return
     */
    boolean isConnectionNet();

    /**
     * 获取到网络加载的数据
     *
     * @return
     */
    String getJsonObject();
}
