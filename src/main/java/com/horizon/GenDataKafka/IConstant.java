package com.horizon.GenDataKafka;

public interface IConstant {

	String FJ_DATA_WT02287 = "FJData/WT02287.csv";
	String FJ_DATA_WT02288 = "FJData/WT02288.csv";
	String FJ_DATA_WT02289 = "FJData/WT02289.csv";
	int TIME_LEN = 30;//30秒内发电机的温度
	int TIME_INTERVAL = 5;//每5秒监控一次，时间间隔
	int COUNT = 5;//高于80度的次数5次
	double MAX_TEMP = 80;//80度以上会报警

	String TABLE = "WARN_NEWS";
	String JDBC_URL= "jdbc:mysql://172.16.29.112:3306/fandata?useSSL=FALSE&useUnicode=TRUE&characterEncoding=UTF-8&serverTimezone=UTC";
	//jdbc:mysql://localhost:3306/fandata?useSSL=false&serverTimezone=UTC
	String DRIVER="com.mysql.cj.jdbc.Driver";
	String USER="hive";
	String PASSWORD="123456";

	
    String TOPIC = "lhTOPIC-201607041400";
}
