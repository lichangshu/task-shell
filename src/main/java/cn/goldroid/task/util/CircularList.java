/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author changshu.li
 * @param <T>
 */
public class CircularList<T> {

	protected final List<T> list;
	protected final int size;
	protected boolean close = false;

	private long counter = 0;

	public CircularList() {
		this(100, new ArrayList<T>());
	}

	public CircularList(int size, List<T> list) {
		this.list = list;
		if (size <= 0) {
			throw new IllegalArgumentException("Must i > 0");
		}
		this.size = size;
		init();
	}

	private CircularList<T> init() {
		list.clear();
		for (int i = 0; i < size; i++) {
			list.add(null);
		}
		return this;
	}

	public synchronized int size() {
		return list.size();
	}

	public synchronized boolean isEmpty() {
		return list.isEmpty();
	}

	public synchronized CircularList<T> push(T e) {
		if (close) {
			throw new IllegalArgumentException("Is colse !");
		}
		list.set(getPosition(), e);
		counter++;
		return this;
	}

	public synchronized List<CircularListNode> getFrom(long index) {
		long from = index;
		if (index > counter) {
			//if to bigger is max
			from = counter;
		} else if (index < counter - size) {
			//if simaller is min position
			from = counter - size;
		}
		long c1 = counter / size;
		long f1 = from / size;
		int cidx = (int) (counter % size);
		int fidx = (int) (from % size);
		List<T> prelist = Collections.EMPTY_LIST;
		List<T> suflist = Collections.EMPTY_LIST;
		List<CircularListNode> result = new ArrayList();
		if (c1 == f1) {//同一页
			prelist = list.subList(fidx, cidx);
		} else if (c1 == f1 + 1) {
			prelist = list.subList(fidx, size);
			suflist = list.subList(0, cidx);
		} else {
			//not run !!
			throw new IllegalArgumentException();
		}
		int psi = prelist.size();
		for (int i = 0; i < psi; i++) {
			T t = prelist.get(i);
			result.add(this.new CircularListNode(t, from + i));
		}
		for (int i = 0; i < suflist.size(); i++) {
			T t = suflist.get(i);
			result.add(this.new CircularListNode(t, from + i + psi));
		}
		return result;
	}

	public synchronized int getPosition() {
		return (int) (counter % size);
	}

	public synchronized boolean isClose() {
		return close;
	}

	public synchronized CircularList<T> closed() {
		this.close = true;
		return this;
	}

	public class CircularListNode {

		private T node;
		private long position;

		public CircularListNode(T node, long position) {
			this.node = node;
			this.position = position;
		}

		public T getNode() {
			return node;
		}

		public long getPosition() {
			return position;
		}

	}
}
