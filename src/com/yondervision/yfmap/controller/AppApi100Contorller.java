/**
 * 
 */
package com.yondervision.yfmap.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi50001Form;
import org.apache.log4j.Logger;



@Controller
public class AppApi100Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi10000.{ext}")
	public void appapi10000(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception{
		form.setBusinName("租房提取复合接口");	
		log.info(Constants.LOG_HEAD+"api/appApi10000 begin. zfqlrzjh=" + form.getQlrzjh());
        log.info("zfqlrzjh=" + form.getQlrzjh());

        /**
         * 运行多个任务并处理全部结果
         * 该范例的关键点在于调用了ExecutorService的invokeAll()方法。
         * 这个方法接收一个实现了Callable接口的任务列表，然后等待所有任务完成，并返回一个Future列表。
         * 我们创建三个任务实例，并加入到任务列表中。
         * 然后调用invokeAll方法来执行这个任务列表，并接受一个Future列表返回值，并打印返回值结果。
         */
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Task> tasks = new ArrayList<Task>();
        for (int i = 1; i < 4; i++) {
            tasks.add(new Task(String.valueOf(i),form,modelMap));
        }
		long starTime=System.currentTimeMillis();
		Map map=new HashMap();
        try {
            List<Future<Result>> futures = executor.invokeAll(tasks);
            executor.shutdown();
            System.out.printf("Main: Start print resutls\n");
            for (Future<Result> future : futures) {
                System.out.printf("%s : %s\n", future.get().getName(), future.get().getValue());
                map.put(future.get().getName(), future.get().getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		long endTime1=System.currentTimeMillis();
		long Time1=endTime1-starTime;
		System.out.print("全部完成耗时"+Time1+"毫秒");
        System.out.printf("Main: End of programe.\n");
        String result=map.toString();
		log.info("租房提取专用复合接口结果"+result);
		log.info(Constants.LOG_HEAD+"appApi00105 end.");
		response.getOutputStream().write(result.getBytes("gb18030"));
		return ;
	}

    @RequestMapping("/appapi10001.{ext}")
    public void appapi10001(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request,
                            HttpServletResponse response) throws Exception{
        form.setBusinName("房屋登记信息查询复合接口");
        log.info(Constants.LOG_HEAD+"api/appApi10001 begin.");

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Taskappapi10001> tasks = new ArrayList<Taskappapi10001>();
        for (int i = 1; i < 3; i++) {
            tasks.add(new Taskappapi10001(String.valueOf(i),form,modelMap));
        }
        long starTime=System.currentTimeMillis();
        Map map=new HashMap();
        try {
            List<Future<Result>> futures = executor.invokeAll(tasks);
            executor.shutdown();
            System.out.printf("Main: Start print resutls\n");
            for (Future<Result> future : futures) {
                System.out.printf("%s : %s\n", future.get().getName(), future.get().getValue());
                map.put(future.get().getName(), future.get().getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime1=System.currentTimeMillis();
        long Time1=endTime1-starTime;
        System.out.print("全部完成耗时"+Time1+"毫秒");
        System.out.printf("Main: End of programe.\n");
        String result=map.toString();
        log.info("房屋登记信息查询复合接口"+result);
        log.info(Constants.LOG_HEAD+"appApi00113 end.");
        response.getOutputStream().write(result.getBytes("gb18030"));
        return ;
    }

    @RequestMapping("/appapi10002.{ext}")
    public void appapi10002(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request,
                            HttpServletResponse response) throws Exception{
        form.setBusinName("民政部_婚姻登记信息核验复合接口");
        log.info(Constants.LOG_HEAD+"api/appapi10002 begin.");

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Taskappapi10002> tasks = new ArrayList<Taskappapi10002>();
        for (int i = 1; i < 3; i++) {
            tasks.add(new Taskappapi10002(String.valueOf(i),form,modelMap));
        }
        long starTime=System.currentTimeMillis();
        Map map=new HashMap();
        try {
            List<Future<Result>> futures = executor.invokeAll(tasks);
            executor.shutdown();
            System.out.printf("Main: Start print resutls\n");
            for (Future<Result> future : futures) {
                System.out.printf("%s : %s\n", future.get().getName(), future.get().getValue());
                map.put(future.get().getName(), future.get().getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime1=System.currentTimeMillis();
        long Time1=endTime1-starTime;
        System.out.print("全部完成耗时"+Time1+"毫秒");
        System.out.printf("Main: End of programe.\n");
        String result=map.toString();
        log.info("民政部_婚姻登记信息核验复合接口"+result);
        log.info(Constants.LOG_HEAD+"appapi10002 end.");
        response.getOutputStream().write(result.getBytes("gb18030"));
        return ;
    }
}
