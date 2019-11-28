package com.github.sunjx.modbus.queue;

import com.github.sunjx.modbus.common.util.ScheduledUtil;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class AutoClearConcurrentLinkedQueue<T>
        extends ConcurrentLinkedQueue<T> {
    private static final long serialVersionUID = -4124978116278119617L;
    protected DelayQueue<DelayedItem<T>> delayQueue;
    private long timeout;
    private ReadWriteLock readWriteLock;
    private Lock readLock;

    public AutoClearConcurrentLinkedQueue(long timeout) {
        /* 21 */
        this.timeout = timeout;
        /* 22 */
        this.delayQueue = new DelayQueue<>();
        /* 23 */
        this.readWriteLock = new ReentrantReadWriteLock(true);
        /* 24 */
        this.readLock = this.readWriteLock.readLock();
        /* 25 */
        Runnable runnable = () -> execute4AutoClear();
        /* 26 */
        ScheduledUtil.schedule(runnable, 10L);
    }

    private void execute4AutoClear() {
        while (true) {
            try {
                /* 32 */
                this.readLock.lock();
                /* 33 */
                DelayedItem<T> delayItem = this.delayQueue.take();
                /* 34 */
                T item = peek();
                /* 35 */
                if (delayItem.getItem().equals(item)) {
                    /* 36 */
                    super.poll();
                }
                /* 38 */
                this.readLock.unlock();
                /* 39 */
            } catch (InterruptedException e) {

                /* 41 */
                e.printStackTrace();
            }
        }
    }


    /* 47 */
    protected DelayedItem<T> getDelayedItem(T item) {
        return new DelayedItem<>(item, this.timeout * (size() + 1));
    }


    @Override
    public boolean add(T e) {
        /* 52 */
        this.delayQueue.add(getDelayedItem(e));
        /* 53 */
        return super.add(e);
    }


    @Override
    public boolean offer(T e) {
        /* 58 */
        this.delayQueue.offer(getDelayedItem(e));
        /* 59 */
        return super.offer(e);
    }


    @Override
    public T poll() {
        /* 65 */
        this.readLock.lock();
        /* 66 */
        this.delayQueue.poll();
        /* 67 */
        T item = super.poll();
        /* 68 */
        this.readLock.unlock();
        /* 69 */
        return item;
    }
}


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.queue\AutoClearConcurrentLinkedQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */