package com.jdkhome.jvmc;

import com.jdkhome.jvmc.io.P2PNode;
import com.jdkhome.jvmc.io.P2PNodeHandLer;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Log
@SpringBootApplication
public class JvmcApplication implements CommandLineRunner {

    @Autowired
    P2PNode p2PNode;

    @Autowired
    P2PNodeHandLer p2PNodeHandLer;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(JvmcApplication.class, args);

        // Spring 容器启动后挂起线程
        Thread.currentThread().join();
    }


    // https://blog.csdn.net/u012377333/article/details/51555207

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        // 避免Spring容器创建完成后自动结束
        log.info("test");

        new Thread(() -> {
            try {
                p2PNode.run(7769);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //112.90.89.15
        new Thread(() -> {
            p2PNode.connect("localhost", 7769);
            p2PNode.connect("112.90.89.15", 7769);
        }).start();
//        new Thread(() -> {
//            p2PNode.connect("112.90.89.15", 7769);
//        }).start();


    }
}
