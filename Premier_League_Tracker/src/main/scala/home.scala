import ujson.Value
import scala.io.StdIn.readLine
import java.io.{File, PrintWriter}
import scala.collection.mutable.Map
import User.Customer
import Menus.sign_in
import Menus.main_menu
import Database.spark_connect
object home {
  def main(args:Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:/hadoop")
    val spark = spark_connect
    spark.initiate()
    //spark.insert_player()
    /*
    //spark.drop()

    //spark.change_team()
    println("created spark session")
    println("Welcome to")
    println("|||||||||     ||               |||||||||||||   ||||||||||          ||        ||||||||  ||    ||  ||||||  ||||||||||  " )
    println("||     ||     ||                    |||        ||       ||        ||||       ||        ||   ||   ||      ||       || ")
    println("||     ||     ||                    |||        ||       ||       ||  ||      ||        ||  ||    ||      ||       || ")
    println("|||||||||     ||                    |||        ||||||||||       ||||||||     ||        ||||      ||||    ||||||||||  ")
    println("||            ||                    |||        ||   |||        ||      ||    ||        ||  ||    ||      ||   |||    ")
    println("||            ||                    |||        ||     |||     ||        ||   ||        ||   ||   ||      ||    |||   ")
    println("||            ||||||||||||          |||        ||      |||   ||          ||  ||||||||  ||    ||  ||||||  ||     |||  ")
    val sI = sign_in
    var si_in:Customer = sI.signInMenu(0, spark)
    while(si_in == null){
      try{
        var input = readLine()
        if(input.equals("q")){
          println("Goodbye")
          System.exit(0)
        }else {
          si_in = sI.signInMenu(input.toInt, spark)
        }
      }catch{
        case e: Exception => println("Please input correct number")
      }
    }
    println(s"Welcome ${si_in.getFirstName()} ${si_in.getLastName()}!")
    if(si_in.getTeamId() == -1){
      var res = false
      while(res == false){
        try{
          println("You have not selected your team, would you like to choose? (y/n)")
          var input = readLine()
          println("\n\n\n")
          if(input.equals("y")){
            println("Here is the list of teams:")
            var res2 = false
            while(res2==false){
              try{
                spark.get_teams()
                print("Please input the ID number of the team, input -1 to cancel Ex(To select Tottenham input 47):")
                var input2 = readLine()
                if(input2.equals("-1")){
                  println("You can choose your team at any time.")
                  res2 = true
                  res = true
                }else{
                 val change = spark.change_team(input2.toInt, si_in.getId())
                  if(change == true){
                    si_in.setTeam(input2.toInt)
                    res2 = true
                    res = true
                  }else{
                    print("Wrong input try again")
                  }
                }
              }catch{
                case e:Exception => println("Wrong input, try again")
              }
            }

          }
          else if(input.equals("n")){
            res = true
          }else{
            println("Wrong input try again")
          }
        }catch{
          case e:Exception => println("Wrong input try again")
        }
      }
    }
    if(si_in.getUser().equals("admin")){
      main_menu.admin()
    }else{
      try{
        var res = ""
        main_menu.user(0, si_in.getTeamId(), spark)
        while(!res.equals("q")){
          res = readLine()
          if(res.equals("q")){
            println("Goodbye")
          }else{
            main_menu.user(res.toInt, si_in.getTeamId(), spark)
          }
        }
      }catch{
        case e:Exception => println("Wrong input try again.")
      }

    }





     */


  }
}
