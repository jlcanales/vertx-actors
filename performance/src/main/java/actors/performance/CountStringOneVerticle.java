package actors.performance;

import io.vertx.core.eventbus.Message;
import jsonvalues.JsObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static actors.performance.Functions.*;

public class CountStringOneVerticle implements Consumer<Message<Integer>>
{

  private Logger LOGGER = LoggerFactory.getLogger(JsGenVerticle.class);

  private Supplier<JsObj> gen = generator.apply(new Random());

  private int total = 0;

  @Override
  public void accept(final Message<Integer> req)
  {
    LOGGER.info("Received message");

    int sum = 0;
    for (int i = 0; i < req.body(); i++)
    {

      sum += filter.andThen(map)
                   .andThen(reduce)
                   .apply(gen.get());
    }
    total+=sum;
    req.reply(sum);


  }
}
