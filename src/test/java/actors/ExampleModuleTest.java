package actors;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.function.Consumer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
public class ExampleModuleTest
{

  static Actors actors;
  @BeforeAll
  public static void prepare(Vertx vertx,
                             VertxTestContext testContext
                            )
  {
    actors = new Actors(vertx);

    testContext.completeNow();

  }


  @Test
  public void test_number_of_instances(Vertx vertx,
                                       VertxTestContext context
                                      )
  {
    int i = 100000;

    final Checkpoint checkpoint = context.checkpoint(i);
    final Consumer<Message<JsObj>> consumer = m -> m.reply(m.body());

    for (int i1 = 0; i1 < i; i1++)
    {
      actors.deploy(consumer,new DeploymentOptions().setWorker(true))
            .onComplete(r -> checkpoint.flag());
    }
  }

  @Test
  public void test_sending_messages(Vertx vertx,
                                    VertxTestContext context
                                   ) throws InterruptedException
  {

    Future<ActorRef<Integer, Integer>> addOne = actors.deploy(i -> i + 1);
    Future<ActorRef<Integer, Integer>> triple = actors.deploy(i -> i +3);
    addOne.flatMap(a -> a.ask()
                         .apply(1))
          .flatMap(b -> triple.flatMap(c -> c.ask()
                                             .apply(b)));

    Thread.sleep(1000);

    addOne.onComplete(h ->
                      {

                        final ActorRef<Integer, Integer> vref = h.result();
                        final Future<Integer> fut = vref
                          .ask()
                          .apply(3);

                        fut.onComplete(i ->
                                       {
                                         assertEquals(4,
                                                      i.result()
                                                     );
                                         vref.undeploy()
                                             .onComplete(a -> context.completeNow());
                                       });

                      }
                     );
  }

  @Test
  @DisplayName("")
  public void test_verticle_consumer(Vertx vertx,
                                     VertxTestContext context
                                    )
  {
    final Consumer<Message<JsObj>> consumer = m -> m.reply(m.body());
    actors.deploy(consumer)
          .onComplete(r -> r.result()
                               .ask()
                               .apply(JsObj.empty())
                               .onComplete(h -> context.verify(() ->
                                                               {
                                                                 assertEquals(h.result(),
                                                                              JsObj.empty()
                                                                             );
                                                                 context.completeNow();
                                                               }
                                                              )
                                          )
                        );
  }

  @Test
  @DisplayName("")
  public void test_verticle_deployment(Vertx vertx,
                                       VertxTestContext context
                                      )
  {
    final Consumer<Message<JsObj>> messageConsumer = m -> m.reply(m.body());
    actors.deploy(messageConsumer)
          .onComplete(r ->
                         {
                           context.verify(() -> assertTrue(r.succeeded()));
                           context.completeNow();
                         }
                        );

  }


}
