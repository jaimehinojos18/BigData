package DataBase
import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import User.Customer

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
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
  def printAccountInfo(): Unit = {

  }
  def depositIntoAccount(): Unit = {

  }
  def transferr(): Unit ={

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

  def closeConnection(): Unit ={
    connection.close()
  }
}
