package DataBase
import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

object testConnection extends App {
  // connect to the database named "mysql" on port 8889 of localhost
  val driver = "com.mysql.cj.jdbc.Driver"
  val url = "jdbc:mysql://localhost:3306/mth_bank"
  val username = "root"
  val password = "pss1119"

  var connection:Connection = DriverManager.getConnection(url, username, password)
  val statement = connection.createStatement()
  try{
    val resultSet = statement.executeQuery("SELECT * FROM user WHERE username = 'jahinojos' AND password='pss1119';")
    if(!resultSet.isBeforeFirst()){
        println("Empty ")
    }else{
      while ( resultSet.next() ) {
        //println(resultSet.getString(1)+", " +resultSet.getString(2))
      }
    }
    /*statement.executeUpdate("INSERT INTO user (firstName, lastName, username, password)" +
      "VALUES('Jaime','Hinojos', 'jahinojos4','pss1119');")
      print("success")*/
  }catch {
    case e: Exception => println("Error occured")
  }

  connection.close()
}