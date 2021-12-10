import MTH_interface._
import User._
import Menus._
import scala.io.StdIn.readLine


object MTH_app extends MTH_Interface {
  def main(ags:Array[String]): Unit ={
    println("Welcome to MTH banking!")
    val sI = signIn
    var customer:Customer = sI.signInMenu(0)
    while (customer == null){
        try{
          var input = readLine()
          if(input.equals("q")){
            println("Goodbye")
            System.exit(0)
          }else {
            customer = sI.signInMenu(input.toInt)
          }
        }catch{
          case e: Exception => println("Please input correct number")
        }
    }
    println(s"Welcome ${customer.getFirstName()} ${customer.getLastName()} what can we help you with today?")
    val home = homeMenu
    var status:Int = home.home(11, customer.getId())
    while(status != 0){
      try{
        var input = readLine()
        status = home.home(input.toInt, customer.getId())
      }catch{
        case e: Exception => println("Please input correct number")
      }
    }
  }

}
