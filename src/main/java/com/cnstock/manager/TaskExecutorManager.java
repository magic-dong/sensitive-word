package com.cnstock.manager;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.cnstock.config.MyThreadPoolTaskExecutor;

/**
 * 多任务线程执行类
 * @author lzd
 * @date 2019年6月3日
 * @version
 */
@Component
public class TaskExecutorManager{
	
	private static final Logger logger = LoggerFactory.getLogger(TaskExecutorManager.class);
	
	private static  MyThreadPoolTaskExecutor myExecutor;
	
	/**
	 * 分页多线程执行任务
	 * @author lzd
	 * @date 2019年6月3日:上午10:49:18
	 * @param param 参数
	 * @param threadNums 线程数
	 * @param size 一个任务执行的数据总数
	 * @param clazz 线程类对象
	 * @param async 是否异步 true:是，false:否
	 * @return
	 * @description
	 */
	public static boolean execute(Map<String, Object> params,Integer threadNums,Integer size,Class<?> clazz,boolean async){
		
		try {
			Assert.notNull(clazz, "线程类对象clazz不能为null！");
			//任务集合
			List<Future<?>> taskList=new ArrayList<>(threadNums);
			//获取构造函数
			Constructor<?> constructor = clazz.getConstructor(Map.class,Integer.class,Integer.class);
			//切分任务
			for (int i = 1; i <=threadNums; i++) {
				BaseThread<?> thread=(BaseThread<?>) constructor.newInstance(params,i,size);
				taskList.add(myExecutor.submit(thread));
			}
			if(!async){
				//遍历任务结果
				for (int i = 0; i < taskList.size(); i++) {
					Future<?> future=taskList.get(i);
					//执行future.get()方法就会阻塞，等待获取处理结果
					Object result = future.get();
					logger.info("第{}任务执行已完成,获取执行结果[{}]",(i+1),result);
				}
			}
			//return "任务执行操作成功！";
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			//return "任务执行操作失败！";
			return false;
		}
		
	}

	/**
	 * 单线程执行任务
	 * @author lzd
	 * @date 2019年8月28日:上午9:39:40
	 * @param params 参数
	 * @param clazz 线程类对象
	 * @param async 是否异步 true:是，false:否
	 * @return
	 * @description
	 */
	public static boolean execute(Map<String, Object> params,Class<?> clazz,boolean async){
		try {
			Assert.notNull(clazz, "线程类对象clazz不能为null！");
			//获取构造函数
			Constructor<?> constructor = clazz.getConstructor(Map.class);
			BaseThread<?> thread=(BaseThread<?>)constructor.newInstance(params);
			Future<?> future=myExecutor.submit(thread);
			if(!async){
				//执行future.get()方法就会阻塞，等待获取处理结果
				Object result = future.get();
				logger.info("任务执行已完成,获取执行结果[{}]",result);
			}
			//return "任务执行操作成功！";
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			//return "任务执行操作失败！";
			return false;
		}
		
	}
	
	/**
	 * 单线程执行任务
	 * @author lzd
	 * @date 2019年8月28日:上午9:39:40
	 * @param thread 线程对象
	 * @param async 是否异步 true:是，false:否
	 * @return
	 * @description
	 */
	public static boolean execute(BaseThread<?> thread,boolean async){
		try {
			Assert.notNull(thread, "线程类对象thread不能为null！");
			Future<?> future=myExecutor.submit(thread);
			if(!async){
				//执行future.get()方法就会阻塞，等待获取处理结果
				Object result = future.get();
				logger.info("任务执行已完成,获取执行结果[{}]",result);
			}
			//return "任务执行操作成功！";
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			//return "任务执行操作失败！";
			return false;
		}
		
	}
	
	/**
	 * 关闭连接池
	 * @author lzd
	 * @date 2019年6月3日:下午3:34:21
	 * @description
	 */
	public static void shutdown(){
		if(myExecutor !=null){
			myExecutor.shutdown();
		}
	}
	
	@Autowired
	public void setMyExecutor(MyThreadPoolTaskExecutor myExecutor) {
		TaskExecutorManager.myExecutor = myExecutor;
	}
}
