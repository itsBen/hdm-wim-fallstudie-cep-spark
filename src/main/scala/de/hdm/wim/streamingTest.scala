package de.hdm.wim

import org.apache.spark._
import org.apache.spark.api.java.function._
import org.apache.spark.streaming._
import org.apache.spark.streaming.api.java._
import scala.Tuple2

/**
  * Created by ben on 18/04/2017.
  */
object streamingTest {

  import org.apache.spark.SparkConf
  import org.scalatest.tools.Durations
  // Create a local StreamingContext with two working thread and batch interval of 1 second

  val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("blubb")
  //val jssc = new Nothing(conf, Durations.seconds(1))



  // Create a DStream that will connect to hostname:port, like localhost:9999
  //JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);
}
