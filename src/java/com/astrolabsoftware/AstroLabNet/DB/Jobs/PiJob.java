package com.astrolabsoftware.AstroLabNet.DB.Jobs;

// Livy
import org.apache.livy.Job;
import org.apache.livy.JobContext;

// Spark
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

// Java
import java.util.List;
import java.util.ArrayList;

public class PiJob implements Job<Double>,
                              Function<Integer, Integer>,
                              Function2<Integer, Integer, Integer> {

  public PiJob(int samples) {
    _samples = samples;
    }

  @Override
  public Double call(JobContext ctx) throws Exception {
    List<Integer> sampleList = new ArrayList<>();
    for (int i = 0; i < _samples; i++) {
      sampleList.add(i + 1);
      }
    return 4.0d * ctx.sc().parallelize(sampleList).map(this).reduce(this) / _samples;
    }

  @Override
  public Integer call(Integer v1) {
    double x = Math.random();
    double y = Math.random();
    return (x*x + y*y < 1) ? 1 : 0;
    }

  @Override
  public Integer call(Integer v1, Integer v2) {
    return v1 + v2;
    }

  private final int _samples;

  }
