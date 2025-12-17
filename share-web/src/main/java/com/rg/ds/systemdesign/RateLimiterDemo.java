/**
 * 
 */
package com.rg.practice.systemdesign;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 */
public class RateLimiterDemo {
	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		// Allow 10 requests per second
		RateLimiter limiter = new RateLimiter(10, 10, 1000);

		for (int i = 0; i < 12; i++) {
		    if (limiter.allowRequest()) {
		        System.out.println("Request " + i + ": allowed");
		    } else {
		        System.out.println("Request " + i + ": blocked");
		    }
		}
	}
	
}
class RateLimiter{
	private final long capacity;            // Maximum number of tokens in the bucket
    private final long refillTokens;        // Number of tokens to add per refill interval
    private final long refillIntervalNanos; // How often (in nanoseconds) to add tokens

    private AtomicLong availableTokens;
    private volatile long lastRefillTimestamp;

    public RateLimiter(long capacity, long refillTokens, long refillIntervalMillis) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillIntervalNanos = refillIntervalMillis * 1_000_000;
        this.availableTokens = new AtomicLong(capacity);
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized boolean allowRequest() {
        refill();
        if (availableTokens.get() > 0) {
            availableTokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsed = now - lastRefillTimestamp;

        if (elapsed > refillIntervalNanos) {
            long refillCount = elapsed / refillIntervalNanos;
            long newTokens = Math.min(capacity, availableTokens.get() + refillCount * refillTokens);
            availableTokens.set(newTokens);
            lastRefillTimestamp += refillCount * refillIntervalNanos;
        }
    }
}
 