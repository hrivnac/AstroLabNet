package com.astrolabsoftware.AstroLabNet.DB.Jobs

// Scala
import scala.math.random

// Spark
import org.apache.spark.sql.SparkSession

/** <code>JavaPiJob</code> is the standard <em>Spark</em> job example.
  * @opt attributes
  * @opt operations
  * @opt types
  * @opt visibility
  * @author <a href="mailto:Julius.Hrivnac@cern.ch">J.Hrivnac</a> */
object ScalaPiJob {
  
  /** Run the example. */
  def main(args: Array[String]) {
    val spark = SparkSession.builder.appName("ScalaPiJob").getOrCreate()
    val slices = if (args.length > 0) args(0).toInt else 2
    val n = math.min(100000L * slices, Int.MaxValue).toInt
    val count = spark.sparkContext.parallelize(1 until n, slices).map {i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x*x + y*y <= 1) 1 else 0
      }.reduce(_ + _)
    println(s"Pi is roughly ${4.0 * count / (n - 1)}")
    spark.stop()
    }
    
  }