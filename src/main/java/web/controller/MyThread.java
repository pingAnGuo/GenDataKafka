package web.controller;



import web.Util.DBUtil;
import web.vo.Alarm;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 用于发送新数据到页面
 */
public class MyThread extends Thread {

    private Session session;
    private List<Alarm> currentMessage;
    private DBUtil dbUtil;
    private int currentIndex;

    public MyThread(Session session)  {
        this.session = session;
        dbUtil = new DBUtil();
        currentIndex = 0;//此时是0条消息,记录当前数据量，当有新数据时，发送新数据
    }
    /*
    run开启无限循环
     */
    @Override
    public void run() {
        while (true) {
            List<Alarm> list = null;
            try {
                list = dbUtil.getFromDB();
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            if (list != null && currentIndex < list.size()) {

                for (int i = currentIndex; i < list.size(); i++) {
                    try {
                        session.getBasicRemote().sendText(list.get(i).getFanNo()
                                + "," + list.get(i).getWarnTime()+ "," + "发动机温度高于 "+ "40 度 " +list.get(i).getWarnCount()+" 次");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                currentIndex = list.size();
            }
            try {
                //5秒刷新一次
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
