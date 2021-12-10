package User

class Customer (private var firstName:String, private var lastName:String, private var id:Long){
  def this(){
    this("", "", 0)
  }
  def getFirstName(): String ={
      firstName
  }
  def getLastName(): String={
      lastName
  }
  def getId(): Long ={
      id
  }

}
