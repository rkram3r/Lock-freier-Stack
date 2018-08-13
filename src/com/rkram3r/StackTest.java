package com.rkram3r;

import java.util.ArrayList;
import java.util.List;

public class StackTest {
	private static final int NOF_THREADS = 100;
	private static final int NOF_STEPS = 100000;
	
	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		LockFreeStack<Integer> stack = new LockFreeStack<>();
		List<Thread> allThreads = new ArrayList<>();
		for (int threadCount = 0; threadCount < NOF_THREADS; threadCount++) {
			Thread thread = new Thread(() -> {
					for (int step = 0; step < NOF_STEPS; step++) {
						stack.push(step);
						if (stack.pop() == null) {
							throw new AssertionError("Race condition");
						}
					}
			});
			allThreads.add(thread);
			thread.start();
		}
		for (Thread thread : allThreads) {
			thread.join();
		}
		if (stack.pop() != null) {
			throw new AssertionError("Race condition");
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Duration: "  + (endTime - startTime) + "ms");
	}
}
