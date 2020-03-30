package actors.performance;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static actors.performance.Module.*;

public class CountStringMultiVerticle implements Consumer<Message<Integer>>
{

  private int total = 0;

  private Logger LOGGER = LoggerFactory.getLogger(CountStringMultiVerticle.class);



  public void accept(final Message<Integer> req)
  {

    LOGGER.info("Received message");

    List<Future> futures = new ArrayList<>();

    for (int i = 0; i < req.body(); i++)
    {

      futures.add(generator.get().flatMap(obj -> filter.apply(obj)
                                                       .flatMap(a -> map.apply(a))
                                                       .flatMap(b -> reduce.apply(b))
                                         )
                 );
    }

    CompositeFuture.all(futures).onComplete(r->{
      final List<Integer> counts = r.result().list();
      total+= sum(counts);
      LOGGER.info("Sending reply");
      req.reply(total);
    });

  }

  private static int sum(List<Integer> list) {
    int sum = 0;
    for (int i: list) {
      sum += i;
    }
    return sum;
  }

}
