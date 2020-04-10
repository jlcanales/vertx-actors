package actors;

import actors.expresions.Conditions;
import actors.expresions.Expressions;
import actors.errors.InvalidUser;
import io.vertx.core.Future;
import jsonvalues.JsObj;
import jsonvalues.JsPath;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class UserAccountModule extends ActorsModule
{

  public static Function<Integer,Future<Boolean>> isLegalAge;
  public static Function<String,Future<Boolean>> isValidId;
  public static Function<JsObj,Future<Boolean>> isValidAddress;
  public static Function<String,Future<Boolean>> isValidEmail;
  public static Function<JsObj,Future<JsObj>> register;

  public static Function<JsObj, Supplier<Future<Boolean>>> isValid = obj ->
    Conditions.and(() -> isLegalAge.apply(obj.getInt(JsPath.fromKey("age"))),
                   () -> isValidId.apply(obj.getStr(JsPath.fromKey("id"))),
                   () -> isValidAddress.apply(obj.getObj(JsPath.fromKey("address"))),
                   () -> isValidEmail.apply(obj.getStr(JsPath.fromKey("email")))
                  );

  public static Function<JsObj,Future<JsObj>> registerIfValid = obj ->
    Expressions.<JsObj>when(isValid.apply(obj))
               .then(() -> register.apply(obj))
               .otherwise(() -> Future.failedFuture(new InvalidUser()))
               .get();


  @Override
  protected void defineActors(final List<Object> futures)
  {
    isLegalAge = this.<Integer,Boolean>toActorRef(futures.get(0)).ask();
    isValidId = this.<String,Boolean>toActorRef(futures.get(1)).ask();
    isValidAddress = this.<JsObj,Boolean>toActorRef(futures.get(2)).ask();
    isValidEmail = this.<String,Boolean>toActorRef(futures.get(3)).ask();
    register = this.<JsObj,JsObj>toActorRef(futures.get(4)).ask();
  }

  @Override
  protected List<Future> deployActors()
  {
    return Arrays.asList(actors.deploy(UserAcountFunctions.isLegalAge),
                         actors.deploy(UserAcountFunctions.isValidId),
                         actors.deploy(UserAcountFunctions.isValidAddress),
                         actors.deploy(UserAcountFunctions.isValidEmail),
                         actors.deploy(UserAcountFunctions.register)
                        );
  }

}
