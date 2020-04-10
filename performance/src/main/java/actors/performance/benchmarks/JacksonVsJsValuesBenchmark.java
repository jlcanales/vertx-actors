package actors.performance.benchmarks;

import actors.performance.Functions;
import actors.performance.Module;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import jsonvalues.JsObj;
import jsonvalues.MalformedJson;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import static actors.performance.Module.*;
import java.util.Random;
import java.util.function.Supplier;

import static actors.performance.Functions.await20segForEnding;

public class JacksonVsJsValuesBenchmark
{
  private final static Supplier<JsObj> objSupplier = Functions.generator.apply(new Random());

  private final static Supplier<String> strGen = () -> objSupplier.get()
                                                                  .toString();

  static
  {
      await20segForEnding(Vertx.vertx().deployVerticle(new Module()));
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void parsing_with_JsValues(Blackhole blackhole) throws MalformedJson
  {
   blackhole.consume(JsObj.parse(strGen.get()));
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void parsing_with_Jackson(Blackhole blackhole) throws MalformedJson
  {
    blackhole.consume(new JsonObject(strGen.get()));
  }


  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_2_steps_JsValues()
  {
    await20segForEnding(parser.apply(strGen.get())
                              .flatMap(id)
                              .flatMap(id));
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_3_steps_JsValues() throws InterruptedException
  {

    await20segForEnding(parser.apply(strGen.get())
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                       );

  }
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_4_steps_JsValues()
  {
    await20segForEnding(parser.apply(strGen.get())
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                       );
  }
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_5_steps_JsValues()
  {
    await20segForEnding(parser.apply(strGen.get())
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                       );
  }
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_6_steps_JsValues()
  {
    await20segForEnding(parser.apply(strGen.get())
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                       );
  }


  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_10_steps_JsValues()
  {
    await20segForEnding(parser.apply(strGen.get())
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                              .flatMap(id)
                       );
  }


  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_2_steps_Jackson()
  {
    await20segForEnding(jacksonParser.apply(strGen.get())
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId));
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_3_steps_Jackson() throws InterruptedException
  {

    await20segForEnding(jacksonParser.apply(strGen.get())
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                       );

  }
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_4_steps_Jackson()
  {
    await20segForEnding(jacksonParser.apply(strGen.get())
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                       );
  }
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_5_steps_Jackson()
  {
    await20segForEnding(jacksonParser.apply(strGen.get())
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                       );
  }
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_6_steps_Jackson()
  {
    await20segForEnding(jacksonParser.apply(strGen.get())
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                       );
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void test_10_steps_Jackson()
  {
    await20segForEnding(jacksonParser.apply(strGen.get())
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                                     .flatMap(jacksonId)
                       );
  }
}
