package com.horizon.dataCleaning;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.windowing.TupleWindow;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class WindowMonitor extends BaseWindowedBolt {
    private String sql = "insert into WARN_NEWS(fan_no,warn_time,warn_count) values(?,?,?)";
    Connection conn=null;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        System.out.println("------开始输入mysql");
        // NOOP
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fandata?useSSL=false&serverTimezone=UTC", "root", "li1105");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(TupleWindow tupleWindow) {
        System.out.println("开始监控正常数据");
        Map<String, Integer> hashMap = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取系统当前时间
        for(Tuple input : tupleWindow.get()){
            String fan_no = input.getStringByField("fan_no");
            Double temp = input.getDoubleByField("temp");
            System.out.println(fan_no+"+++++++++++++"+temp);
            if(temp > 40){
                Integer value = hashMap.get(fan_no);
                if (value == null) {
                    hashMap.put(fan_no, 1);
                } else {
                    hashMap.put(fan_no, value + 1);
                }
            }

        }
        for (Map.Entry<String, Integer> e : hashMap.entrySet()) {

            if(e.getValue() > 5){
                System.out.println("输出结果： =============="+e.getKey() + ":" + e.getValue());
                PreparedStatement pst = null;//用来执行SQL语句查询，对sql语句进行预编译处理
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, e.getKey());
                    pst.setString(2, df.format(date));
                    pst.setInt(3, e.getValue());
                    pst.executeUpdate();
                    pst.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void cleanup() {
        // NOOP
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
