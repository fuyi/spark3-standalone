
from pyspark import SparkContext
from pyspark.sql import SparkSession

# execution on simple RDD
sc = SparkContext()

rdd = sc.parallelize([1,2,3,4,5,6])

print(rdd.collect())
print(rdd.sum())