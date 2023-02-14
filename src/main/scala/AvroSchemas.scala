import com.sksamuel.avro4s.*
import org.apache.avro

import java.io.*
import scala.util.*

// Try serializing case class/ADT to AVRO

sealed trait Person
case class Adult(name: String, age: Int, job: String)        extends Person
case class Child(name: String, age: Int, toys: List[String]) extends Person

object Person:
  val avroSchema: avro.Schema = AvroSchema[Person]

@main
def avroSchemaMain =
  val alice     = Child("Alice", 12, List("doll", "ball"))
  val john      = Adult("John", 42, "Engineer")
  val bob       = Adult("Bob", 42, "Engineer")
  val jake      = Child("Jake", 12, List("phone", "rc car"))
  val catherine = Child("Catherine", 12, List("lego", "nintendo switch"))
  val nils      = Adult("Nils", 42, "Engineer")

  // Manager is a resource manager that will automatically close resources when the block of code is finished.
  Using.Manager { use =>
    val byteOs = use(ByteArrayOutputStream())
    val avroOs = use(AvroOutputStream.data[Person].to(new File("person.avro")).build())

    avroOs.write(
      List(
        alice,
        john,
        bob,
        jake,
        catherine,
        nils
      )
    )
    avroOs.flush()

    val results = byteOs.toByteArray.toVector

    println(Person.avroSchema)
    println(results)
  } // All resources opened with 'use' are closed here
