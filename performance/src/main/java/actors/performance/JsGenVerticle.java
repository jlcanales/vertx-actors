package actors.performance;

import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.function.Consumer;

public class JsGenVerticle implements Consumer<Message<String>>
{

  private Logger LOGGER = LoggerFactory.getLogger(JsGenVerticle.class);

  private final Random random = new Random();

  @Override
  public void accept(final Message<String> message)
  {
    LOGGER.info("Received req");
    message.reply(Functions.generator
                    .apply(random)
                    .get()
                 );
    LOGGER.info("Sending resp");
  }
}
