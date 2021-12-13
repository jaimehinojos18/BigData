package DataBase
import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import User.Customer
import Tools.Print_statements

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
        var resultSet2 = statement.executeQuery(s"SELECT * FROM transactions WHERE account_id = '$id' ORDER BY transaction_date DESC;")
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
        var resultSet4 = statement.executeQuery(s"with deposits as(Select account_id as Account_Number, sum(amount) as Deposits from transactions WHERE account_id = '$id' AND transaction_type='Deposit'), transfers_in as(Select receiving_account as Account_Number, sum(amount) as Transfers_In from transactions WHERE receiving_account = '$id' AND transaction_type='Transfer') Select deposits.Account_Number, coalesce(deposits.Deposits, 0) + coalesce(transfers_in.Transfers_In, 0) as Money_In FROM deposits LEFT JOIN transfers_in ON deposits.Account_Number = transfers_in.Account_Number;")
        if(!resultSet4.isBeforeFirst()){
          println("You don't have any transactions")

        }else{

          while ( resultSet4.next() ) {
            print("Money In: ")
            println(s"$$${resultSet4.getString(2)}")
          }
        }

        var resultSet3 = statement.executeQuery(s"Select account_id as Account_Number, coalesce(sum(amount), 0) as Transfers from transactions WHERE account_id = '$id' AND transaction_type='Transfer';")
        if(!resultSet3.isBeforeFirst()){
          println("You don't have any transactions")

        }else{

          while ( resultSet3.next() ) {
            print("Money out: ")
            println(s"$$${resultSet3.getString(2)}")
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
  def transfer(account:Long, transferTo:Long, amount:Double): Unit ={
      try{
        var resultSet = statement.executeQuery(s"SELECT account_balance FROM accounts WHERE account_number ='$transferTo';")

        if (!resultSet.isBeforeFirst()){
          println("Account does not exist. ")
        }else{
          resultSet.next()
          var transferBalance = resultSet.getString("account_balance").toDouble
          var resultSet2 = statement.executeQuery(s"SELECT account_balance FROM accounts WHERE account_number = '$account';")
          resultSet2.next()
          var accountBalance = resultSet2.getString("account_balance").toDouble

          if(accountBalance < amount){
            println("You do not have sufficient funds.")
          }else{
            transferBalance += amount
            accountBalance -= amount
            statement.executeUpdate(s"UPDATE accounts SET account_balance = '$transferBalance' WHERE account_number = '$transferTo';")
            statement.executeUpdate(s"UPDATE accounts SET account_balance = '$accountBalance' WHERE account_number = '$account';")
            statement.executeUpdate(s"INSERT INTO transactions (account_id, receiving_account, amount, transaction_type) VALUES ('$account','$transferTo','$amount', 'Transfer');")
            println("Transfer was completed. ")
          }


        }

      }catch{
        case e:Exception =>{ print("Error occured")}
      }
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
  def print_statement(id:Long): Unit ={
    var transactions = new ListBuffer[Array[String]]()
    try{
      var resultSet = statement.executeQuery(s"SELECT * FROM accounts WHERE account_number = '$id';")
      var account:Array[String] = Array[String]()
      while ( resultSet.next() ) {
        account = Array[String](resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4))
      }
      var resultSet2 = statement.executeQuery(s"SELECT * FROM transactions WHERE account_id = '$id' ORDER BY transaction_date DESC;")
      if(!resultSet2.isBeforeFirst()){
        transactions += Array(" ")

      }else{
        while ( resultSet2.next() ) {
          transactions += Array[String](resultSet2.getString(1), resultSet2.getString(2), resultSet2.getString(3), resultSet2.getString(4), resultSet2.getString(5), resultSet2.getString(6))
        }
      }
      var resultSet4 = statement.executeQuery(s"with deposits as(Select account_id as Account_Number, sum(amount) as Deposits from transactions WHERE account_id = '$id' AND transaction_type='Deposit'), transfers_in as(Select receiving_account as Account_Number, sum(amount) as Transfers_In from transactions WHERE receiving_account = '$id' AND transaction_type='Transfer') Select deposits.Account_Number, coalesce(deposits.Deposits, 0) + coalesce(transfers_in.Transfers_In, 0) as Money_In FROM deposits LEFT JOIN transfers_in ON deposits.Account_Number = transfers_in.Account_Number;")
      var moneyIn = ""
      if(!resultSet4.isBeforeFirst()){
        println("You don't have any transactions")

      }else{

        while ( resultSet4.next() ) {
          moneyIn = resultSet4.getString(2)
        }
      }

      var resultSet3 = statement.executeQuery(s"Select account_id as Account_Number, coalesce(sum(amount), 0) as Transfers from transactions WHERE account_id = '$id' AND transaction_type='Transfer';")
      var moneyOut = ""
      if(!resultSet3.isBeforeFirst()){
        println("You don't have any transactions")

      }else{

        while ( resultSet3.next() ) {
          moneyOut = resultSet3.getString(2)
        }
      }
      val money:Array[String] = Array(moneyIn, moneyOut)
      Print_statements.write_to_csv(account, transactions, money)
    }catch{
      case e:Exception => println("Error occurred")
    }
  }
  def closeConnection(): Unit ={
    connection.close()
  }
}
