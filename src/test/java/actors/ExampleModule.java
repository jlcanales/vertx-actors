package actors;
import io.vertx.core.*;
import io.vertx.core.eventbus.Message;
import jsonvalues.JsObj;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExampleModule extends ActorsModule
{



  Function<String, JsObj> getCustomer;

  public ExampleModule(final Function<String, JsObj> getCustomer) {
    this.getCustomer = getCustomer;
  }

  public static Function<Integer,Future<Integer>> triple;
  public static Supplier<Function<Integer,Future<Integer>>> quadruple;
  public static Function<Integer,Future<Integer>> addOne;
  public static Consumer<Integer> printNumber;


  @Override
  protected void defineActors(final List<Object> futures)
  {

    triple = this.<Integer,Integer>toActorRef(futures.get(0)).ask();
    addOne = this.<Integer,Integer>toActorRef(futures.get(1)).ask();
    printNumber = this.<Integer,Void>toActorRef(futures.get(2)).tell();
    quadruple = actors.spawn(i -> i *4);

  }

  @Override
  protected List<Future> deployActors()
  {
    final Function<Integer, Integer> triple = i -> i * 3;
    final Function<Integer, Integer> addOne = i -> i + 1;
    final Consumer<Message<Integer>> printNumber = m -> System.out.println(m.body());
    return Arrays.asList(actors.deploy(triple),
                         actors.deploy(addOne),
                         actors.deploy(printNumber)
                        );
  }
}
