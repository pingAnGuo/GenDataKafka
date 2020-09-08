package web.Util;



import org.springframework.stereotype.Service;
import web.vo.Alarm;
import web.vo.Series;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*
连接数据库
 */

@Service("dbService")
public class DBUtil {
    private static final String TABLE = "WARN_NEWS";
    private static final String JDBC_URL= "jdbc:mysql://172.16.29.112:3306/fandata?useSSL=FALSE&useUnicode=TRUE&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String USER="hive";
    private static final String PASSWORD="123456";

    public List<Alarm> getFromDB() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName(DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement stat = conn.createStatement();
        List<Alarm> list=new ArrayList<Alarm>();
        String sql="select * from "+TABLE;
        ResultSet rs=stat.executeQuery(sql);
        while (rs.next()){
            Alarm Alarm=new Alarm(rs.getString(1),rs.getString(2),rs.getInt(3));
            list.add(Alarm);
        }
        rs.close();
        stat.close();
        conn.close();
        return list;
    }
    public List<Series> getFromDBByTime(String time) throws SQLException, ParseException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println(time);
        Class.forName(DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(JDBC_URL, USER,PASSWORD);
        Statement stat = conn.createStatement();
        //08:09:04
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date time_web = df.parse(time);
//        int min = time_web.getMinutes();
        List<Alarm> list_alarm=new ArrayList<Alarm>();

        String sql="select * from " + TABLE;
        ResultSet rs= stat.executeQuery(sql);
        HashMap<String,Integer> map = new HashMap<>();
        Date now_10 = time_web;
        for(int j = 0;j<6;j++){
            now_10 = new Date(now_10.getTime() - 600000); //10分钟前的时间
            String key = df.format(now_10);
            map.put(key,0);
        }
        while (rs.next()) {
            Alarm Alarm = new Alarm(rs.getString(1), rs.getString(2), rs.getInt(3));
            String time_db = rs.getString(2).split(" ")[1];
            Date time_db_D = df.parse(time_db);
            System.out.println(time_db_D.getTime() + "---" + time_web.getTime());
            if (time_db_D.getTime() - time_web.getTime() < 60 * 60 * 1000) {
                //list_alarm.add(Alarm);
                for (String key : map.keySet()) {
                    int key_min = df.parse(key).getMinutes();
                    int alarm_min = time_db_D.getMinutes();
                    System.out.println(key_min + "+++" + alarm_min);
                    if (alarm_min - key_min < 10) {
                        map.put(key, map.get(key) + 1);
                    }
                }
            }
        }
        rs.close();
        stat.close();
        conn.close();
        List<Series> serieslist = new ArrayList<>();
        for(String key:map.keySet()){
           Series s = new Series(key,map.get(key));
           serieslist.add(s);
        }
//        List<Series> serieslist = new ArrayList<>();
//        Series s = new Series("08:00:16",44);
//        Series s1 = new Series("08:10:16",22);
//        Series s2 = new Series("08:20:16",43);
//        Series s3 = new Series("08:30:16",6);
//        Series s4 = new Series("08:40:16",100);
//        Series s5 = new Series("08:50:16",501);
//        serieslist.add(s);
//        serieslist.add(s1);
//        serieslist.add(s2);
//        serieslist.add(s3);
//        serieslist.add(s4);
//        serieslist.add(s5);

        return serieslist;
    }
}
