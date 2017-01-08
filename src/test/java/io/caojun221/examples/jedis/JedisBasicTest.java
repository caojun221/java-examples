package io.caojun221.examples.jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisBasicTest {

    private static final MetricRegistry metrics = new MetricRegistry();
    private final Jedis jedis = new Jedis("localhost", 6379);

    private static final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    private static final JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379);

    static {
        jedisPoolConfig.setMaxTotal(10);
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
                                                  .convertDurationsTo(TimeUnit.MILLISECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        jedis.set("mykey", "value");
        String value = jedis.get("mykey");
    }

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    @Test
    public void bit() throws InterruptedException {

        Meter meter = metrics.meter("setbit");

        final String key = "bitkey";
        jedis.del(key);
        int size = (int) Math.pow(2, 20);
        for (int i = 0; i < size; i++) {
            final int offset = i;
            executor.execute(() -> {
                try (Jedis pooled = jedisPool.getResource()) {
                    pooled.setbit(key, offset, true);
                    meter.mark();
                }
            });
        }
        executor.awaitTermination(1, TimeUnit.DAYS);
        System.out.println(jedis.bitcount(key));
        jedisPool.destroy();
    }
}
