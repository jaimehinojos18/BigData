package Menus
import User.Customer
import scala.io.StdIn
import DataBase.accounts
object homeMenu {
  val connection = accounts
  def home(i:Int, id:Long):Int = i match{
    case 0 => {print("Goodbye!")
      connection.closeConnection()
      0
    }
    case 1 => {
      print("What type of account would you like to open?\n1. Checking Account\n2. Savings Account")
      var answer = false
      while(answer != true){
        print("\nAction: ")
        try{
          var input = StdIn.readLine().toInt
          if(input == 1){
            connection.createAccount(id, "Checking")
            answer = true
          }else if(input == 2){
            connection.createAccount(id, "Savings")

            answer = true
          }else{
            println("Wrong input try again")
          }

        }catch{
          case e:Exception => println("Input type not valid")
        }
      }
      print("MTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }
    case 2 => {
      val accounts = connection.getAccounts(id)
      var count = 1
      println("\n\nAvailable accounts:")
      for( x <- accounts ){
        print(s"$count. ${x(0)} ${x(1)} Account"+"\n")
        count+=1
      }
      print("Please select an account: ")
      print("\nMTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }
    case 3 => {print("MTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }
    case 4 => {print("MTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }
    case _ => {print("MTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }

  }
}
