package actors;

import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import jsonvalues.JsArray;
import jsonvalues.JsObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import static java.util.Objects.requireNonNull;

/**
 Class that deploys and spawns verticles. If an address is not provided, one is generated. You only
 need a ActorRef to interact with a verticle. When a verticle is deployed it's waiting for messages
 to be processed. When a verticle is spawn, it processes a message or does some stuff and after that
 it's undeployed automatically. It register codecs to work with the immutable and persistent Jsons
 from the library json-values
 */
public class Actors
{
  private static final DeploymentOptions DEFAULT_OPTIONS = new DeploymentOptions();
  private final static AtomicLong processSequence = new AtomicLong(0);
  private final Vertx vertx;
  private final DeploymentOptions deploymentOptions;

  /**
   Creates a factory to deploy and spawn verticles
   @param vertx the vertx instance
   @param deploymentOptions the default deployment options that will be used for deploying and spawing
   verticles if one is not provided
   */
  public Actors(final Vertx vertx,
                final DeploymentOptions deploymentOptions
               )
  {
    this.vertx = requireNonNull(vertx);
    this.deploymentOptions = requireNonNull(deploymentOptions);
    registerJsValuesCodecs();
  }

  /**
  Creates a factory to deploy and spawn verticles
   */
  public Actors(final Vertx vertx)
  {

    this(vertx,
         DEFAULT_OPTIONS);

  }

  /**
   It deploys a verticle that listen on the given address.
   @param address the address of the verticle
   @param consumer the consumer that will process the messages sent to the verticle
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Future<ActorRef<I, O>> deploy(final String address,
                                              final Consumer<Message<I>> consumer
                                             )
  {
    return deploy(address,
                  consumer,
                  deploymentOptions
                 );
  }

  /**
   It deploys a verticle
   @param consumer the consumer that will process the messages sent to the verticle
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Future<ActorRef<I, O>> deploy(final Consumer<Message<I>> consumer)
  {
    return deploy(generateProcessAddress(),
                  consumer,
                  deploymentOptions
                 );
  }

  /**

   @param consumer the consumer that will process the messages sent to the verticle
   @param options options for configuring the verticle deployment
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Future<ActorRef<I, O>> deploy(final Consumer<Message<I>> consumer,
                                              final DeploymentOptions options
                                             )
  {
    return deploy(generateProcessAddress(),
                  consumer,
                  options
                 );
  }

  /**

   @param address the address of the verticle
   @param consumer the consumer that will process the messages sent to the verticle
   @param options options for configuring the verticle deployment
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Future<ActorRef<I, O>> deploy(final String address,
                                              final Consumer<Message<I>> consumer,
                                              final DeploymentOptions options
                                             )
  {
    final int instances = options.getInstances();
    final Set<String> ids = new HashSet<>();
    final List<Future> futures = new ArrayList<>();
    final Actor<I> verticle = new Actor<>(consumer,
                                          address
    );
    for (int i = 0; i < instances; i++)
    {
      final Future<String> future = vertx.deployVerticle(verticle,
                                                         options.setInstances(1)
                                                        );
      futures.add(future.onSuccess(ids::add));
    }

    return CompositeFuture.all(futures)
                          .flatMap(cf -> getActorRefFuture(address,
                                                           ids,
                                                           cf
                                                          ));
  }

  /**

   @param address the address of the verticle
   @param fn the function that takes a message of type I and produces an output of type O
   @param options options for configuring the verticle deployment
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Future<ActorRef<I, O>> deploy(final String address,
                                              final Function<I, O> fn,
                                              final DeploymentOptions options
                                             )
  {
    final int instances = options.getInstances();
    final Set<String> ids = new HashSet<>();
    final List<Future> futures = new ArrayList<>();
    final Actor<I> verticle = new Actor<>(m -> m.reply(fn.apply(m.body())),
                                          address
    );
    for (int i = 0; i < instances; i++)
    {
      final Future<String> future = vertx.deployVerticle(verticle,
                                                         options.setInstances(1)
                                                        );
      futures.add(future.onSuccess(ids::add));
    }

    return CompositeFuture.all(futures)
                          .flatMap(cf -> getActorRefFuture(
                            address,
                            ids,
                            cf
                                                          )
                                  );
  }

  /**

   @param address the address of the verticle
   @param fn the function that takes the messages and produces an output
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Future<ActorRef<I, O>> deploy(final String address,
                                              final Function<I, O> fn
                                             )
  {
    return deploy(address,
                  fn,
                  deploymentOptions
                 );
  }

  /**

   @param fn the function that takes a message of type I and produces an output of type O
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Future<ActorRef<I, O>> deploy(final Function<I, O> fn)
  {
    return deploy(generateProcessAddress(),
                  fn,
                  deploymentOptions
                 );
  }

  /**

   @param consumer the consumer that will process the messages sent to the verticle
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Supplier<Function<I, Future<O>>> spawn(final Consumer<Message<I>> consumer)
  {
    return spawn(consumer,
                 deploymentOptions);
  }

  /**

   @param consumer the consumer that will process the messages sent to the verticle
   @param options options for configuring the verticle deployment
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Supplier<Function<I, Future<O>>> spawn(final Consumer<Message<I>> consumer,
                                                       final DeploymentOptions options
                                                      )
  {
    return () -> n ->
    {
      Future<ActorRef<I, O>> future = deploy(consumer,
                                             options
                                            );
      return future.flatMap(r -> r.ask()
                                  .apply(n)
                                  .onComplete(a -> r.undeploy()));
    };
  }

  /**

   @param fn the function that takes a message of type I and produces an output of type O
   @param options options for configuring the verticle deployment
   @param <I> the type of the message sent to the verticle
   @param <O> the type of the reply
   @return an ActorRef wrapped in a future
   */
  public <I, O> Supplier<Function<I, Future<O>>> spawn(final Function<I, O> fn,
                                                       final DeploymentOptions options
                                                      )
  {
    return () -> n ->
    {
      Consumer<Message<I>> consumer = m -> m.reply(fn.apply(m.body()));

      Future<ActorRef<I, O>> future = deploy(consumer,
                                             options);
      return future.flatMap(r -> r.ask()
                                  .apply(n)
                                  .onComplete(a -> r.undeploy())
                           );
    };
  }

  /**

   @param fn the function that takes a message of type I and produces an output of type O
   @param <I> the type of the input message
   @param <O> the type of the output
   @return an ActorRef wrapped in a future
   */
  public <I, O> Supplier<Function<I, Future<O>>> spawn(final Function<I, O> fn)
  {
    return spawn(fn,
                 deploymentOptions);
  }

  private static String generateProcessAddress()
  {
    return "__vertx.generated." + processSequence.incrementAndGet();
  }

  private <I, O> Future<ActorRef<I, O>> getActorRefFuture(final String address,
                                                          final Set<String> ids,
                                                          final CompositeFuture cf
                                                         )
  {
    if (cf.isComplete()) return Future.succeededFuture(new ActorRef<>(vertx,
                                                                      ids,
                                                                      address
                                                       )
                                                      );
    else return Future.failedFuture(cf.cause());
  }

  private void registerJsValuesCodecs()
  {
    vertx.eventBus()
         .registerDefaultCodec(JsObj.class,
                               new JsObjValCodec()
                              );

    vertx.eventBus()
         .registerDefaultCodec(JsArray.class,
                               new JsArrayValCodec()
                              );
  }

}
