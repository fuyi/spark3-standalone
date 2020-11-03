## TODO

* ~Create Spark application from notebook~
* ~Create PySpark job from local shell~
* ~use spark-submit to submit a python job~
* ~spark-submit to run a example java spark application~
* Run Event2S3 pipline on the docker cluster

## Submit Java Pipeline to Spark standalone cluster

1. Build fat jar with `mvn package`
2. Submit the applciation `spark-submit --master spark://localhost:7077 --executor-memory 512M target/sparkwordcount-0.1.0-SNAPSHOT.jar`

## Question

* How to view application detail UI: http://localhost:4041/jobs/

## Issues

* Spark master allocates 0 cores to application: 

Hints: https://stackoverflow.com/questions/51318426/spark-standalone-application-gets-0-cores

Solution: set executor memory to 512m instead of the default 1G: `pyspark --master spark://localhost:7077 --executor-memory 512M`

* Exception: Python in worker has different version 3.7 than that in driver 3.8, PySpark cannot run with different minor versions.Please check environment variables PYSPARK_PYTHON and PYSPARK_DRIVER_PYTHON are correctly set.

Solution: use python3.7 for driver program

1. Install python3.7 if not installed already  `pyenv install 3.7.3`
2. Create virtual env with python 3.7.3 `pipenv --python 3.7.3 shell`
3. launch pyspark in this virtual env `pyspark --master spark://localhost:7077 --executor-memory 512M`



## Learning

* How to setup a standalone Spark3 cluster.
* How to connect local PySparkShell to standalone cluster.
* Spark Session vs Spark Context. (Spark session is higher level abstraction, deal with structured dataset, Spark context is low level API, deal directly with RDD)
* Python version for driver program and executor program need to be the same major version, otherwise executor throws exception.
* More worker can be added from worker node by executing following command from worker node

`bin/spark-class org.apache.spark.deploy.worker.Worker spark://${SPARK_MASTER_HOST}:${SPARK_MASTER_PORT} >> logs/spark-worker.out`

## Reference

Setup spark3 docker cluster: https://www.kdnuggets.com/2020/07/apache-spark-cluster-docker.html
Difference between DataFrame/Dataset vs RDD: https://databricks.com/blog/2016/07/14/a-tale-of-three-apache-spark-apis-rdds-dataframes-and-datasets.html
Use python3 for pyspark on MacOS: https://stackoverflow.com/questions/30279783/apache-spark-how-to-use-pyspark-with-python-3