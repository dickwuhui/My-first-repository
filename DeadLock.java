package cn.thread;

import java.util.concurrent.locks.Lock;

/*
 * 死锁 原理   ---同步中，还有同步== 嵌套同步+
 * 		多个线程，再抢一个锁，出现谁也抢不到，结果导致程序死掉
 * 		嵌套里面嵌套同步
 * 	
 * 
 */
class Dead implements Runnable {
	boolean f;

	Dead(boolean f) {
		this.f = f;
	}

	public void run() {
		if (f) {
			while (true) {
				synchronized (LockA.loa) {
					System.out.println("if....lockA");
					synchronized (LockB.lob) {
						System.out.println("else...LockB");
					}
				}
			}
		} else {
			while (true) {
				synchronized (LockB.lob) {
					System.out.println("if....lockB");
					synchronized (LockA.loa) {
						System.out.println("else...LockA");
					}
				}
			}
		}
	}
}

class LockA {
	public static final LockA loa = new LockA();
}

class LockB {
	public static final LockB lob = new LockB();
}

public class DeadLock {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dead d1 = new Dead(true);
		Dead d2 = new Dead(false);
		
		Thread t1 = new Thread(d1);
		Thread t2 = new Thread(d2);
		t2.start();
		t1.start();
		
	}

}
