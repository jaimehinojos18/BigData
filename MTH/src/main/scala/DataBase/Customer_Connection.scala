package DataBase
import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import User.Customer
object Customer_Connection {
  val driver = "com.mysql.cj.jdbc.Driver"
  val url = "jdbc:mysql://localhost:3306/mth_bank"
  val username = "root"
  val password = "pss1119"

  var connection:Connection = DriverManager.getConnection(url, username, password)
  val statement = connection.createStatement()

  def signIn(u:String, p:String):Customer = {
      try{
        val resultSet = statement.executeQuery(s"SELECT * FROM user WHERE username = '$u' AND password='$p';")
        if(!resultSet.isBeforeFirst()){
          println("User not found")
          null
        }else{
          resultSet.next()
          new Customer(resultSet.getString(2), resultSet.getString(3), resultSet.getString(1).toLong)
          //println(resultSet.getString(1)+", " +resultSet.getString(2))

        }
      }catch {
        case e: Exception => {println("Error occured")

          null}
      }
  }
  def createAccount(fn:String,fl:String, u:String, p:String):Customer = {
    try{
      statement.executeUpdate("INSERT INTO user (firstName, lastName, username, password)" +
        s"VALUES('$fn','$fl', '$u','$p');")

      print("Account Successfully Created")
      null
    }catch{
      case e:Exception => {println("Error: Account not created please try again. ")
      null}
    }
  }
  def closeConnection(): Unit ={
    connection.close()
  }

}
