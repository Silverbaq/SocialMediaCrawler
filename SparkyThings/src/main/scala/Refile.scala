import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by silverbaq on 5/17/17.
  */

object Refile{
  def main(args: Array[String]): Unit = {
    val input = "/home/silverbaq/Documents/BJTU/Project2/twitter-stmo/relations.txt"
    val output = "/home/silverbaq/Documents/BJTU/Project2/twitter-stmo/relations2.txt"

    val conf = new SparkConf().setAppName("Socialinfo")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    val relationInput = sc.textFile(input).map(_.replace("      ", " "))

    relationInput.saveAsTextFile(output)

  }
}