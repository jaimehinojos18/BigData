package Menus
import Database.spark_connect
object main_menu {
  def admin(): Unit ={

  }
  def user(i:Int, team_id:Int, sp:spark_connect.type ): Unit = i match {
    case 1 => {
      println("\n\nPremier League Table")
      sp.get_table()
      Thread.sleep(4000)
      user(0, 0, sp)
    }
    case 2 => {
      if(team_id == -1){
        println("You have no favorite team selected.")
        Thread.sleep(4000)
        user(0, 0, sp)
      }else{

      }
    }
    case 3 => {
      if(team_id == -1){
        println("You have no favorite team selected.")
        Thread.sleep(4000)
        user(0, 0, sp)
      }else{

      }

    }
    case 4 => {}
    case 5 => {}
    case 6 => {}
    case _ =>{
      println("\n\nPL Tracker Main Menu:\n" +
        "1. View League Table\n" +
        "2. View Favorite team statistics\n" +
        "3. View Favorite team players\n" +
        "4. View teams Currently qualified for Champions League\n" +
        "5. View teams in the relegation zone\n" +
        "6. Select new favorite team\n" +
        "q. Sign out\n" +
        "What would you like to do? ")
    }
  }


}
