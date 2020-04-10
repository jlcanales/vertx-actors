package actors.performance;

import actors.performance.Module;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Random;
import java.util.function.Supplier;

import static actors.performance.Functions.await20segForEnding;
import static actors.performance.Module.*;

@ExtendWith(VertxExtension.class)
public class TestPerformance
{
  private final static Supplier<JsObj> objSupplier = Functions.generator.apply(new Random());

  private final static Supplier<String> strGen = () -> objSupplier.get()
                                                                  .toString();

  @BeforeAll
  public static void prepare(Vertx vertx,
                             VertxTestContext testContext
                            )
  {

    vertx.deployVerticle(new Module())
         .onSuccess(it -> testContext.completeNow());
  }


  @Test
  public void testCountString(final Vertx vertx,
                              final VertxTestContext testContext
                             )
  {
    Module.countStringsOneVerticle
      .apply(3)
      .onSuccess(it -> testContext.completeNow());
  }

  @Test
  public void testCountStringMultiProcesses(final Vertx vertx,
                                            final VertxTestContext testContext
                                           )
  {
    Module.countStringsMultiProcesses
      .apply(3)
      .onSuccess(it -> testContext.completeNow());
  }

  @Test
  public void testCountStringMultiVerticle(final Vertx vertx,
                                           final VertxTestContext testContext
                                          )
  {
    Module.countStringsMultiVerticles
      .apply(3)
      .onSuccess(it -> testContext.verify(testContext::completeNow));
  }

  @Test
  public void testA(){

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

  @Test
  public void testB(){

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
