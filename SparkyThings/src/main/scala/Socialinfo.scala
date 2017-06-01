import java.lang.NullPointerException

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.GraphLoader
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql._

import scala.collection.mutable


/**
  * Created by silverbaq on 5/16/17.
  */

case class Profile(id: Int, name: String, birthday: String, death: String,
                   children: Int, mother: String, Father: String,
                   followers: List[Int], following: List[Int], pageRank: Double,
                   occupation: List[String], spouse: List[String],
                   wiki_content: String, imdb_content: String, images: List[String],
                   refs: List[String], wiki_url: String, tweets: List[String])

case class WikiProfile(id: String, birthday: String, children: Int, death: String,
                       father: String, mother: String, name: String,
                       occupation: List[String], spouse: List[String],
                       content: String, images: List[String], refs: List[String],
                       url: String)

case class IMDBProfile(id: String, name: String, birthday: String, death: String,
                       children: Int, occupation: List[String], spouse: List[String],
                       content: String)


object Socialinfo {
  val baseInput = "file:/home/silverbaq/Documents/BJTU/Project2/twitter-stmo/"
  val baseInput2 = "file:/home/silverbaq/Documents/BJTU/Project2/wiki_imdb-stmo/"

  val relationFile = baseInput + "relations2.txt"
  val twitterProfile = baseInput + "profiles.txt"
  val tweets = baseInput + "tweets.txt"

  val imdbBasicInfoInput = baseInput2 + "imdb_basic_output.txt"
  val imdbAdditionInfoInput = baseInput2 + "imdb_content_output.txt"
  val wikiBasicInfoInput = baseInput2 + "wiki_basic_output.txt"
  val wikiAdditionInfoInput = baseInput2 + "wiki_addition_output.txt"


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Socialinfo")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)


    // Read input into a graph and run pageRank
    val graph = GraphLoader.edgeListFile(sc, relationFile)
    val ranks = graph.pageRank(0.0001).vertices


    // Linking files together
    val spark = SparkSession.builder().appName("an example").getOrCreate()
    val basicWiki = spark.read.json(wikiBasicInfoInput)
    val additionalWiki = spark.read.json(wikiAdditionInfoInput)
    val basicImdb = spark.read.json(imdbBasicInfoInput)
    val imdbAddition = spark.read.json(imdbAdditionInfoInput)


    val wikiData = basicWiki.join(additionalWiki, Seq("id"))
    val wikiCollection = wikiData.collect()

    val imdbData = basicImdb.join(imdbAddition, Seq("id"))
    val imdbCollection = imdbData.collect()


    // Binding all the information
    val profiles = ranks.map(x => {

      // IMDB Profile
      val imdb = imdbCollection.filter(a => a(0).equals(x._1.toString)) //"Steve Wozniak"

      if (imdb.length > 0) {
        val imdbProfile = imdb.map(a => {
          new IMDBProfile(
            id = a(0).toString,
            name = a(4).toString,
            birthday = a(1).toString,
            death = a(3).toString,
            children = a(2).toString.toInt,
            occupation = a(5).asInstanceOf[mutable.WrappedArray[String]].toList,
            spouse = a(6).asInstanceOf[mutable.WrappedArray[String]].toList,
            content = a(7).toString
          )
        }) take 1

      } else {
        val imdbProfile = null
      }

      // Wikipedia Profile
      val wiki = wikiCollection.filter(a => a(0).equals(x._1.toString))

      if (wiki.length > 0){
        val wikiProfile = wiki.map(a => {
          new WikiProfile(
            id = a(0).toString,
            birthday = a(1).toString,
            children = if (a(2).toString.equals("")) 0 else a(2).toString.toInt,
            death = a(3).toString,
            father = a(4).toString,
            mother = a(5).toString,
            name = a(6).toString,
            occupation = if (a(7) == null) null else a(7).asInstanceOf[mutable.WrappedArray[String]].toList,
            spouse = null, //a(8).asInstanceOf[mutable.WrappedArray[Any]].map(b => b.toString).toList, // WrappedArray([Peggy Lentz<br />,1967,1974,end=divorced,marriage], [Mary Fisk<br />,1977,1987,end=divorced,marriage], [[[Mary Sweeney]]<br />,2006,2006,end=divorced,marriage], [Emily Stofle<br />,February 2009,null,null,marriage])
            content = a(9).toString,
            images = a(10).asInstanceOf[mutable.WrappedArray[String]].toList,
            refs = a(11).asInstanceOf[mutable.WrappedArray[String]].toList,
            url = a(12).toString
          )
          //println(a)
        }) take 1
        val test = ""

      } else {
        val wikiProfile = null
      }


    }).collect()


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
