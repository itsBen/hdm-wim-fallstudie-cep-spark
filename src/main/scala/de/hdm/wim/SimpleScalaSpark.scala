package de.hdm.wim

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

/**
  * Created by ben on 14/04/2017.
  * Count appearance of a's and b's in a file
  */
object SimpleScalaSpark {


  def main(args: Array[String]): Unit = {

    val logFile = "C:/DEV/spark/spark-2.1.0-bin-hadoop2.7/README.md"
    val conf    = new SparkConf().setAppName("Simple Application").setMaster("local[*]")

    val sc      = new SparkContext(conf)

    val logData = sc.textFile(logFile, 2).cache()

    var numAs   = logData.filter(line => line.contains("a")).count()
    var numBs   = logData.filter(line => line.contains("b")).count()

    println("Lines with a: %s, lines with b: %s".format(numAs, numBs))
  }



}
