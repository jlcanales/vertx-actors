package actors.future;

import io.vertx.core.Future;
import jsonvalues.JsArray;
import jsonvalues.JsValue;

public class JsArrayFuture
{

  /**
   returns a future computation that returns a json array.
   @param fut a future JsValue
   @param others optionals futures of values
   @return a future JsArray
   */
  public static Future<JsArray> of(final Future<? extends JsValue> fut,
                                   final Future<? extends JsValue>... others
                                  )
  {
    Future<JsArray> result = fut.map(JsArray::of);
    for (final Future<? extends JsValue> other : others)
      result = result.flatMap(arr -> other.map(v -> arr.append(v)));
    return result;
  }

  /**
   returns a future computation that returns a json array. The head of the paths of the pairs have to
   be numbers, pointing to the positions of the array when it will be inserted
   @param pair a pair
   @param others optionals pairs
   @return a future JsArray
   */
  public Future<JsArray> of(final JsFuturePair pair,
                            final JsFuturePair... others
                           )
  {

    Future<JsArray> future = pair.future.map(it -> JsArray.empty()
                                                          .put(pair.path,
                                                               it
                                                              )
                                            );

    for (final JsFuturePair other : others)
    {
      future = future.flatMap(arr -> other.future.map(v -> arr.put(other.path,
                                                                   v
                                                                  )
                                                     )
                             );
    }

    return future;
  }


}
