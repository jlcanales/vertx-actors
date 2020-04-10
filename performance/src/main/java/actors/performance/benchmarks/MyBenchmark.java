package actors.performance.benchmarks;

import actors.performance.Module;
import io.vertx.core.Vertx;
import org.openjdk.jmh.annotations.*;

import static actors.performance.Functions.await20segForEnding;
import static actors.performance.Module.*;

public class MyBenchmark
{

  static
  {
    await20segForEnding(Vertx.vertx()
                             .deployVerticle(new Module()));
  }

  private final static int TIMES = 100;

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void testCountStringOneVerticle() throws InterruptedException
  {
    await20segForEnding(countStringsOneVerticle.apply(TIMES));
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void testCountStringMultiVerticle() throws InterruptedException
  {

    await20segForEnding(countStringsMultiVerticles.apply(TIMES));

  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void testCountStringProcesses() throws InterruptedException
  {

    await20segForEnding(countStringsMultiProcesses.apply(TIMES));


  }

}
