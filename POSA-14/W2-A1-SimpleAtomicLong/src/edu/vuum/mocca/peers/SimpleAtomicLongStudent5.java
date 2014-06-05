package edu.vuum.mocca.peers;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @class SimpleAtomicLong
 *
 * @brief This class implements a subset of the
 *        java.util.concurrent.atomic.AtomicLong class using a
 *        ReentrantReadWriteLock to illustrate how they work.
 */
class SimpleAtomicLongStudent5
{
    /**
     * The value that's manipulated atomically via the methods.
     */
    private long mValue;
    
    /**
     * The ReentrantReadWriteLock used to serialize access to mValue.
     */

    private final ReentrantReadWriteLock mRWLock;

    /**
     * Creates a new SimpleAtomicLong with the given initial value.
     */
    public SimpleAtomicLongStudent5(long initialValue)
    {
        mValue = initialValue;
        mRWLock = new ReentrantReadWriteLock();
    }

    /**
     * @brief Gets the current value.
     * 
     * @returns The current value
     */
    public long get()
    {
        mRWLock.readLock().lock();
        try {
            return mValue;
        }finally {
            mRWLock.readLock().unlock();
        }
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the updated value
     */
    public long decrementAndGet()
    {
        mRWLock.writeLock().lock();
        try {
            mValue--;
        }finally {
            mRWLock.writeLock().unlock();
        }
        return get();
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the previous value
     */
    public long getAndIncrement()
    {
        try{
            return get();
        }finally {
            mRWLock.writeLock().lock();
            mValue++;
            mRWLock.writeLock().unlock();
        }
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the previous value
     */
    public long getAndDecrement()
    {
        try{
            return get();
        }finally {
            mRWLock.writeLock().lock();
            mValue--;
            mRWLock.writeLock().unlock();
        }
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the updated value
     */
    public long incrementAndGet()
    {
        mRWLock.writeLock().lock();
        try {
            mValue++;
        }finally {
            mRWLock.writeLock().unlock();
        }
        return get();
    }
}

