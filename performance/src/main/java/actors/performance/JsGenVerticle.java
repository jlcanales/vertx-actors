package actors.performance;

import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.function.Consumer;

/**
 Generates a JsObj waiting for the specified time
 */
public class JsGenVerticle implements Consumer<Message<Integer>>
{

  private Logger LOGGER = LoggerFactory.getLogger(JsGenVerticle.class);

  private final Random random = new Random();

  @Override
  public void accept(final Message<Integer> message)
  {
    LOGGER.info("Received req");
    try
    {
      Thread.sleep(message.body());
      message.reply(Functions.generator
                      .apply(random)
                      .get()
                   );
      LOGGER.info("Sending resp");
    }
    catch (InterruptedException e)
    {
      throw new RuntimeException(e);
    }

  }
}
