package actors.expresions;
import io.vertx.core.Future;
import java.util.function.Supplier;
public class IfThenElse<O> implements Supplier<Future<O>>
{

  private Supplier<Future<Boolean>> condition;
  private Supplier<Future<O>>  then;
  private Supplier<Future<O>>  otherwise;

  IfThenElse(final Supplier<Future<Boolean>> condition) {
    this.condition = condition;
  }

  public IfThenElse<O> then(final Supplier<Future<O>> then){
    this.then = then;
    return this;
  }

  public IfThenElse<O> otherwise(final Supplier<Future<O>> otherwise){
    this.otherwise = otherwise;
    return this;
  }

  @Override
  public Future<O> get()
  {
    final Future<Boolean> futureCon = condition.get();
    if(futureCon.failed()) return Future.failedFuture(futureCon.cause());
    return futureCon.flatMap(c -> {
      if (c) return then.get();
      else return otherwise.get();
    });
  }
}
