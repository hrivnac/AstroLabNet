from __future__ import print_function

import sys
from random import random
from operator import add
from pyspark import SparkContext, SparkConf
from pyspark.sql import SparkSession

if __name__ == "__main__":
  spark = SparkSession.builder.appName("PythonPiJob").getOrCreate()
  slices = 2
  samples = 100000 * slices

  def f(_):
    x = random() * 2 - 1
    y = random() * 2 - 1
    return 1 if x ** 2 + y ** 2 <= 1 else 0

  count = spark.sparkContext.parallelize(range(1, samples + 1), slices).map(f).reduce(add)
  pi = 4.0 * count / samples
  print("Pi is roughly %f" % pi)
