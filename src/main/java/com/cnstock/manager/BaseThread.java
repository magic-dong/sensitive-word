package com.cnstock.manager;

import java.util.concurrent.Callable;

public abstract class BaseThread<V> implements Callable<V> {
	/**
	 * 线程call方法
	 */
	@Override
	public abstract V call();
	

}
