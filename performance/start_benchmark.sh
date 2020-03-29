java -Xms256m  -jar target/benchmark.jar -o count_strings_one_verticle.txt \
dev\.imrafaelmerino\.vertx\.performance\.MyBenchmark\.testCountStringOneVerticle

java -Xms256m  -jar target/benchmark.jar -o count_strings_multiple_verticles.txt \
dev\.imrafaelmerino\.vertx\.performance\.MyBenchmark\.testCountStringMultiVerticle

java -Xms256m  -jar target/benchmark.jar -o count_strings_processes.txt \
dev\.imrafaelmerino\.vertx\.performance\.MyBenchmark\.testCountStringProcesses

