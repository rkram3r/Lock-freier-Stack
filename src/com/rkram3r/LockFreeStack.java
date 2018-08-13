package com.rkram3r;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> {
	private AtomicReference<Node<T>> top = new AtomicReference<>();

	public void push(T value) {
		top.updateAndGet(top -> new Node<>(value, top));
	}

	public T pop() {
		Node<T> n = top.getAndUpdate(node -> node == null ? null : node.getNext());
		return n == null ? null : n.getValue();
	}
}
