package actors;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;

import java.util.function.Consumer;

/**

 @param <I> type of the message sent to the actor
 */
public class Actor<I> extends AbstractVerticle
{
  private final Consumer<Message<I>> consumer;
  private final String address;

  /**
   Creates an actor instance that when deployed will process the messages sent to the given
   address
   @param consumer consumer that takes and process the messages sent to the actor
   @param address the actor listen on this address
   */
  public Actor(final Consumer<Message<I>> consumer,
               final String address
              )
  {
    this.consumer = consumer;
    this.address = address;
  }

  @Override
  @SuppressWarnings("unchecked")
  //if you interact with this actor via its ActorRef object, there's no
  //way you can send it messages of type different than I
  public void start(final Promise<Void> promise)
  {
    try
    {
      vertx.eventBus()
           .consumer(address,
                     m -> consumer.accept((Message<I>) m));
      promise.complete();
    }

    catch (Exception e)
    {
      promise.fail(e);
    }
  }
}
