package Tools
import java.io.{BufferedWriter, FileWriter}
import scala.collection.JavaConversions._
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import au.com.bytecode.opencsv.CSVWriter
object Print_statements {
  val outputFile = new BufferedWriter(new FileWriter("C:/Users/ghost/IdeaProjects/MTH/src/main/statements/test.csv")) //replace the path with the desired path and filename with the desired filename
  val csvWriter = new CSVWriter(outputFile)
  val csvFields = Array("Account Number", "Account Type", "User ID", "Balance")
  val temp = Array(" ")
  val nameList = Array("transaction_id", "account_id", "receiving_account", "amount", "transaction_date", "transaction_type")
  val money_labels = Array("Money In", "Money Out")
  var listOfRecords = new ListBuffer[Array[String]]()
  def write_to_csv(user:Array[String], transaction:ListBuffer[Array[String]], money:Array[String]): Unit ={
    listOfRecords += csvFields
    listOfRecords+=user
    listOfRecords+=temp
    listOfRecords+=temp
    listOfRecords+=temp
    listOfRecords+=nameList
    for (i <- transaction){
      listOfRecords += i

    }
    listOfRecords+=money_labels
    listOfRecords+=money
    csvWriter.writeAll(listOfRecords.toList)
    outputFile.close()
  }


}
