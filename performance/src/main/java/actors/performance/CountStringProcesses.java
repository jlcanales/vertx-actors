package actors.performance;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static actors.performance.Functions.TIME_WAITING_MS;
import static actors.performance.Module.*;

public class CountStringProcesses implements Consumer<Message<Integer>>
{
  private final static Random random = new Random();
  private int total = 0;
  private Logger LOGGER = LoggerFactory.getLogger(JsGenVerticle.class);


  @Override
  public void accept(final Message<Integer> req)
  {
    LOGGER.info("Received message");

    List<Future> futures = new ArrayList<>();

    for (int i = 0; i < req.body(); i++)
    {
      futures.add(generatorProcess.get()
                                  .apply(TIME_WAITING_MS)
                                  .flatMap(obj -> filterProcess.get()
                                                               .apply(obj)
                                                               .flatMap(a -> mapProcess.get()
                                                                                       .apply(a))
                                                               .flatMap(b -> reduceProcess.get()
                                                                                          .apply(b))
                                          )
                 );
    }

    CompositeFuture.all(futures)
                   .onComplete(r ->
                               {
                                 final List<Integer> counts = r.result()
                                                               .list();
                                 final int sum = sum(counts);
                                 total += sum;
                                 req.reply(sum);
                               });

  }

  private static int sum(List<Integer> list)
  {
    int sum = 0;
    for (int i : list)
    {
      sum += i;
    }
    return sum;
  }

}
