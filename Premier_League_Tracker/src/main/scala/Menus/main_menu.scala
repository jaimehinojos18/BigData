package Menus
import Database.spark_connect
import scala.io.StdIn.readLine
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

        user(0, 0, sp)
      }else{
        println("Team Stats.")
        sp.get_stats(team_id)
      }
      Thread.sleep(4000)
      user(0, 0, sp)
    }
    case 3 => {
      if(team_id == -1){
        println("You have no favorite team selected.")

      }else{
          sp.get_team_players(team_id)
      }
      Thread.sleep(4000)
      user(0, 0, sp)

    }
    case 4 => {
      println("\n")
      sp.champions_league()
      Thread.sleep(4000)
      user(0, 0, sp)
    }
    case 5 => {
      println("\nTeams currently in the Relegation Zone")
      sp.relegation_zone()
      Thread.sleep(4000)
      user(0, 0, sp)
    }
    case 6 => {}
    case _ =>{
      println("\n____________________________________________________________________\nPL Tracker Main Menu:\n" +
        "1. View league table\n" +
        "2. View favorite team statistics\n" +
        "3. View favorite team players\n" +
        "4. View teams currently qualified for Champions League\n" +
        "5. View teams in the relegation zone\n" +
        "c. Select new favorite team\n" +
        "m. Manage Account\n" +
        "q. Sign out\n" +
        "What would you like to do? ")
    }
  }
  def manage_user(i:Int, u_id:Int,sp:spark_connect.type): Unit = i match{
    case 1 => {
      print("Enter Password:")
      val passw = readLine()
      print("\nEnter New Password:")
      val newpass = readLine()
      print("Confirm New Password:")
      val newpass2 = readLine()

      if(newpass.equals(newpass2)){
        sp.change_password(u_id, passw, newpass)
      }
      else{
        println("Passwords didn't match")
      }
      manage_user(0, u_id, sp)
    }
    case 2 => {
      print("Enter New Username:")
      val user = readLine()
      if(user.equals("") || user == null){
        "Nothing was entered"
      }else{
        sp.change_username(u_id, user)
      }
      manage_user(0, u_id, sp)
    }
    case 3 =>{
      println("Are you sure you want to delete your account?(y/n)")
      val ans = readLine()
      if(ans.equals("y")){
        println("Account deleted")
        sp.delete_account(u_id)
        System.exit(0)
      }else{
        println("Operation cancelled")
        manage_user(0, u_id, sp)
      }
    }
    case _ => {
      println("\n____________________________________________________________________\nManage Menu:\n" +
        "1. Change Password\n" +
        "2. Update Username\n" +
        "3. Delete Account\n" +
        "b. Back to main menu\n" +
        "What would you like to do? ")
    }

  }


}
