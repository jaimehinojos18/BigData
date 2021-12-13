package Tools
import java.io.{BufferedWriter, FileWriter}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.util.Random

import au.com.bytecode.opencsv.CSVWriter


object write_to_csv extends App{
  val outputFile = new BufferedWriter(new FileWriter("C:/Users/ghost/IdeaProjects/MTH/src/main/statements/test.csv")) //replace the path with the desired path and filename with the desired filename
  val csvWriter = new CSVWriter(outputFile)
  val csvFields = Array("Account Number", "Account Type", "User ID", "Balance")
  val temp = Array(" ")
  val nameList = Array("Transaction Number", "Account Number", "Account sent to", "Amount", "Transaction Date", "Transaction Type")
  val ageList = (24 to 26).toList
  val cityList = List("Delhi", "Kolkata", "Chennai", "Mumbai")
  val random = new Random()
  var listOfRecords = new ListBuffer[Array[String]]()
  listOfRecords += csvFields
  listOfRecords+=temp
  listOfRecords+=temp
  listOfRecords+=temp
  listOfRecords+=temp
  listOfRecords+=nameList
  var list2 = new ListBuffer[Array[String]]()
  list2+= csvFields
  list2 += nameList
  for (i <- list2){
    listOfRecords += i
    print("Hi")

  }
  csvWriter.writeAll(listOfRecords.toList)
  outputFile.close()
  /*for(i <- )
  for (i <- listOfRecords += Array(i.toString, nameList(random.nextInt(nameList.length)), ageList(random.nextInt(ageList.length)).toString, cityList(random.nextInt(cityList.length)))



   */

}
