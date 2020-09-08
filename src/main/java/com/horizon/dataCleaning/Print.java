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
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Print implements IRichBolt {

    private HashMap<String, String> normaldata = null;
    private HashMap<String, String> unusualdata = null;
    private OutputCollector collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        normaldata = new HashMap<String, String>();
        unusualdata = new HashMap<String, String>();
    }

    public void execute(Tuple tuple) {
        String cleandata = tuple.getStringByField("cleandata");
        String rowkey = tuple.getStringByField("rowkey");
        System.out.println("cleandata---------------------------"+cleandata);
        if(cleandata.contains("unusual")){
            this.unusualdata.put(rowkey, cleandata.substring(8));
        }else{
            this.normaldata.put(rowkey, cleandata);
//            this.collector.emit(new Values(cleandata));

        }
    }

    public void insert(Table table,String rowkey,String colFamily,String column,String value) throws IOException {
        Put put = new Put(rowkey.getBytes());
        put.addColumn(colFamily.getBytes(), column.getBytes(), value.getBytes());
        table.put(put);
    }
    public void cleanup() {

            for(String rowkey:normaldata.keySet()){
//                insert(normaltable,rowkey,"result","value",normaldata.get(rowkey).toString());
                System.out.println("normal------------"+normaldata.get(rowkey));
            }
            for(String rowkey:unusualdata.keySet()){
//                insert(unusualtable,rowkey,"result","value",unusualdata.get(rowkey).toString());
                System.out.println("unusual------------"+unusualdata.get(rowkey));
            }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
//        outputFieldsDeclarer.declare(new Fields("normaldata"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
