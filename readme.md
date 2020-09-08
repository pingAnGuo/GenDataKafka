这个是我大三的Storm的大作业,webapp是javaweb代码<br>
===
  实验描述
  ---
  
    利用风场实时采集的风电机组运行数据，对数据进行实时清洗，保证数据的质量。
    同时处理后的数据划分为 两类（正常数据、不合理数据），分别存储到HBase中，作为历史数据日后分析。
    通过时间窗口对正常数据进行指 标监控，指标数值超出特定界限时，给出报警信息，并存储到MySQL，最后推送到Web端实时展示报警信息。
  实验原理
  ----
  
    编写代码GenDataKafka模拟实时产生的风机数据。
    之后使用storm将该数据清洗一下，挑出异常数据放到hbase的两个表中，并且对正常数据监控异常指标温度，当30秒内温度大于80度出现5次以上则将风机号，报警时间、报警次数存入数据库和展示到web界面。
    最后使用echarts画一下当前时间前一小时每十分钟的报警数量。
* Storm部分和web部分分开(在webapp分支)，二者相连的地方就是mysql数据库。Storm往进写，web往出读并且进行展示。Storm部分就是编写拓扑和相应的Bolt、Spout。Web则是注意前后端之间数据的传输和交互。实时展示报警信息使用websocket主动推送。Echarts折线图使用ajax传输数据，使用echarts.js设置图表即可。<br>

  实验环境
  ----
  
* IDEA  2019.2.4（ULTIMATE）<br>
* JDK 1.8<br>
* Mysql 1.8<br>
* Tomcat7<br>
* Spring 4.1.1.RELEASE<br>
* Jackson 2.3.3<br>
* Hbase 1.2.2<br>
* Kafka 2.1.1<br>
* Storm 1.1.1<br>
* Windos10<br>
