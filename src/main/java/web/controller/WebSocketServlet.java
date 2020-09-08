package web.controller;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket")
public class WebSocketServlet {
//    //用来存放每个客户端对应的MyWebSocket对象。
//    private static CopyOnWriteArraySet<WebSocketServlet> webSocketSet = new CopyOnWriteArraySet<>();
//    //通过session可以给每个WebSocket长连接中的客户端发送数据
//    private javax.websocket.Session session = null;
//    private MyThread myThread;


    /**
     * @ClassName: onOpen
     * @Description: 开启连接的操作
     */
    @OnOpen
    public void onOpen(Session session)  {
        MyThread myThread = null;
        myThread = new MyThread(session);
        //开启一个线程对数据库中的数据进行轮询
        myThread.start();
    }

}
