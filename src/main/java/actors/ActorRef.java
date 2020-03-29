package actors;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 It represents a reference to a Verticle, the unit of computation. It allows you to send message
 to the Verticle with the tell method, or establish conversations with the ask method.

 @param <I> the type of the message sent to the verticle
 @param <O> the type of the message returned by the verticle
 */
public class ActorRef<I, O>
{

  private final Vertx vertx;
  /**
   the default delivery options that will be used if not specified
   */
  private static final DeliveryOptions DEFAULT = new DeliveryOptions();
  /**
   address where a Verticle is listening to
   */
  public final String address;
  /**
   the identifiers assigned to the different instances of the Verticle after being deployed
   */
  public final Set<String> ids;

  ActorRef(final Vertx vertx,
           final Set<String> ids,
           final String address
          )
  {
    this.vertx = vertx;
    this.ids = ids;
    this.address = address;
  }

  /**
   Method to establish a conversation with the verticle
   @param options the delivery options
   @return a function that takes an object of type I and returns an object of type O wrapped in a
   future
   */
  public Function<I, Future<O>> ask(final DeliveryOptions options)
  {
    requireNonNull(options);
    return body -> vertx.eventBus().<O>request(address,
                                               body,
                                               options
                                              ).map(Message::body);
  }

  /**
   Method to establish a conversation with the verticle
   @return a function that takes an object of type I and returns an object of type O wrapped in a
   future
   */
  public Function<I, Future<O>> ask()
  {
    return ask(DEFAULT);
  }


  /**
   Method to send a message to the verticle.
   @param options the delivery options
   @return a consumer that takes an object of type I
   */
  public Consumer<I> tell(final DeliveryOptions options)
  {
    requireNonNull(options);
    return body -> vertx.eventBus().<I>request(address,
                                               body,
                                               options
                                              ).map(Message::body);
  }

  /**
   Method to send a message to the verticle.
   @return a consumer that takes an object of type I
   */
  public Consumer<I> tell()
  {
    return tell(DEFAULT);
  }

  /**
   Undeploy all the verticle instances
   @return a future
   */
  public Future<Void> undeploy()
  {

    List<Future> futures = new ArrayList<>();
    for (final String id : ids)
    {
      final Future<Void> future = vertx.undeploy(id);
      futures.add(future);

    }
    final Future<Void> fut = CompositeFuture.all(futures)
                                            .flatMap(it ->
                                                     {
                                                       if (it.succeeded()) return Future.succeededFuture();
                                                       else return Future.failedFuture(it.cause());
                                                     });
    return fut;
  }

}
