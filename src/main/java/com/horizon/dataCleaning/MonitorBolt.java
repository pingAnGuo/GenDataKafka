package com.horizon.dataCleaning;

import com.horizon.GenDataKafka.IConstant;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实时监控报警
 */
public class MonitorBolt extends BaseWindowedBolt {
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context,
                        OutputCollector collector) {
        System.out.println("开始监控prepare");
        this.collector = collector;
    }

    @Override
    public void execute(TupleWindow inputWindow) {
        System.out.println("开始监控正常数据");
        Map<String, Integer> hashMap = new HashMap<>();
        //获取系统当前时间
        Date date = new Date();
        ////"yyyy-MM-dd hh:mm:ss"
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Tuple input : inputWindow.get()){
            String fan_no = input.getStringByField("fan_no");
            Double temp = input.getDoubleByField("temp");
            if(temp > 25){//超出最大温度，开始计数IConstant.MAX_TEMP
                Integer value = hashMap.get(fan_no);
                if (value == null) {
                    hashMap.put(fan_no, 1);
                } else {
                    hashMap.put(fan_no, value + 1);
                }
            }
        }
        for (Map.Entry<String, Integer> e : hashMap.entrySet()) {
            if(e.getValue() > IConstant.COUNT){
                collector.emit(new Values(e.getKey(),df.format(date),e.getValue()));
                System.out.println("输出结果： =============="+e.getKey() + ":" + e.getValue());
            }
            System.out.println("输出结果： =============="+e.getKey() + ":" + e.getValue());

        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("fan_no","warn_time","warn_count"));
    }

}
