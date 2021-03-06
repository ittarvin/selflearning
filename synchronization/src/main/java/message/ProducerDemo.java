package message;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 	申请空缓冲区
 * 	申请公共缓冲区池的互斥访问权限
 * 	将消息放入 in 指针指向的缓冲区
 * 	in 指针指向下一个空缓冲区
 * 	释放对公共缓冲区的互斥访问权
 * 	释放消息资源
 *
 */
public class ProducerDemo {

    /**
     * 缓冲区地址
     */
    private static AtomicInteger in  = new AtomicInteger(0);

    /**
     * 生产消息：
     * @param msg
     */
    public static void produce(String msg){

        //申请空缓冲区
        MsgQueue.waitProducer(msg);

        //申请公共缓冲区池的互斥访问权限
        MsgQueue.waitMsgQueue(msg);

        //将消息放入 in 指针指向的缓冲区
        int n = MsgQueue.addContent(in.get(),msg);

        //System.out.println(msg + ",当前队列大小 n = "+n);

        in.addAndGet(1);

        //in 指针指向下一个空缓冲区
        int next = in.get() % MsgQueue.capacity;

        //System.out.println(msg + ",下一个空缓冲区 next = "+next);

        in.set(next);

        //释放对公共缓冲区的互斥访问权
        MsgQueue.signalMsgQueue(msg);

        //释放消息资源
        MsgQueue.signalProducer(msg);

    }




}
