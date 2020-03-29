package actors;
import io.vertx.core.*;
import java.util.List;
import java.util.Objects;

/**
 Verticle that acts as a module exposing all the deployed verticles. Any non standard message can
 be sent registering a codec overwriting the method registerMessageCodecs.
 */
public abstract class ActorsModule extends AbstractVerticle
{

  /**
   The purpose of this method is to initialize the functions/consumers/suppliers defined in the public
   static fields of this class that will be exposed.
   @param futures the list of VerticlesRef wrapped in futures that were deployed by the method
   deployActors
   */
  protected abstract void defineActors(final List<Object> futures);

  /**
   deploy all the verticles of the module
   @return a list of ActorRef wrapped in futures
   */
  //CompositeFuture.all method takes a list of futures with no generic type specified, that's why
  //List<Future> instead of List<Future<?>> has been used
  protected abstract List<Future> deployActors();

  /**
   Factory to deploy or spawn verticles
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
   @param vertx the vertx instance where he module will be deployed
   */
  protected void registerMessageCodecs(final Vertx vertx)
  {
  }

  private void failIfErrorOrInitModule(final Promise<Void> start,
                                       final AsyncResult<CompositeFuture> result
                                      )
  {
    if (result.failed()) start.fail(result.cause());
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
   Call this method from the defineActors method to initialize the actors exposed by
   this module that were deployed in the deployActors method
   @param object object result of deploying or spawing an actor
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
