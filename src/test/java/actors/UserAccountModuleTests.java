package actors;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsInt;
import jsonvalues.JsObj;
import jsonvalues.JsStr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class UserAccountModuleTests
{


  @BeforeAll
  public static void prepare(final Vertx vertx,
                             final VertxTestContext testContext
                            )
  {

    final Future<String> future = vertx.deployVerticle(new UserAccountModule());
    future.onSuccess(it-> testContext.completeNow());


  }

  @Test
  public void _15_is_not_legal_age(final Vertx vertx,
                                   final VertxTestContext context){
    UserAccountModule.isLegalAge
                     .apply(13)
                     .onComplete(it -> context.verify(()->
                                                      {
                                                        Assertions.assertFalse(it.result());
                                                        context.completeNow();
                                                      }
                                                      )
                                );
  }


  @Test
  public void _17_is_not_legal_age(final Vertx vertx,
                                   final VertxTestContext context){
    UserAccountModule.isLegalAge
                     .apply(17)
                     .onComplete(it -> context.verify(()->
                                                      {
                                                        Assertions.assertTrue(it.result());
                                                        context.completeNow();
                                                      }
                                                      )
                                );


  }


  @Test
  public void _user_is_valid(final Vertx vertx,
                             final VertxTestContext context){
    UserAccountModule.isValid
      .apply(JsObj.of("email", JsStr.of("imrafaelmerino@gmail.com"),
                      "age", JsInt.of(17),
                      "address",JsObj.of("city",JsStr.of("Madrid")),
                      "id",JsStr.of("03886961F")
                     )
            )
      .get()
      .onComplete(it -> context.verify(()->
                                       {
                                         Assertions.assertTrue(it.succeeded());
                                         Assertions.assertTrue(it.result());
                                         context.completeNow();
                                       }
                                      )
                 );


  }
}
