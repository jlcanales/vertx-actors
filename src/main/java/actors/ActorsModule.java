package actors;
import io.vertx.core.*;
import java.util.List;
import java.util.Objects;

/**
 Actor that acts as a module deploying and exposing all the deployed actors. Any non standard message
 can be sent registering a codec overwriting the method {@link #registerMessageCodecs(Vertx)}.
 */
public abstract class ActorsModule extends AbstractVerticle
{

  /**
   The purpose of this method is to initialize the functions/consumers/suppliers defined in
   static fields of this class that will be exposed.
   @param futures the list of ActorRef wrapped in futures that were returned by the method
   {@link #deployActors()}}.
   */
  protected abstract void defineActors(final List<Object> futures);

  /**
   deploy all the actors of the module using the factory {@link Actors}
   @return a list of ActorRef wrapped in futures
   */
  //CompositeFuture.all method takes a list of futures with no generic type specified, that's why
  //List<Future> instead of List<Future<?>> has been used
  protected abstract List<Future> deployActors();

  /**
   Factory to deploy or spawn actors
   */
  protected Actors actors;

  @Override
  public void start(final Promise<Void> start)
  {

    try
    {
      actors = new Actors(Objects.requireNonNull(vertx));
      registerMessageCodecs(vertx);
      CompositeFuture.all(deployActors())
                     .onComplete(result -> failIfErrorOrInitModule(start,
                                                                   result
                                                                  ));
    }
    catch (Exception e)
    {
      start.fail(e);
    }
  }

  /**
   Overwrite this method to register all the message codecs if you want to send any non standard
   message across the event bus.
   @param vertx the vertx instance where the module will be deployed
   */
  protected void registerMessageCodecs(final Vertx vertx)
  {
  }

  private void failIfErrorOrInitModule(final Promise<Void> start,
                                       final AsyncResult<CompositeFuture> result
                                      )
  {
    if (result.failed()) start.fail(result.cause());
    else
    try
    {
      defineActors(result.result().list());
      start.complete();
    }
    catch (Exception e)
    {
      start.fail(e);
    }
  }

  /**
   Call this method from the {@link #defineActors(List)} method to cast every object of the list into
   its real type {@link ActorRef}
   @param object object result of deploying or spawing an actor with the factory {@link Actors}
   @param <I> the type of the input message
   @param <O> the type of the output message
   @return an ActorRef
   */
  @SuppressWarnings("unchecked")
  //It's responsibility of the caller to make sure the object has the correct type
  protected  <I, O> ActorRef<I, O> toActorRef(final Object object)
  {
    return (ActorRef<I, O>) object;
  }


}
