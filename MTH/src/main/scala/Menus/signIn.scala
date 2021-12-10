package Menus
import User.Customer
import DataBase.Customer_Connection
import scala.io.StdIn
object signIn {

  def signInMenu(i:Int): Customer =i  match{
    case 1 => {
      println("Please enter the following information: ")

      print("First Name: \n")
      val name = StdIn.readLine()
      print("Last Name: \n")
      val lastname = StdIn.readLine()
      print("Username: \n")
      val username = StdIn.readLine()
      print("Password: \n")
      val password = StdIn.readLine()
      val connect = Customer_Connection
      connect.createAccount(name, lastname, username, password)
      println("\nMenu: ")
      println("1. Create Account \n2. Login \nq. quit\n " +
        "Please type what you would like to do Ex(To Create account input 1): ")

      null
    }
    case 2 => {
      val connect = Customer_Connection
      print("Please enter your username: ")
      val username = StdIn.readLine()
      print("\nPlease enter your password: ")
      val password = StdIn.readLine()
      val result = connect.signIn(username, password)
      if(result == null){
        println("\nMenu: ")
        println("1. Create Account \n2. Login \nq. quit\n " +
          "Please type what you would like to do Ex(To Create account input 1): ")
        null
      }else {
        connect.closeConnection()
        result
      }
    }
    case _ => {
      println("\nMenu: ")
      println("1. Create Account \n2. Login \nq. quit\n " +
        "Please type what you would like to do Ex(To Create account input 1): ")
      null
    }

  }


}
