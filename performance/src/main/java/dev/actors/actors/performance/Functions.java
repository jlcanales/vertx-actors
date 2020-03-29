package dev.actors.actors.performance;

import jsonvalues.*;

import java.util.Random;
import java.util.function.*;

public class Functions
{
  private static final int A = 97;
  private static final int Z= 122;
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


  public static Function<Random,Supplier<JsObj>> generator =  random -> () ->
  {
    try
    {
      Thread.sleep(100);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }

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
                                        "watermelon")
                            )
                   );
  };


  private static Function<Random, Function<Integer,Supplier<String>>> randomStr(){
    return  random -> length -> () -> random.ints(A, Z + 1)
                 .limit(length)
                 .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                 .toString();
  }

  public static void println(String message){

    //System.out.println(Thread.currentThread()+ ": "+message);
  }
}
