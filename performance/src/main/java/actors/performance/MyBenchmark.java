package actors.performance;

import io.vertx.core.Vertx;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MyBenchmark
{

  static {
    try
    {
      Vertx vertx = Vertx.vertx();
      vertx.deployVerticle(new Module());
      Thread.sleep(1000);
    }
    catch (InterruptedException e)
    {
       throw new RuntimeException(e);
    }
  }

  private final static int TIMES = 100;

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @Fork(1)
  @OperationsPerInvocation(1)
  public void testCountStringOneVerticle() throws InterruptedException
  {
    var latch = new CountDownLatch(1);

    Module.countStringsOneVerticle
      .apply(TIMES)
      .onComplete(r-> latch.countDown());

    latch.await(20,
                SECONDS);

  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @Fork(1)
  @OperationsPerInvocation(1)
  public void testCountStringMultiVerticle() throws InterruptedException
  {
    var latch = new CountDownLatch(1);

    Module.countStringsMultiVerticles
      .apply(TIMES)
      .onComplete(r-> latch.countDown());

    latch.await(20,
                SECONDS);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @Fork(1)
  @OperationsPerInvocation(1)
  public void testCountStringProcesses() throws InterruptedException
  {
    var latch = new CountDownLatch(1);

    Module.countStringsMultiProcesses
      .apply(TIMES)
      .onComplete(r-> latch.countDown());

    latch.await(20,
                SECONDS);
  }

}
