package actors;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class Main extends AbstractVerticle
{

  @Override
  public void start(final Promise<Void> promise)
  {

    final Future<String> future = vertx.deployVerticle(new ModuleExample());

    future.onComplete(result -> {
      if(result.failed()) promise.fail(result.cause());
      else promise.complete();
    });

  }
}
