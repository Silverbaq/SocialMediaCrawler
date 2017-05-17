import org.apache.spark.sql.SparkSession
import scala.slick.session.Database
import scala.slick.jdbc.StaticQuery
import scala.slick.jdbc.meta.MTable

/**
  * Created by silverbaq on 5/17/17.
  */

class SQLite(name:String) {

  val sparkSession = SparkSession.builder.
    master("local")
    .appName("spark session example")
    .getOrCreate()

  val df = sparkSession.sqlContext.read.format("jdbc").options(
    Map(
      "url" -> "jdbc:sqlite:/home/silverbaq/Documents/BJTU/Project2/twitter-stmo/db.sqlite",
      "user" -> "x",
      "password" -> "x",
      "dbtable" -> "(select * from pg_tables) as t")).load()

  def connect(): Unit ={

  }

  def insert(): Unit ={

  }

}