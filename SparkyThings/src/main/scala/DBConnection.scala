/**
  * Created by silverbaq on 6/5/17.
  */

import java.sql.{Connection, DriverManager}

object DBConnection {
  // connect to the database named "mysql" on port 3306 of localhost
  val url = "jdbc:mysql://localhost:3306/mysql"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "123456"
  var connection: Connection = _




  def selectTest: Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT host, user FROM mysql.user") // this is for testing purpose, but we will change it to suit our purpose
      while (rs.next) {
        val host = rs.getString("host")
        val user = rs.getString("user")
        println("host = %s, user = %s".format(host, user))
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
  }

  def test2(): Unit ={

    val id1= "123123123123123"
    val id2= "123123123123123"

    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeUpdate("INSERT INTO `Persona`.`Follower`(`FollowerID`,`FollowingID`)" +
        "VALUES('"+id1+"','"+id2+"');")



    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
  }


  def writeProfile(profile: Profile): Unit = {
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)


    //DBConnection.insertProfile(profile.id, profile.name, "", profile.birthday, "", profile.father, profile.mother, profile.children, profile.death)
   //DBConnection.insertSocialNetworkStatus(profile.id, profile.pageRank)

    //profile.spouse.foreach(s => DBConnection.insertSpouce(profile.id, s))
    //profile.following.foreach(f => DBConnection.insertFollower(profile.id, f))
    profile.following.foreach(f => DBConnection.insertFollower2(profile.id, f))
    //profile.followers.foreach(f => DBConnection.insertFollower(f, profile.id))
    //profile.images.foreach(i => DBConnection.insertPicture(profile.id, i))
    //profile.occupation.foreach(o => DBConnection.insertOccupation(profile.id, o))
    //profile.refs.foreach(r => DBConnection.insertReference(profile.id, r))
    //profile.tweets.foreach(t => DBConnection.insertTweet(profile.id, t.message, t.date))
    println("Have inserted one")

    connection.close()

  }


  def insertProfile(profileId: String, name: String, nickname: String, birthdate: String, gender: String, father: String, mother: String,
                    children: Int, deathDate: String): Unit = {

    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {

      val ps = connection.prepareStatement("INSERT INTO `Persona`.`Profile`" +
        "(`ProfileID`,`Name`,`BirthDate`,`Gender`,`Father`,`Mother`,`Children`,`DeathDate`,`NickName`)" +
        "VALUES(?,?,?,?,?,?,?,?,?);")

      ps.setString(1, profileId)
      ps.setString(2, name)
      ps.setString(3, birthdate)
      ps.setString(4, gender)
      ps.setString(5, father)
      ps.setString(6, mother)
      ps.setInt(7, children)
      ps.setString(8, deathDate)
      ps.setString(9, nickname)
      ps.executeUpdate()

    } catch {
      case e: Exception => e.printStackTrace
    }

  }

  def insertFollower(followerID: String, followingID: String): Unit = {

    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {


      val ps = connection.prepareStatement("INSERT INTO `Persona`.`Follower`(`FollowerID`,`FollowingID`)" +
        "VALUES(?,?);")

      ps.setString(1, followerID)
      ps.setString(2, followingID)
      ps.executeUpdate()

    } catch {
      case e: Exception => e.printStackTrace
    }

  }

  def insertFollower2(followerID: String, followingID: String): Unit = {

    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {
      val statement = connection.createStatement

      val ps = statement.executeUpdate("INSERT INTO `Persona`.`Follower`(`FollowerID`,`FollowingID`)" +
        "VALUES('"+ followerID +"','"+ followingID +"');")


    } catch {
      case e: Exception => e.printStackTrace
    }

  }

  def insertInfoContent(profileId: String, contentInfo: String): Unit = {
    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {
      val ps = connection.prepareStatement("INSERT INTO `Persona`.`InfoContent`(`ProfileID`,`ContentInfo`)" +
        "VALUES(?,?);")

      ps.setString(1, profileId)
      ps.setString(2, contentInfo)
      ps.executeUpdate()

    } catch {
      case e: Exception => e.printStackTrace
    }

  }

  def insertOccupation(profileId: String, occupation: String): Unit = {
    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {


      val ps = connection.prepareStatement("INSERT INTO `Persona`.`ProfileOccupation`(`Occupation`,`ProfileID`)" +
        "VALUES(?,?);")

      ps.setString(2, profileId)
      ps.setString(1, occupation)
      ps.executeUpdate()

    } catch {
      case e: Exception => e.printStackTrace
    }

  }

  def insertPicture(profileId: String, link: String): Unit = {
    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {
      val ps = connection.prepareStatement("INSERT INTO `Persona`.`Pictures`(`ProfileID`,`Link`)" +
        "VALUES(?,?);")

      ps.setString(1, profileId)
      ps.setString(2, link)
      ps.executeUpdate()

    } catch {
      case e: Exception => e.printStackTrace
    }

  }

  def insertSocialNetworkStatus(profileId: String, pagerank: Double): Unit = {
    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {

      val ps = connection.prepareStatement("INSERT INTO `Persona`.`SocialNetworkStatus`(`ProfileID`,`PageRank`)" +
        "VALUES(?,?);")

      ps.setString(1, profileId)
      ps.setDouble(2, pagerank)
      ps.executeUpdate()

    } catch {
      case e: Exception => e.printStackTrace
    }

  }

  def insertTweet(profileId: String, tweet: String, postDate: String): Unit = {
    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {
      val ps = connection.prepareStatement("INSERT INTO `Persona`.`Tweets`(`TweetContent`,`ProfileID`,`PostDate`)" +
        "VALUES(?,?,?);")

      ps.setString(1, tweet)
      ps.setString(2, profileId)
      ps.setString(3, postDate)
      ps.executeUpdate()

    } catch {
      case e: Exception => e.printStackTrace
    }

  }

  def insertReference(profileId: String, link: String): Unit = {
    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {
      val ps = connection.prepareStatement("INSERT INTO `Persona`.`References`(`ProfileId`,`Url`)" +
        "VALUES(?,?);")

      ps.setString(1, profileId)
      ps.setString(2, link)
      ps.executeUpdate()

    } catch {
      case e: Exception => e.printStackTrace
    }

  }

  def insertSpouce(profileId: String, name: String): Unit = {
    if (connection.isClosed) {
      connection = DriverManager.getConnection(url, username, password)
    }
    try {
      val ps = connection.prepareStatement("INSERT INTO `Persona`.`Spouce`(`ProfileId'`,`Name`)" +
        "VALUES(?,?);")

      ps.setString(1, profileId)
      ps.setString(2, name)
      ps.executeUpdate()

    } catch {
      case e: Exception => e.printStackTrace
    }

  }


}