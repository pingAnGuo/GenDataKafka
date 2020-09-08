package com.horizon.dataCleaning;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class CleanBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String data = tuple.getString(0);
        String[] fandata = data.split(",");
        String fandate = fandata[2];//数据日期
        Boolean tag = true;//是否异常的标签

        String fan_no= fandata[1];//风机编号
        String speed = fandata[4];//风速
        String active_power = fandata[21];//功率
        Double temp = Double.parseDouble(fandata[13]);//温度
        Double speedd = Double.parseDouble(speed);
        Double active_powerd = Double.parseDouble(active_power);
        //获得运行数据日期不是当日数,且风速和功率不为空
        if(!(fandate.contains("2016/1/1")||fandate.contains("2016-01-01"))||speed==null||active_power==null){
           tag = false;
        }
        else if(speedd==-902||speedd>12&&speedd<3) {//获得运行数据风速 (4) 或 -902 或 在 3～12之外
            tag = false;
        } else if(active_powerd==-902||active_powerd>2*1500||active_powerd<-0.5*1500){  //获得运行数据功率 (21)为 -902 或 在 -0.5*1500~2*1500之外
            tag = false;
        }
        String rowkey = fandate+"_"+fandata[1];
        this.collector.emit(new Values(data,rowkey,fan_no,temp,tag));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
//        outputFieldsDeclarer.declare(new Fields("cleandata","rowkey","fan_no","temp","tag"));
        outputFieldsDeclarer.declare(new Fields("cleandata","rowkey","fan_no","temp","tag"));

    }
}
