package com.horizon.dataCleaning;

import com.horizon.GenDataKafka.IConstant;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class ToMysql implements IRichBolt {

    private String str;
    private FSDataOutputStream fo;

    Connection conn;
    private Statement state;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        System.out.println("开始将数据输入mysql");
        //准备数据库连接
        try{
            String driver = IConstant.DRIVER;
            Class.forName(driver);
            conn = DriverManager.getConnection(IConstant.JDBC_URL,IConstant.USER,IConstant.PASSWORD);
            state = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Tuple tuple) {
        String fan_no = tuple.getStringByField("fan_no");
        String warn_time =  tuple.getStringByField("warn_time");
        System.out.println("toMql--------------"+fan_no);
        int count = tuple.getIntegerByField("warn_count");
        try{
            state.execute("insert into WARN_NEWS(FAN_NO,WARN_TIME,WARN_COUNT) values('" + fan_no + "','"+warn_time+"','"+count+"')");
            System.out.println("insert into WARN_NEWS(FAN_NO,WARN_TIME,WARN_COUNT) values('" + fan_no + "',"+warn_time+","+count+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanup() {
        try {
            state.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
