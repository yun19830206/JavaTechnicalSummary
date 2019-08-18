package com.cloud.experience.jdkknowledge.threadpool;

import com.cloud.experience.jdkknowledge.pojo.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 模拟一个接口，可以创建N个线程的线程池，然后线程做死循环CPU占用操作，模拟服务器CPU打满场景
 * @author ChengYun
 * @date 2019/7/24  Vesion 1.0
 */
@RestController
@RequestMapping("/ThreadPoolOccupyCpuController/")
@Slf4j
public class ThreadPoolOccupyCpuController {

    private ExecutorService executorService;

    /**
     * 创建N个线程的线程池，死循环占用CPU
     * @param cpuNumber
     * @return
     */
    @RequestMapping("/createPoolOccupyCpu")
    public AjaxResponse createPoolOccupyCpu(@RequestParam(value = "cpuNumber",required = false,defaultValue = "4") int cpuNumber,
                                            @RequestParam(value = "occupySecond",required = false,defaultValue = "2") int occupySecond){
        if(cpuNumber == 0 || cpuNumber < 1 || cpuNumber > 32){
            cpuNumber = 4 ;
        }
        if(occupySecond == 0 || occupySecond < 1 || occupySecond > 10){
            cpuNumber = 2 ;
        }
        this.createExecutorService(cpuNumber,occupySecond);
        return AjaxResponse.success(null,"创建N个线程的线程池，死循环占用CPU成功。");
    }

    /** 创建N个线程的线程池，死循环占用CPU */
    private void createExecutorService(int cpuNumber,int occupySecond) {
        executorService = Executors.newFixedThreadPool(cpuNumber);
        for(int i=0; i<cpuNumber;i++){
            executorService.execute(() -> {
                long result ;
                long startTimeMiles = System.currentTimeMillis();
                long occupyTimeMiles = occupySecond*60*1000 ;
                while ((System.currentTimeMillis() - startTimeMiles) < occupyTimeMiles){
                    result = 235*199 ;
                }
            });
        }
    }

    /** 关闭当前占用CPU的死循环的线程池 */
    @RequestMapping("/stopOccupyCpuPool")
    public AjaxResponse stopOccupyCpuPool(){
        if(executorService != null){
            this.stopExecutorService(executorService);
        }
        return AjaxResponse.success(null,"关闭当前占用CPU的死循环的线程池成功。");
    }

    /** 关闭线程池 */
    private void stopExecutorService(ExecutorService executorService) {

        executorService.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.error("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executorService.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
