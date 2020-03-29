package dev.actors.actors.performance;

import actors.ActorsModule;
import io.vertx.core.*;
import jsonvalues.JsObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


public class Module extends ActorsModule
{

  public static Function<Integer, Future<Integer>> countStringsOneVerticle;
  public static Function<Integer, Future<Integer>> countStringsMultiProcesses;
  public static Function<Integer, Future<Integer>> countStringsMultiVerticles;
  public static Function<JsObj, Future<JsObj>> filter;
  public static Supplier<Function<JsObj, Future<JsObj>>> filterProcess;
  public static Supplier<Future<JsObj>> generator;
  public static Supplier<Function<String, Future<JsObj>>> generatorProcess;
  public static Function<JsObj, Future<JsObj>> map;
  public static Supplier<Function<JsObj, Future<JsObj>>> mapProcess;
  public static Function<JsObj, Future<Integer>> reduce;
  public static Supplier<Function<JsObj, Future<Integer>>> reduceProcess;

  @Override
  protected void defineActors(final List<Object> list)
  {
    filter = this.<JsObj, JsObj>toActorRef(list.get(0)).ask();

    map = this.<JsObj, JsObj>toActorRef(list.get(1)).ask();

    reduce = this.<JsObj, Integer>toActorRef(list.get(2)).ask();

    generator = () -> this.<String, JsObj>toActorRef(list.get(3)).ask().apply("");

    countStringsOneVerticle = this.<Integer, Integer>toActorRef(list.get(4)).ask();

    countStringsMultiVerticles = this.<Integer, Integer>toActorRef(list.get(5)).ask();

    countStringsMultiProcesses = this.<Integer, Integer>toActorRef(list.get(6)).ask();

    filterProcess = actors.spawn(Functions.filter);

    mapProcess = actors.spawn(Functions.map);

    reduceProcess = actors.spawn(Functions.reduce);

    generatorProcess = actors.spawn(new JsGenVerticle());
  }


  protected List<Future> deployActors()
  {
    final DeploymentOptions WORKER = new DeploymentOptions().setWorker(true);

    return Arrays.asList(actors.deploy(Functions.filter),
                         actors.deploy(Functions.map),
                         actors.deploy(Functions.reduce),
                         actors.deploy(new JsGenVerticle(),
                                       WORKER.setInstances(8)
                                      ),
                         actors.deploy(new CountStringOneVerticle(),
                                       WORKER
                                      ),
                         actors.deploy(new CountStringMultiVerticle(),
                                       WORKER
                                      ),
                         actors.deploy(new CountStringProcesses(),
                                       WORKER
                                       )
                        );
  }

  public static void main(String[] args) throws InterruptedException
  {
    Logger LOGGER = LoggerFactory.getLogger(Module.class);

    final Vertx vertx = Vertx.vertx();


    vertx.deployVerticle(new Module(),
                         h ->
                           Module.countStringsMultiVerticles.apply(10)
                                                            .onComplete(r ->
                                                                        {
                                                                          LOGGER.info("Result: {}",
                                                                                      r.result()
                                                                                     );
                                                                        })
                        );

    Thread.sleep(10000);

    LOGGER.info("The end.");


  }


}
