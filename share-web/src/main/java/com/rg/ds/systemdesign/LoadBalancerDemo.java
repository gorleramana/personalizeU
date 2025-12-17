/**
 * 
 */
package com.rg.practice.systemdesign;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.*;

/**
 * @author gorle
 */
public class LoadBalancerDemo {

	/**
	 * @param args
	 */
		public static void main(String[] args) throws InterruptedException {
	        LoadBalancer lb = new LoadBalancer();

	        Server s1 = new Server("Server-1");
	        Server s2 = new Server("Server-2");
	        Server s3 = new Server("Server-3");

	        lb.addServer(s1);
	        lb.addServer(s2);
	        lb.addServer(s3);

	        // Send requests in a loop
	        for (int i = 1; i <= 20; i++) {
	            lb.distributeRequest("Request-" + i);
	            Thread.sleep(500); // slow down requests for demo
	        }

	        // Simulate server manually marked down
	        System.out.println("\n*** Manually shutting down Server-2 ***");
	        s2.setAvailable(false);

	        for (int i = 21; i <= 30; i++) {
	            lb.distributeRequest("Request-" + i);
	            Thread.sleep(500);
	        }

	        // Let circuit breaker auto-reset after timeout
	        System.out.println("\n--- Waiting for circuit breaker reset period ---");
	        Thread.sleep(12_000);

	        for (int i = 31; i <= 35; i++) {
	            lb.distributeRequest("Request-" + i);
	            Thread.sleep(500);
	        }
	        lb.shutdown();
	    }
}

class LoadBalancer {
    private final List<Server> servers = new CopyOnWriteArrayList<>();
    private final AtomicInteger nextServerIndex = new AtomicInteger(0);
    private final ScheduledExecutorService healthCheckScheduler = Executors.newSingleThreadScheduledExecutor();

    public LoadBalancer() {
        startHealthChecks();
    }

    public void addServer(Server server) {
        servers.add(server);
        System.out.println("Added server: " + server.getName());
    }

    public void removeServer(Server server) {
        servers.remove(server);
        System.out.println("Removed server: " + server.getName());
    }

    public Server getNextAvailableServer() {
        int attempts = 0;
        int size = servers.size();
        if (size == 0) return null;

        while (attempts < size) {
            int idx = Math.abs(nextServerIndex.getAndIncrement() % size);
            Server s = servers.get(idx);
            if (s.isAvailable()) {
                return s;
            }
            attempts++;
        }
        return null; // No available servers
    }

    public void distributeRequest(String request) {
        Server server = getNextAvailableServer();
        if (server != null) {
            boolean success = server.handleRequest(request);
            if (!success) {
                System.out.println("Request \"" + request + "\" failed on " + server.getName() + ". Retrying with another server...");
                // Optional: retry with another server
                distributeRequest(request);
            }
        } else {
            System.out.println("All servers are down. Request \"" + request + "\" could not be handled.");
        }
    }

    private void startHealthChecks() {
        healthCheckScheduler.scheduleAtFixedRate(() -> {
            System.out.println("\n--- Running Health Checks ---");
            for (Server server : servers) {
                boolean healthy = server.healthCheck();
                if (!healthy) {
                    System.out.println("Health check failed for " + server.getName());
                } else {
                    System.out.println(server.getName() + " is healthy.");
                }
            }
            System.out.println("--- Health Checks Complete ---\n");
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void shutdown() {
        healthCheckScheduler.shutdown();
    }
}

class Server {
    private final String name;
    private volatile boolean isAvailable = true;
    private AtomicInteger failureCount = new AtomicInteger(0);
    private final int failureThreshold = 3;
    private final long circuitBreakerTimeoutMillis = 10_000; // 10 seconds
    private volatile long lastFailureTime = 0;

    public Server(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public boolean isAvailable() {
        // If circuit breaker is open, check if timeout elapsed to reset
        if (!isAvailable && System.currentTimeMillis() - lastFailureTime > circuitBreakerTimeoutMillis) {
            resetCircuitBreaker();
            return true;
        }
        return isAvailable;
    }

    private void resetCircuitBreaker() {
        isAvailable = true;
        failureCount.set(0);
        System.out.println(name + ": Circuit breaker reset. Server marked available.");
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
        if (!available) {
            lastFailureTime = System.currentTimeMillis();
        }
    }

    // Simulate handling a request, with random failure possibility
    public boolean handleRequest(String request) {
        if (!isAvailable) {
            System.out.println("[" + name + "] Server is down or circuit breaker open.");
            return false;
        }

        // Simulate random failure (for demo)
        if (Math.random() < 0.2) { // 20% failure chance
            System.out.println("[" + name + "] Failed to handle request: " + request);
            onFailure();
            return false;
        }

        System.out.println("[" + name + "] Successfully handled request: " + request);
        onSuccess();
        return true;
    }

    private void onFailure() {
        int failures = failureCount.incrementAndGet();
        if (failures >= failureThreshold) {
            isAvailable = false;
            lastFailureTime = System.currentTimeMillis();
            System.out.println("[" + name + "] Circuit breaker triggered! Server marked as down.");
        }
    }

    private void onSuccess() {
        failureCount.set(0); // reset failures on success
    }

    // Health check method (could be a ping in real life)
    public boolean healthCheck() {
        // For simulation, just return current availability
        return isAvailable;
    }
}