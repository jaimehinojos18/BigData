package Database
import org.apache.spark.sql.SparkSession
import User.Customer
import API.API

import java.io.FileNotFoundException
object spark_connect {
  System.setProperty("hadoop.home.dir", "C:/hadoop")
  val spark = SparkSession
    .builder
    .appName("hello hive")
    .config("spark.master", "local")
    .enableHiveSupport()
    .getOrCreate()
  spark.conf.set("hive.exec.dynamic.partition.mode", "nonstrict")
  spark.sparkContext.setLogLevel("ERROR")
  def initiate(): Unit = {
    //val api = API
    //api.get("https://api-football-v1.p.rapidapi.com/v3/standings?season=2021&league=39", "PLTable")
    //api.get("https://api-football-v1.p.rapidapi.com/v3/players?league=39&season=2021", "PLPlayers")

    spark.sql("create table IF NOT EXISTS users (id int, username string, password string, firstName string, lastName string, team_id int, type string) row format delimited fields terminated by ','")
    spark.sql("drop table if exists PL_Table")
    spark.sql("create table IF NOT EXISTS PL_Table(id Int, rank int,team String, played int, win int, draw int, loss int, goalsFor int, goalsAgainst int, home_played int, home_win int, home_draw int, " +
      "home_loss int, hg_for int, hg_against int," +
      " away_played int, away_win int, away_draw int, away_loss int, ag_for int, ag_against int, points int) row format delimited fields terminated by ','")

    try{
      val input_file ="C:/Users/ghost/IdeaProjects/Premier_League_Tracker/src/main/Files/PLTable.json"
      val json_content = scala.io.Source.fromFile(input_file).mkString
      //print(table.mkString)

      val json_data = ujson.read(json_content)
      var trav = json_data("response")(0)("league")("standings")(0)
      var array = trav.arr.size
      //var obj = ujson.read(trav)
      for(i <- 0 to array - 1){
        spark.sql(s"INSERT INTO PL_TABLE VALUES('${trav(i)("team")("id")}', '${trav(i)("rank")}','${trav(i)("team")("name")}', '${trav(i)("all")("played")}', '${trav(i)("all")("win")}', '${trav(i)("all")("draw")}', '${trav(i)("all")("lose")}', '${trav(i)("all")("goals")("for")}', '${trav(i)("all")("goals")("against")}'" +
          s", '${trav(i)("home")("played")}', '${trav(i)("home")("win")}', '${trav(i)("home")("draw")}', '${trav(i)("home")("lose")}', '${trav(i)("home")("goals")("for")}', '${trav(i)("home")("goals")("against")}', '${trav(i)("away")("played")}'" +
          s", '${trav(i)("away")("win")}', '${trav(i)("away")("draw")}', '${trav(i)("away")("lose")}', '${trav(i)("away")("goals")("for")}', '${trav(i)("away")("goals")("against")}', '${trav(i)("points")}')")
      }

    }
    catch{
      case e:FileNotFoundException => println("File not found")
      case _:Exception => println("Error")
    }
    spark.sql("drop table if exists Players")
    spark.sql("drop table if exists Players_temp")
    spark.sql("create table IF NOT EXISTS Players_temp(player_id int, name string, nationality string, played int, minutes int," +
      "position string, shots int, goals int, goals_conceded int, assists int, passes int, key_passes int, accuracy int," +
      "y_cards int, r_cards int, id int) row format delimited fields terminated by ','")

    val teams = spark.sql("SELECT id FROM PL_Table").collect()
    teams.foreach(x => spark.sql(s"LOAD DATA LOCAL INPATH 'C:/Users/ghost/IdeaProjects/Premier_League_Tracker/src/main/Files/Players${x.get(0).toString}.csv' INTO TABLE Players_temp"))

    spark.sql("drop table if exists Players")
    spark.sql("create table IF NOT EXISTS Players(player_id int, name string, nationality string, played int, minutes int," +
      "position string, shots int, goals int, goals_conceded int, assists int, passes int, key_passes int, accuracy int," +
      "y_cards int, r_cards int) PARTITIONED BY (id int) row format delimited fields terminated by ','")
    spark.sql("INSERT OVERWRITE TABLE Players SELECT * FROM Players_temp")
    spark.sql("drop table if exists Players_temp")






  }
  def create_account(id:Int, firstName:String, lastName:String, username:String, pass:String) = {
    try{
      var response = spark.sql(s"SELECT * FROM users WHERE username = '$username'").collect()
      if(response.length == 0){
        spark.sql(s"INSERT INTO users VALUES('$id', '$username', '$pass','$firstName', '$lastName', '-1', 'user')")
        println("Account successfully created")
      }else{
        println("username already exists")
      }
    }catch{
      case e: Exception => print("Error")
    }


  }
  def sign_in(username:String, pass:String):Customer = {
    try{
      var response = spark.sql(s"SELECT * FROM users WHERE username = '$username' AND password = '$pass'").collect()
      if(response.length == 1){
        var id = 0
        var fn = ""
        var ln = ""
        var team_id = 0
        var user_t = ""

        response.foreach(row => {
          id = row.get(0).toString.toInt
          fn = row.get(3).toString
          ln = row.get(4).toString
          team_id = row.get(5).toString.toInt
          user_t = row.get(6).toString

        })

        new Customer(fn, ln, id, team_id, user_t)
      }else{
        println("User not found")
        null
      }
    }catch{
      case e: Exception => print("Error")
        null
    }


  }
  def drop(): Unit ={
    spark.sql("drop table if exists users")
  }
  def change_team(id:Int , u_id:Int): Boolean ={
    try{
      val res = spark.sql(s"SELECT id FROM PL_Table WHERE id = '$id'").collect()
      if(res.length == 1){
        val user = spark.sql(s"SELECT * FROM users WHERE id = '$u_id'").collect()
        spark.sql(s"INSERT OVERWRITE TABLE users SELECT * FROM users WHERE id!='$u_id'")
        user.foreach(row =>{
          spark.sql(s"INSERT INTO users VALUES('${row.get(0).toString}', '${row.get(1).toString}','${row.get(2).toString}', '${row.get(3).toString}', '${row.get(4).toString}', '$id', '${row.get(6).toString}')")

        })
        spark.sql("SELECT * FROM users").show()
        true
      }else{
        false
      }
    }catch{
      case e:Exception => {println("Error occured")
        false}
    }


    /*
    spark.sql("INSERT INTO users VALUES('789706', 'jahinojos2','pss1119', 'Jaime', 'Hinojos', '-1', 'user')")
    spark.sql("SELECT * FROM users").show()

    spark.sql("INSERT INTO users VALUES('789706', 'jahinojos2','pss1119', 'Jaime', 'Hinojos', '19', 'user')")
    spark.sql("SELECT * FROM users").show()

     */
  }
  def get_teams(): Unit = {
    val response = spark.sql("SELECT id, team from PL_Table").collect()
    response.foreach(row => { println(s"${row.get(0).toString}. ${row.get(1).toString}")
    })

  }
  def get_table(): Unit ={
    spark.sql("SELECT rank as Position, team as Team, points as Pts, played as P, win as W, draw as D, loss as L FROM PL_Table ORDER BY Position").show()
  }
  def insert_player(): Unit ={
    val teams = spark.sql("SELECT id FROM PL_Table").collect()
    teams.foreach(x => API.getPlayers(x.get(0).toString.toInt))
  }


}
