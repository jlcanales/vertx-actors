package actors.future;

import io.vertx.core.Future;
import jsonvalues.JsObj;
import jsonvalues.JsPath;
import jsonvalues.JsValue;

public class JsObjFuture
{

  public Future<JsObj> of(final JsFuturePair pair,
                          final JsFuturePair... others
                         )
  {
    Future<JsObj> future = pair.future.map(it -> JsObj.empty()
                                                      .put(pair.path,
                                                           it
                                                          )
                                          );

    for (final JsFuturePair other : others)
    {
      future = future.flatMap(obj -> other.future.map(v -> obj.put(other.path,
                                                                   v
                                                                  )
                                                     )
                             );
    }

    return future;
  }

  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut
                         )
  {
    return fut.map(it -> JsObj.empty()
                              .put(path,
                                   it
                                  )
                  );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut
                         )
  {
    return fut.map(it -> JsObj.empty()
                              .put(JsPath.fromKey(key),
                                   it
                                  )
                  );
  }

  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1
                         )
  {
    return of(path,
              fut
             ).flatMap(obj -> fut1.map(v -> obj.put(path1,
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1
                         )
  {
    return of(key,
              fut
             ).flatMap(obj -> fut1.map(v -> obj.put(JsPath.fromKey(key1),
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1,
                          final JsPath path2,
                          final Future<? extends JsValue> fut2
                         )
  {
    return of(path,
              fut,
              path1,
              fut1
             ).flatMap(obj -> fut2.map(v -> obj.put(path2,
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1,
                          final String key2,
                          final Future<? extends JsValue> fut2
                         )
  {
    return of(key,
              fut,
              key1,
              fut1
             ).flatMap(obj -> fut2.map(v -> obj.put(JsPath.fromKey(key2),
                                                    v
                                                   )
                                      )
                      );
  }


  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1,
                          final JsPath path2,
                          final Future<? extends JsValue> fut2,
                          final JsPath path3,
                          final Future<? extends JsValue> fut3
                         )
  {
    return of(path,
              fut,
              path1,
              fut1,
              path2,
              fut2
             ).flatMap(obj -> fut3.map(v -> obj.put(path3,
                                                    v
                                                   )
                                      )
                      );
  }


  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1,
                          final String key2,
                          final Future<? extends JsValue> fut2,
                          final String key3,
                          final Future<? extends JsValue> fut3
                         )
  {
    return of(key,
              fut,
              key1,
              fut1,
              key2,
              fut2
             ).flatMap(obj -> fut3.map(v -> obj.put(JsPath.fromKey(key3),
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1,
                          final JsPath path2,
                          final Future<? extends JsValue> fut2,
                          final JsPath path3,
                          final Future<? extends JsValue> fut3,
                          final JsPath path4,
                          final Future<? extends JsValue> fut4
                         )
  {
    return of(path,
              fut,
              path1,
              fut1,
              path2,
              fut2,
              path3,
              fut3
             ).flatMap(obj -> fut4.map(v -> obj.put(path4,
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1,
                          final String key2,
                          final Future<? extends JsValue> fut2,
                          final String key3,
                          final Future<? extends JsValue> fut3,
                          final String key4,
                          final Future<? extends JsValue> fut4
                         )
  {
    return of(key,
              fut,
              key1,
              fut1,
              key2,
              fut2,
              key3,
              fut3
             ).flatMap(obj -> fut4.map(v -> obj.put(JsPath.fromKey(key4),
                                                    v
                                                   )
                                      )
                      );
  }


  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1,
                          final JsPath path2,
                          final Future<? extends JsValue> fut2,
                          final JsPath path3,
                          final Future<? extends JsValue> fut3,
                          final JsPath path4,
                          final Future<? extends JsValue> fut4,
                          final JsPath path5,
                          final Future<? extends JsValue> fut5
                         )
  {
    return of(path,
              fut,
              path1,
              fut1,
              path2,
              fut2,
              path3,
              fut3,
              path4,
              fut4
             ).flatMap(obj -> fut5.map(v -> obj.put(path5,
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1,
                          final String key2,
                          final Future<? extends JsValue> fut2,
                          final String key3,
                          final Future<? extends JsValue> fut3,
                          final String key4,
                          final Future<? extends JsValue> fut4,
                          final String key5,
                          final Future<? extends JsValue> fut5
                         )
  {
    return of(key,
              fut,
              key1,
              fut1,
              key2,
              fut2,
              key3,
              fut3,
              key4,
              fut4
             ).flatMap(obj -> fut5.map(v -> obj.put(JsPath.fromKey(key5),
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1,
                          final JsPath path2,
                          final Future<? extends JsValue> fut2,
                          final JsPath path3,
                          final Future<? extends JsValue> fut3,
                          final JsPath path4,
                          final Future<? extends JsValue> fut4,
                          final JsPath path5,
                          final Future<? extends JsValue> fut5,
                          final JsPath path6,
                          final Future<? extends JsValue> fut6
                         )
  {
    return of(path,
              fut,
              path1,
              fut1,
              path2,
              fut2,
              path3,
              fut3,
              path4,
              fut4,
              path5,
              fut5
             ).flatMap(obj -> fut6.map(v -> obj.put(path6,
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1,
                          final String key2,
                          final Future<? extends JsValue> fut2,
                          final String key3,
                          final Future<? extends JsValue> fut3,
                          final String key4,
                          final Future<? extends JsValue> fut4,
                          final String key5,
                          final Future<? extends JsValue> fut5,
                          final String key6,
                          final Future<? extends JsValue> fut6
                         )
  {
    return of(key,
              fut,
              key1,
              fut1,
              key2,
              fut2,
              key3,
              fut3,
              key4,
              fut4,
              key5,
              fut5
             ).flatMap(obj -> fut6.map(v -> obj.put(JsPath.fromKey(key6),
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1,
                          final JsPath path2,
                          final Future<? extends JsValue> fut2,
                          final JsPath path3,
                          final Future<? extends JsValue> fut3,
                          final JsPath path4,
                          final Future<? extends JsValue> fut4,
                          final JsPath path5,
                          final Future<? extends JsValue> fut5,
                          final JsPath path6,
                          final Future<? extends JsValue> fut6,
                          final JsPath path7,
                          final Future<? extends JsValue> fut7
                         )
  {
    return of(path,
              fut,
              path1,
              fut1,
              path2,
              fut2,
              path3,
              fut3,
              path4,
              fut4,
              path5,
              fut5,
              path6,
              fut6
             ).flatMap(obj -> fut7.map(v -> obj.put(path7,
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1,
                          final String key2,
                          final Future<? extends JsValue> fut2,
                          final String key3,
                          final Future<? extends JsValue> fut3,
                          final String key4,
                          final Future<? extends JsValue> fut4,
                          final String key5,
                          final Future<? extends JsValue> fut5,
                          final String key6,
                          final Future<? extends JsValue> fut6,
                          final String key7,
                          final Future<? extends JsValue> fut7
                         )
  {
    return of(key,
              fut,
              key1,
              fut1,
              key2,
              fut2,
              key3,
              fut3,
              key4,
              fut4,
              key5,
              fut5,
              key6,
              fut6
             ).flatMap(obj -> fut7.map(v -> obj.put(JsPath.fromKey(key7),
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1,
                          final JsPath path2,
                          final Future<? extends JsValue> fut2,
                          final JsPath path3,
                          final Future<? extends JsValue> fut3,
                          final JsPath path4,
                          final Future<? extends JsValue> fut4,
                          final JsPath path5,
                          final Future<? extends JsValue> fut5,
                          final JsPath path6,
                          final Future<? extends JsValue> fut6,
                          final JsPath path7,
                          final Future<? extends JsValue> fut7,
                          final JsPath path8,
                          final Future<? extends JsValue> fut8
                         )
  {
    return of(path,
              fut,
              path1,
              fut1,
              path2,
              fut2,
              path3,
              fut3,
              path4,
              fut4,
              path5,
              fut5,
              path6,
              fut6,
              path7,
              fut7
             ).flatMap(obj -> fut8.map(v -> obj.put(path8,
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1,
                          final String key2,
                          final Future<? extends JsValue> fut2,
                          final String key3,
                          final Future<? extends JsValue> fut3,
                          final String key4,
                          final Future<? extends JsValue> fut4,
                          final String key5,
                          final Future<? extends JsValue> fut5,
                          final String key6,
                          final Future<? extends JsValue> fut6,
                          final String key7,
                          final Future<? extends JsValue> fut7,
                          final String key8,
                          final Future<? extends JsValue> fut8
                         )
  {
    return of(key,
              fut,
              key1,
              fut1,
              key2,
              fut2,
              key3,
              fut3,
              key4,
              fut4,
              key5,
              fut5,
              key6,
              fut6,
              key7,
              fut7
             ).flatMap(obj -> fut8.map(v -> obj.put(JsPath.fromKey(key8),
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1,
                          final JsPath path2,
                          final Future<? extends JsValue> fut2,
                          final JsPath path3,
                          final Future<? extends JsValue> fut3,
                          final JsPath path4,
                          final Future<? extends JsValue> fut4,
                          final JsPath path5,
                          final Future<? extends JsValue> fut5,
                          final JsPath path6,
                          final Future<? extends JsValue> fut6,
                          final JsPath path7,
                          final Future<? extends JsValue> fut7,
                          final JsPath path8,
                          final Future<? extends JsValue> fut8,
                          final JsPath path9,
                          final Future<? extends JsValue> fut9
                         )
  {
    return of(path,
              fut,
              path1,
              fut1,
              path2,
              fut2,
              path3,
              fut3,
              path4,
              fut4,
              path5,
              fut5,
              path6,
              fut6,
              path7,
              fut7,
              path8,
              fut8
             ).flatMap(obj -> fut9.map(v -> obj.put(path9,
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1,
                          final String key2,
                          final Future<? extends JsValue> fut2,
                          final String key3,
                          final Future<? extends JsValue> fut3,
                          final String key4,
                          final Future<? extends JsValue> fut4,
                          final String key5,
                          final Future<? extends JsValue> fut5,
                          final String key6,
                          final Future<? extends JsValue> fut6,
                          final String key7,
                          final Future<? extends JsValue> fut7,
                          final String key8,
                          final Future<? extends JsValue> fut8,
                          final String key9,
                          final Future<? extends JsValue> fut9
                         )
  {
    return of(key,
              fut,
              key1,
              fut1,
              key2,
              fut2,
              key3,
              fut3,
              key4,
              fut4,
              key5,
              fut5,
              key6,
              fut6,
              key7,
              fut7,
              key8,
              fut8
             ).flatMap(obj -> fut9.map(v -> obj.put(JsPath.fromKey(key9),
                                                    v
                                                   )
                                      )
                      );
  }

  public Future<JsObj> of(final String key,
                          final Future<? extends JsValue> fut,
                          final String key1,
                          final Future<? extends JsValue> fut1,
                          final String key2,
                          final Future<? extends JsValue> fut2,
                          final String key3,
                          final Future<? extends JsValue> fut3,
                          final String key4,
                          final Future<? extends JsValue> fut4,
                          final String key5,
                          final Future<? extends JsValue> fut5,
                          final String key6,
                          final Future<? extends JsValue> fut6,
                          final String key7,
                          final Future<? extends JsValue> fut7,
                          final String key8,
                          final Future<? extends JsValue> fut8,
                          final String key9,
                          final Future<? extends JsValue> fut9,
                          final String key10,
                          final Future<? extends JsValue> fut10
                         )
  {
    return of(key,
              fut,
              key1,
              fut1,
              key2,
              fut2,
              key3,
              fut3,
              key4,
              fut4,
              key5,
              fut5,
              key6,
              fut6,
              key7,
              fut7,
              key8,
              fut8,
              key9,
              fut9
             ).flatMap(obj -> fut10.map(v -> obj.put(JsPath.fromKey(key10),
                                                     v
                                                    )
                                       )
                      );
  }

  public Future<JsObj> of(final JsPath path,
                          final Future<? extends JsValue> fut,
                          final JsPath path1,
                          final Future<? extends JsValue> fut1,
                          final JsPath path2,
                          final Future<? extends JsValue> fut2,
                          final JsPath path3,
                          final Future<? extends JsValue> fut3,
                          final JsPath path4,
                          final Future<? extends JsValue> fut4,
                          final JsPath path5,
                          final Future<? extends JsValue> fut5,
                          final JsPath path6,
                          final Future<? extends JsValue> fut6,
                          final JsPath path7,
                          final Future<? extends JsValue> fut7,
                          final JsPath path8,
                          final Future<? extends JsValue> fut8,
                          final JsPath path9,
                          final Future<? extends JsValue> fut9,
                          final JsPath path10,
                          final Future<? extends JsValue> fut10
                         )
  {
    return of(path,
              fut,
              path1,
              fut1,
              path2,
              fut2,
              path3,
              fut3,
              path4,
              fut4,
              path5,
              fut5,
              path6,
              fut6,
              path7,
              fut7,
              path8,
              fut8,
              path9,
              fut9
             ).flatMap(obj -> fut10.map(v -> obj.put(path10,
                                                     v
                                                    )
                                       )
                      );
  }
}

