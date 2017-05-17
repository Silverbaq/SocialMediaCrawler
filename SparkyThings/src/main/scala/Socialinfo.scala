import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SparkSession}
import org.apache.spark.graphx.{GraphLoader}


/**
  * Created by silverbaq on 5/16/17.
  */

object Socialinfo {
  def main(args: Array[String]): Unit = {
    val baseInput = "file:/home/silverbaq/Documents/BJTU/Project2/twitter-stmo/"

    val relationFile = baseInput + "relations2.txt"
    //val weiboInput = "file:/home/silverbaq/Downloads/weibo/weibo.txt"

    val conf = new SparkConf().setAppName("Socialinfo")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)


    // Read input into a graph and run pageRank
    val graph = GraphLoader.edgeListFile(sc, relationFile)
    //graph.edges.collect.foreach(println)
    val ranks = graph.pageRank(0.0001).vertices

    // find the 100th top ranker by:
    // take the first 100, do a reverse order by there page ranking
    // get the id of "user" on index 99
    val top100Id = ranks.takeOrdered(100)(Ordering[Double].reverse.on(x => x._2))
    top100Id.foreach(println)


    /*
    // Find followers of the user
    // val followers = graph.edges.filter(a => a.dstId == top100Id).map(a => a.srcId)
    val followersRDD = sc.textFile(relationInput).map(_.split("\t")).filter(a => a.length > 1).filter(a => a(1).equals(top100Id.toString))

    // Clean/flatMap and make to array
    val followers = followersRDD.flatMap(a => a).filter(a => !a.equals(top100Id.toString)).collect()

    // Read the weibo file as a table
    val weiboTable = sc.textFile(weiboInput).map(_.split("\t")).filter(a => a.length > 5)

    // Get comments from all the followers, and save them in individual files
    followers.map { id =>

      // maps the userId and the comments of a user.
      // then only returns the comments that match the THIS user
      val something = weiboTable.map(a => (a(1), a(5))).filter(a => a._1.equals(id)).map(a => a._2)

      val some = something.collect()


      if (!some.isEmpty)
        something.saveAsTextFile("file:/home/silverbaq/Downloads/weibo/top100/" + id)

    }
    */
    sc.stop()
  }
}
