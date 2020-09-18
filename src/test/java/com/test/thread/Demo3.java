package com.test.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Demo3 {

	public static int pepoleNum=20;//就餐人数
	
	public static void main(String[] args) {
		try {
			CountDownLatch countDownLatch=new CountDownLatch(pepoleNum);
			Semaphore semaphore=new Semaphore(FoodStore.tableSize);
			
			for (int i = 0; i < pepoleNum; i++) {
				MyThread4 my=new MyThread4(countDownLatch,semaphore,i+1);
				my.start();
			}
			countDownLatch.await();
			System.out.println("客人总共："+pepoleNum+"人,全部就餐完毕！");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class FoodStore{
	public static int tableSize=5;//餐桌数
	
	public static void setTable(int tableNo){
		System.out.println("第"+tableNo+"客人入座，开始就餐》》》》》");
	}
	
	public static void releaseTable(int tableNo){
		System.out.println("第"+tableNo+"客人就餐完毕，请下一位客人开始就餐》》》》》");
	}
	
	
}

class MyThread4 extends Thread{
	
	private CountDownLatch countDownLatch;
	private Semaphore semaphore;
	private int tableNo;
	
	public MyThread4(CountDownLatch countDownLatch,Semaphore semaphore,int tableNo){
		super("thread_"+tableNo);
		this.countDownLatch=countDownLatch;
		this.semaphore=semaphore;
		this.tableNo=tableNo;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			semaphore.acquire();
			semaphore.availablePermits();
			FoodStore.setTable(tableNo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			semaphore.release();
			FoodStore.releaseTable(tableNo);
			countDownLatch.countDown();
		}
		
	}
	
}