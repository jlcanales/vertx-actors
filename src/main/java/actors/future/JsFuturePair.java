package actors.future;

import io.vertx.core.Future;
import jsonvalues.JsPath;
import jsonvalues.JsValue;

/**
 represents a pair which first element is a path and second element is a future of a JsValue
 */
public class JsFuturePair
{


  final JsPath path;

  final Future<JsValue> future;

  private JsFuturePair(final JsPath path,
                      final Future<JsValue> future
                     )
  {
    this.path = path;
    this.future = future;
  }


  public static JsFuturePair pair(final String key,final Future<JsValue> value){
    return new JsFuturePair(JsPath.fromKey(key),value);
  }


  public static JsFuturePair pair(final JsPath path,final Future<JsValue> value){
    return new JsFuturePair(path,value);
  }
}
