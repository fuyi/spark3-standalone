package com.fuyi;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import java.util.List;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class WordCount {

    // Note:  To use standalone mode, set resource limit, do NOT depends on local file system, submit application with fat jar
    public static void main(String[] args) {
        System.out.println("Hello World!"); // Display the string.
        JavaSparkContext sc = new JavaSparkContext(new SparkConf()
                .setAppName("wordcount2")
                .setMaster("spark://localhost:7077")
                .set("spark.executor.memory", "512M")
//                .setMaster("local[4]")
        );

//        final int threshold = Integer.parseInt(args[1]);

        List<String> data = Arrays.asList("I am a student I love my family and friends I live in Sweden love dog");

        // Note: Executor can NOT read the file on driver program' local file system.
//        JavaRDD<String> tokenized = sc.textFile("wordcount_input.txt").flatMap(
        JavaRDD<String> tokenized = sc.parallelize(data).flatMap(
                new FlatMapFunction<String, String>() {
                    public Iterator<String> call(String s) {
                        return Arrays.asList(s.split(" ")).iterator();
                    }
                }
        );


//        System.out.println(tokenized.collect());
        JavaPairRDD<String, Integer> paired = tokenized.mapToPair(
                new PairFunction() {
                    public Tuple2 call(Object o) {
                        return new Tuple2(((String) o), 1);
                    }
                }
        );

        JavaPairRDD<String, Integer> counts = paired.reduceByKey(
                new Function2<Integer, Integer, Integer>() {
                    public Integer call(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }
        );

        System.out.println(counts.collectAsMap());

    }
}
