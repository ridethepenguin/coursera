package edu.vuum.mocca.peers;

// Import the necessary Java synchronization and scheduling classes.

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/**
 * @class SimpleAtomicLong
 *
 * @brief This class implements a subset of the
 *        java.util.concurrent.atomic.SimpleAtomicLong class using a
 *        ReentrantReadWriteLock to illustrate how they work.
 */
class SimpleAtomicLongStudent4
{
    /**
     * The value that's manipulated atomically via the methods.
     */
    private long mValue;
    
    /**
     * The ReentrantReadWriteLock used to serialize access to mValue.
     */
    // TODO - replace the null with the appropriate initialization:
    private ReentrantReadWriteLock mRWLock = new ReentrantReadWriteLock();

    /**
     * Creates a new SimpleAtomicLong with the given initial value.
     */
    public SimpleAtomicLongStudent4(long initialValue)
    {
        // TODO - you fill in here
    	mRWLock.writeLock().lock();
    	try {
    		mValue = initialValue;
    	} finally {
    		mRWLock.writeLock().unlock();
    	}
    	
    }

    /**
     * @brief Gets the current value.
     * 
     * @returns The current value
     */
    public long get()
    {
        long value;
        // TODO - you fill in here, using a readLock()
        mRWLock.readLock().lock();
        try {
        	value = mValue;
        } finally {
        	mRWLock.readLock().unlock();
        }
        
        return value;
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the updated value
     */
    public long decrementAndGet()
    {
        long value;
        // TODO - you fill in here, using a writeLock()
        mRWLock.writeLock().lock();
        try {
        	mValue = mValue--;
        } finally {
        	mRWLock.writeLock().unlock();
        }
        value = this.get();
        return value;
    }


	/**
     * @brief Atomically increments by one the current value
     *
     * @returns the previous value
     */
    public long getAndIncrement()
    {
        long value;
        // TODO - you fill in here, using a writeLock()
        value = this.get();
        mRWLock.writeLock().lock();
        try {
        	value++;
        } finally {
        	mRWLock.writeLock().unlock();
        }
        return value;
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the previous value
     */
    public long getAndDecrement()
    {
        long value;
        // TODO - you fill in here, using a writeLock()
        value = this.get();
        mRWLock.writeLock().lock();
        try {
        	value--;
        } finally {
        	mRWLock.writeLock().unlock();
        }
        return value;
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the updated value
     */
    public long incrementAndGet()
    {
        long value;
        // TODO - you fill in here, using a writeLock()
        mRWLock.writeLock().lock();
        try {
        	mValue = mValue++;
        } finally {
        	mRWLock.writeLock().unlock();
        }
        value = this.get();
        return value;
    }
}

