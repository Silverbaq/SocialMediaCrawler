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

case class Tweet(id: String, date: String, message: String)

case class TwitterProfile(id: String, name: String, nickname: String, followers: List[String],
                          following: List[String], tweets: List[Tweet])


object Socialinfo {
  val baseInput = "file:/home/silverbaq/Documents/BJTU/Project2/twitter-stmo/"
  val baseInput2 = "file:/home/silverbaq/Documents/BJTU/Project2/wiki_imdb-stmo/"

  val relationFile = baseInput + "relations2.txt"
  val twitterProfileInput = baseInput + "profiles.txt"
  val tweetsInput = baseInput + "tweets.txt"

  val imdbBasicInfoInput = baseInput2 + "imdb_basic_output.txt"
  val imdbAdditionInfoInput = baseInput2 + "imdb_content_output.txt"
  val wikiBasicInfoInput = baseInput2 + "wiki_basic_output.txt"
  val wikiAdditionInfoInput = baseInput2 + "wiki_addition_output.txt"


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Socialinfo")
      .setMaster("local[2]")
      .set("spark.executor.memory", "2g")
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

    val twitterData = spark.read.json(twitterProfileInput).collect()
    val tweetsData = spark.read.json(tweetsInput).collect()
    val relationsData = sc.textFile(relationFile).map(_.split(" ")).collect()

    val wikiData = basicWiki.join(additionalWiki, Seq("id"))
    val wikiCollection = wikiData.collect()

    val imdbData = basicImdb.join(imdbAddition, Seq("id"))
    val imdbCollection = imdbData.collect()


    // Binding all the information
    val profiles = ranks.map(x => {

      var imdbProfile: IMDBProfile = null
      var wikiProfile: WikiProfile = null
      var twitterProfile: TwitterProfile = null


      // IMDB Profile
      val imdb = imdbCollection.filter(a => a(0).equals(x._1.toString))
      if (imdb.length > 0) {
        imdb.map(a => {
          imdbProfile = new IMDBProfile(
            id = a(0).toString,
            name = a(4).toString,
            birthday = a(1).toString,
            death = a(3).toString,
            children = a(2).toString.toInt,
            occupation = a(5).asInstanceOf[mutable.WrappedArray[String]].toList,
            spouse = a(6).asInstanceOf[mutable.WrappedArray[String]].toList,
            content = a(7).toString
          )
        })

      }

      // Wikipedia Profile
      val wiki = wikiCollection.filter(a => a(0).equals(x._1.toString))
      if (wiki.length > 0) {
        wiki.map(a => {
          wikiProfile = new WikiProfile(
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
        })
        val test = ""
      }


      // Twitter Profile
      val twitter = twitterData.filter(a => a.getAs("id").equals(x._1.toString))

      if (twitter.length > 0) {
        val tweets = tweetsData.filter(a => a.getAs("twitterId").equals(x._1.toString)).map(a => new Tweet(a.getAs("id").toString, a.getAs("date").toString, a.getAs("message").toString)).toList
        val following = relationsData.filter(a => a.length > 1).filter(a => a(0).equals(x._1.toString)).flatMap(a => a).filter(a => !a.equals(x._1.toString)).toList
        val followers = relationsData.filter(a => a.length > 1).filter(a => a(1).equals(x._1.toString)).flatMap(a => a).filter(a => !a.equals(x._1.toString)).toList


        twitter.map(a => {
          twitterProfile = new TwitterProfile(
            id = a.getAs("id"),
            name = a.getAs("name"),
            nickname = a.getAs("nickname"),
            followers = followers,
            following = following,
            tweets = tweets
          )
        })
        val test = ""
      }

      //createFinalProfile(imdbProfile, wikiProfile, twitterProfile)

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

  def writeProfileToDatabas(profile: Profile): Unit = {
    ???
  }

  def createFinalProfile(imdb: IMDBProfile, wiki: WikiProfile, twitter: TwitterProfile): Profile = {
    ???
  }


}
