package com.test.thread;

public class Demo {

	public static void main(String[] args) {
		// MyThread my=new MyThread("窗口一");
		// my.start();
		//
		// MyThread my2=new MyThread("窗口二");
		// my2.start();
		//
		// MyThread my3=new MyThread("窗口三");
		// my3.start();

		MyThread2 my = new MyThread2();
		Thread t = new Thread(my, "窗口一");
		t.start();

		Thread t2 = new Thread(my, "窗口二");
		t2.start();

		Thread t3 = new Thread(my, "窗口三");
		t3.start();

	}

}

class MyThread extends Thread {
	private Integer tickets = 10;

	public MyThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			if (tickets > 0) {
				System.out.println(Thread.currentThread().getName() + "卖第" + (tickets--) + "张票");
			}
		}
	}
}

class MyThread2 implements Runnable {

	private Integer tickets = 10;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			if (this.tickets > 0) {
				//加上休眠就容易出现共享资源的线程安全问题
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "卖第" + (tickets--) + "张票");
			}	
		}
	}
}
