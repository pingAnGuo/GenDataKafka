package com.horizon.dataCleaning;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NormalHbaseBolt implements IRichBolt {
    private Connection conn=null;
    private Table normaltable=null;
    private Table unusualtable=null;
    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        Configuration conf= HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","172.16.29.112");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        try {
            conn= ConnectionFactory.createConnection(conf);
            normaltable=conn.getTable(TableName.valueOf("lhnormal"));
            unusualtable = conn.getTable(TableName.valueOf("lhunusual"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Tuple tuple) {
        String rowkey = tuple.getStringByField("rowkey");
        Double temp = tuple.getDoubleByField("temp");
        String fan_no= tuple.getStringByField("fan_no");
        Boolean tag = tuple.getBooleanByField("tag");
        try {
            if(tag){
                System.out.println("normal------------------------------------"+rowkey);
                insert(normaltable,rowkey,"result","value",tuple.getStringByField("cleandata"));
                this.collector.emit(new Values(fan_no,temp));
            }else{
//                System.out.println("unusual-----------------------------------"+rowkey);
                insert(unusualtable,rowkey,"result","value",tuple.getStringByField("cleandata"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void insert(Table table,String rowkey,String colFamily,String column,String value) throws IOException {
        Put put = new Put(rowkey.getBytes());
        put.addColumn(colFamily.getBytes(), column.getBytes(), value.getBytes());
        table.put(put);
    }
    @Override
    public void cleanup() {
        try {
            normaltable.close();
            unusualtable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("fan_no","temp"));
        System.out.println("---------\"fan_no\",\"temp\"");
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
