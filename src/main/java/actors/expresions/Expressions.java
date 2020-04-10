package actors.expresions;

import io.vertx.core.Future;

import java.util.function.Supplier;

public class Expressions
{
  public static <O> IfThenElse<O> when(final Supplier<Future<Boolean>> condition){
    return new IfThenElse<>(condition);
  }
}
