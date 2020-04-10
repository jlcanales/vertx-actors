package actors.performance;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import jsonvalues.*;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

public class Functions
{
  private static final int A = 97;
  private static final int Z= 122;
  public static int TIME_WAITING_MS = 100;


  public static Function<Random,Supplier<JsObj>> generator =  random -> () ->
  {

    final Supplier<String> strGen = randomStr().apply(random)
                                               .apply(10);
    return JsObj.of("a",
                    JsStr.of(strGen.get()),
                              "b",
                              JsStr.of(strGen.get()),
                              "c",
                              JsStr.of(strGen.get()),
                              "d",
                              JsInt.of(random.nextInt()),
                              "e",
                              JsObj.of("f",
                                       JsStr.of(strGen.get()),
                                       "g",
                                       JsStr.of(strGen.get()),
                                       "h",
                                       JsStr.of(strGen.get()),
                                       "i",
                                       JsBool.TRUE,
                                       "j",
                                       JsArray.of("apple",
                                                  "banana",
                                                  "pineapple",
                                                  "tangerine",
                                                  "melon",
                                                  "watermelon"
                                                 )

                                      )
                             );
  };
  public static Function<JsObj, JsObj> id = obj -> obj;
  public static Function<JsonObject, JsonObject> jacksonId = obj -> obj;
  public static Function<String,JsonObject> jacksonParser = JsonObject::new;
  public static Function<String, JsObj> parser = str ->
  {
    try
    {
      return JsObj.parse(str);
    }
    catch (MalformedJson malformedJson)
    {
      throw new RuntimeException();
    }
  };
  private static Predicate<JsPair> isStr = pair -> pair.value.isStr();
  public static Function<JsObj, JsObj> filter = obj ->
  {
    println("Filter");
    return obj.filterAllValues(isStr);
  };
  private static Function<JsPair, JsValue> toNumber = pair -> JsInt.of(pair.value.toJsStr().value.length());
  public static Function<JsObj, JsObj> map = obj ->
  {
    println("Map");

    return obj.mapAllValues(toNumber);
  };
  private static BinaryOperator<Integer> op = Integer::sum;
  private static Function<JsPair, Integer> mapPair = pair -> pair.value.toJsInt().value;
  private static Predicate<JsPair> predicate = pair -> true;
  public static Function<JsObj, Integer> reduce = json ->
  {
    println("Reduce");

    return json.reduceAll(op,
                          mapPair,
                          predicate
                         )
               .orElse(0);
  };

  public static <O> void await20segForEnding(Future<O> fn){
    awaitForEnding(fn,20,TimeUnit.SECONDS);
  }

  public static <O> void awaitForEnding(Future<O> fn,int time, TimeUnit unit){
    CountDownLatch latch = new CountDownLatch(1);

    try
    {
      fn.onComplete(it ->latch.countDown());
      latch.await(time,
                  unit);
    }
    catch (InterruptedException e)
    {
      throw new RuntimeException(e);
    }
  }

  public static void println(String message){

    //System.out.println(Thread.currentThread()+ ": "+message);
  }

  private static Function<Random, Function<Integer,Supplier<String>>> randomStr(){
    return  random -> length -> () -> random.ints(A, Z + 1)
                 .limit(length)
                 .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                 .toString();
  }
}
