package DataBase
import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import User.Customer

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.StdIn
import scala.util.Random
object accounts {
  val driver = "com.mysql.cj.jdbc.Driver"
  val url = "jdbc:mysql://localhost:3306/mth_bank"
  val username = "root"
  val password = "pss1119"
  var connection:Connection = DriverManager.getConnection(url, username, password)
  val statement = connection.createStatement()

  def createAccount(id:Long ,s:String): Unit ={
    try{
      val resultSet = statement.executeQuery(s"SELECT * FROM accounts WHERE ID = '$id' AND type ='$s';")
      if(!resultSet.isBeforeFirst()){
        val code= (100000 + Random.nextInt(900000)).toString()
        statement.executeUpdate("INSERT INTO accounts (account_number, type, ID, account_balance)" +
          s"VALUES('$code','$s', '$id', '0.0');")
        print("Account Successfully Created")
      }else{
        println(s"You already have a $s account.")
      }
    }catch{
      case e:Exception => println("Error")
    }
  }
  def printAccountInfo(id:Long): Unit = {
      try{
        var resultSet = statement.executeQuery(s"SELECT * FROM accounts WHERE account_number = '$id';")
        while ( resultSet.next() ) {
          println(s"Account Number: ${resultSet.getString(1)}" +
            s"\nAccount Type: ${resultSet.getString(2)}" +
            s"\nAccount Balance: ${resultSet.getString(4)}")
        }
        var resultSet2 = statement.executeQuery(s"SELECT * FROM transactions WHERE account_id = '$id';")
        if(!resultSet2.isBeforeFirst()){
          println("You don't have any transactions")

        }else{
          println("Transactions")
          println("Transaction Number, Transaction Type, Transaction Amount, Transaction Date Format")
          while ( resultSet2.next() ) {
            println(s"${resultSet2.getString(1)}                    " +
              s"${resultSet2.getString(6)}              " +
              s"$$${resultSet2.getString(4)}               " +
              s"${resultSet2.getString(5)}            ")
          }
        }
      }catch{
        case e:Exception => println("Error occurred")
      }
  }
  def depositIntoAccount(id:Long, amount:Double): Unit = {
    try{
      var resultSet = statement.executeQuery(s"SELECT account_balance FROM accounts WHERE account_number = '$id';")
      resultSet.next()
      var balance = resultSet.getString("account_balance").toDouble
      balance += amount
      statement.executeUpdate(s"UPDATE accounts SET account_balance = '$balance' WHERE account_number = '$id';")
      statement.executeUpdate(s"INSERT INTO transactions (account_id, amount, transaction_type) VALUES ('$id', '$amount', 'Deposit');")
      println("Money has been deposited")

    }catch{
      case e:Exception => println("Error occurred")
    }

  }
  def transfer(): Unit ={

  }
  def getAccounts(id:Long):ArrayBuffer[Array[String]]= {
    try{
      val resultSet = statement.executeQuery(s"SELECT * FROM accounts WHERE ID = '$id';")
      if(!resultSet.isBeforeFirst()){
        print("You have no accounts opened")
        null
      }
      else{
        val accounts = ArrayBuffer[Array[String]]()
        while ( resultSet.next() ) {
          val a_n = resultSet.getString(1)
          val a_t = resultSet.getString(2)
          accounts.append(Array[String](a_n,a_t))
        }
        accounts
      }
    }catch{
      case e:Exception => {println("Error")
        null
      }
    }
  }
  def deleteAccount(id:Long): Unit ={
    try{
      var input = ""
      println("Are you sure you want to delete this account(Y/N)? Any outstanding balance and information will be lost. ")
      while(!input.equals("N")){
        input = StdIn.readLine()
        if(input.equals("Y")){
          statement.executeUpdate(s"DELETE FROM accounts WHERE account_number = '$id';")
          input = "N"
        }else if(input.equals("N")){
          println("Going back to main menu.")
        }else{
          print("Wrong input, try again: ")
        }
      }

    }catch{
      case e: Exception => println("Error Ocurred")
    }
  }

  def closeConnection(): Unit ={
    connection.close()
  }
}
