package com.cloud.experience.jdk7gc;

import com.cloud.experience.jdk7gc.utils.CSExecutorServiceUtil;
import com.cloud.experience.jdk7gc.utils.CSRestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Jdk7GcApplicationTests {

	@Autowired
	private RestTemplate restTemplate ;

	@Test
	public void contextLoads() {

	}

	@Test
	public void simpleTest(){
		CSRestTemplateUtils.get(restTemplate,"http://52.80.157.151:8080/jdk7gcperformance/dobusiness",null, Map.class);
	}

	@Test
	public void jdk8GcRequest() throws Exception {
		/**
		 * 开启50个线程，并发对测试接口http://52.80.157.151:8080/jdk7gcperformance/dobusiness发请求100次，记录响应时间到List当中，
		 * 最终统计最大响应时间，最小响应时间，平均响应时间
		 */
		int threadNumber=5;
		AtomicLong count = new AtomicLong(0);
		CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
		CopyOnWriteArrayList<HttpRequestResult> allQuestResult = new CopyOnWriteArrayList<>();

		//1: 创建一个线程池，用于发起Http请求
		//ThreadFactory threadFactory = new ThreadPoolExecutorFactoryBean()
		ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
		for(int i=0; i<threadNumber; i++) {
			executorService.execute(() -> {
				for (int i1 = 0; i1 < 4000; i1++) {
					try {
						long startTime=System.currentTimeMillis();
						CSRestTemplateUtils.get(restTemplate, "http://52.80.157.151:8080/jdk7gcperformance/dobusiness", null, Map.class);
						count.incrementAndGet();
						long endTime=System.currentTimeMillis();
						allQuestResult.add(new HttpRequestResult(startTime,endTime-startTime));
					} catch (Exception e) {
						log.error(Thread.currentThread().getName()+" CSRestTemplateUtils.get Error , count="+count+". "+e.getMessage());
						//e.printStackTrace();
					}
				}
				countDownLatch.countDown();
			});
		}
		//2:等待线程结束
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			log.info("countDownLatch.await() InterruptedException");
		}
		log.info("count="+count.get());
		CSExecutorServiceUtil.shutdownAndAwaitTermination(executorService);
		log.info("app over="+count.get());

		//3:获得总接口调用次数，最大耗时、最小耗时，平均耗时, 300MS大于10次的数量
		long requestCount=0, requestMaxTime=0, requestMinTime=0, requestAverageTime=0, requestTotalUsedTime=0 ;
		long timeOutStartTime=0, timeOutTimes=0;
		for(HttpRequestResult httpRequestResult : allQuestResult){
			requestCount++;
			requestTotalUsedTime=requestTotalUsedTime+httpRequestResult.getUsedTimeMs();
			if(null!=httpRequestResult && requestMaxTime<httpRequestResult.getUsedTimeMs()){
				requestMaxTime = httpRequestResult.getUsedTimeMs();
			}
			if(null!=httpRequestResult && requestMinTime>httpRequestResult.getUsedTimeMs()){
				requestMinTime = httpRequestResult.getUsedTimeMs();
			}
			//log.info("INSERT INTO cs_request_result(start_time, usedTimeMs) VALUES ("+httpRequestResult.getStartTimeMilles()+", "+httpRequestResult.getUsedTimeMs()+");");
			//审核一分钟，300MS大于10次的数量
			if(timeOutStartTime==0){
				timeOutStartTime = httpRequestResult.getStartTimeMilles();
			}
			if(null!=httpRequestResult && 300<httpRequestResult.getUsedTimeMs()){
				//log.info("接口响应时间大于100MS:"+httpRequestResult);
				timeOutTimes++;
			}
			if(null!=httpRequestResult && (httpRequestResult.getStartTimeMilles()-timeOutStartTime)>60000){
				if(timeOutTimes>10){
					log.error("出现一次1分钟内接口响应时间大于300MS：开始时间="+timeOutStartTime+", 次数="+timeOutTimes);
					timeOutTimes=0;
				}
				timeOutStartTime=0;
			}
		}
		log.info("10个并发各请求2000次，最大接口响应时间="+requestMaxTime);
		log.info("10个并发各请求2000次，最小接口响应时间="+requestMinTime);
		log.info("10个并发各请求2000次，平均接口响应时间="+requestTotalUsedTime/requestCount);






	}

	class HttpRequestResult{
		/**  Http请求时间戳 */
		private long startTimeMilles ;
		/** Http请求耗时 */
		private long  usedTimeMs ;

		public HttpRequestResult(long startTimeMilles, long usedTimeMs) {
			this.startTimeMilles = startTimeMilles;
			this.usedTimeMs = usedTimeMs;
		}

		public long getStartTimeMilles() {
			return startTimeMilles;
		}

		public long getUsedTimeMs() {
			return usedTimeMs;
		}

		@Override
		public String toString() {
			return "HttpRequestResult{" +
					"startTimeMilles=" + startTimeMilles +
					", usedTimeMs=" + usedTimeMs +
					'}';
		}
	}

}
