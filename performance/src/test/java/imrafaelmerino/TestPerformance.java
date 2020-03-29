package imrafaelmerino;

import dev.actors.actors.performance.Module;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestPerformance
{

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
}
