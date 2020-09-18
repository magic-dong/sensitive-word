package com.test.thread;

import java.util.concurrent.CountDownLatch;

public class Demo2 {

	public static void main(String[] args) {
		CountDownLatch countDownLatch=new CountDownLatch(6);
		for (int i = 0; i < 6; i++) {
			MyThread3 my=new MyThread3("thread-"+i,countDownLatch);
			my.start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"*****班长最后关门走人");
	}
}

class MyThread3 extends Thread{
	
	private CountDownLatch countDownLatch;
	
	public MyThread3(String threadName,CountDownLatch countDownLatch){
		super(threadName);
		this.countDownLatch=countDownLatch;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(Thread.currentThread().getName()+" 上完自习，离开教室");
		countDownLatch.countDown();
	}
	
}