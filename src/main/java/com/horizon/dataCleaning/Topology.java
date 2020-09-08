package com.horizon.dataCleaning;

import com.horizon.GenDataKafka.IConstant;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.kafka.ZkHosts;

import java.util.concurrent.TimeUnit;

public class Topology {

    public static void main(String[] args) throws InterruptedException {
        ZkHosts zkHosts = new ZkHosts("172.16.29.112:2181");
        //定义SpoutConfig,读数据
        SpoutConfig fandata = new SpoutConfig(zkHosts, IConstant.TOPIC,"","");
        fandata.scheme = new SchemeAsMultiScheme(new StringScheme());

        //构建topology
        TopologyBuilder builder=new TopologyBuilder();
        builder.setSpout(IConstant.TOPIC,new KafkaSpout(fandata));
        //清洗数据
        builder.setBolt("clean",new CleanBolt())
                .shuffleGrouping(IConstant.TOPIC);
//        builder.setBolt("print",new Print())
//                .shuffleGrouping("clean");
        //将数据传入hbase
        builder.setBolt("tohbase",new NormalHbaseBolt())
                .shuffleGrouping("clean");
      //  监控正常数据
        //  int TIME_LEN = 30;//30秒内发电机的温度
        //	int TIME_INTERVAL = 5;//每5秒监控一次，时间间隔
        builder.setBolt("monitor",new MonitorBolt()
                .withWindow(BaseWindowedBolt.Duration.seconds(IConstant.TIME_LEN), BaseWindowedBolt.Duration.seconds(IConstant.TIME_INTERVAL)), 1)
                .shuffleGrouping("tohbase");
        //输出mysql
        builder.setBolt("tomysql",new ToMysql())
                .shuffleGrouping("monitor");
        //配置信息
        Config conf = new Config();
        conf.setMessageTimeoutSecs(60);
        //conf.setNumAckers(2);//设置工作节点的个数,JVM的个数
//        conf.setDebug(true);//是不是开发模式

        //本地模式
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("fandataTopo", conf, builder.createTopology());//名字、配置、写死的topo
        System.out.println("waiting--------------------------------------------------------------------------------------");
        Thread.sleep(3000000);
        cluster.killTopology("fandataTopo");//如果不停会一致执行nexttuple
        cluster.shutdown();
    }
}
