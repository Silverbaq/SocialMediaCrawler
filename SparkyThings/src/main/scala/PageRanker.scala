import java.io._

import Socialinfo.relationFile
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.GraphLoader

/**
  * Created by silverbaq on 6/7/17.
  */

case class ProfilePageRank(profileID: String, PageRank: String) {
  def printOut(): String = {
    profileID + " " + PageRank
  }
}

object PageRanker {
  def main(args: Array[String]): Unit = {
    val baseInput = "file:/home/silverbaq/Documents/BJTU/Project2/FinalData/twitter/"
    val inputFile = baseInput + "new-relations2.txt"
    val outputFile = baseInput + "PageRankOutput.txt"


    val conf = new SparkConf().setAppName("PageRanker")
      .setMaster("local[2]")
      //.set("spark.executor.memory", "8g")
    val sc = new SparkContext(conf)


    // Read input into a graph and run pageRank
    val graph = GraphLoader.edgeListFile(sc, inputFile)
    val ranks = graph.pageRank(0.01).vertices

    val profiles = ranks.map(x => new ProfilePageRank(x._1.toString, x._2.toString)).collect()


    // Write to file
    import java.io.FileWriter
    import java.io.PrintWriter
    val pw = new PrintWriter(new FileWriter("PageRankOutput.txt"))
    profiles.foreach(x=> print(pw.println(x.printOut())))
    pw.close()

    sc.stop()
  }
}