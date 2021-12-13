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
      print("\n\nMTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }
    case 2 => {
      val accounts = connection.getAccounts(id)
      if(accounts != null){
        var count = 1
        println("\n\nAvailable accounts:")
        for( x <- accounts ){
          print(s"$count. ${x(0)} ${x(1)} Account"+"\n")
          count+=1
        }
        print("Please select an account: ")
        var jump = false
        var input = ""
        var account_selected:Array[String] = Array()
        while(jump != true){
          try{
            input = StdIn.readLine()
            account_selected = accounts(input.toInt-1)
            jump = true
          }catch{
            case e:Exception=> println("Wrong Input")
          }
        }
        print(s"${account_selected(1)} Account ${account_selected(0)}  Options: \n1. Print Statement" +
          s"\n2. View Account Information\n3. Delete Account\n0. Main Menu\nPlease select an option: ")
        jump = false
        while(jump!=true){
          input = StdIn.readLine()
          if(input.equals("1")){
            println("print statement")
            jump = true

          }
          else if(input.equals("2")){
            println("\n\nAccount Info")
            connection.printAccountInfo(account_selected(0).toLong)
            jump = true
          }
          else if(input.equals("3")){
            println("Delete account")
            connection.deleteAccount(account_selected(0).toLong)
            jump = true
          }
          else if(input.equals("0")){
            println("Main Menu")
            jump = true
          }else{
            print("\nNot a menu option.\nOptions: \n1. Print Statement\n2. View Account Info\n3. Delete Account\n0. Main Menu\nPlease select an option: ")
          }
        }
        Thread.sleep(4000)
      }else{
        println("Back to main menu")
      }


      print("\n\nMTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }
    case 3 => {
      val accounts = connection.getAccounts(id)
      if(accounts != null){
        var count = 1
        println("\n\nAvailable accounts:")
        for( x <- accounts ){
          print(s"$count. ${x(0)} ${x(1)} Account"+"\n")
          count+=1
        }
        print("Please select an account: ")
        var jump = false
        var input = ""
        var account_selected:Array[String] = Array()
        while(jump != true){
          try{
            input = StdIn.readLine()
            account_selected = accounts(input.toInt-1)
            jump = true
          }catch{
            case e:Exception=> println("Wrong Input")
          }
        }

        jump = false
        while(jump!= true){
          try{
            print("Amount to Deposit: $")
            input = StdIn.readLine()
            connection.depositIntoAccount(account_selected(0).toLong, input.toDouble)
            jump = true
          }catch{
            case e:Exception=> println("Error Ocurred")
          }
        }

      }
      else{
        println("Back to main menu")
      }
      print("\n\nMTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }
    case 4 => {print("\n\nMTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }
    case _ => {print("\n\nMTH Bank \n1. Open an Account \n2. Manage Accounts\n3. Deposit\n4. Send/Transfer Money\n0. Sign Out" +
      "\nPlease select an option: ")
      1
    }

  }
}
