import reflect.Selectable.reflectiveSelectable

trait Animal:
  def legCount: Int

type Duck = Animal {
  def quack: Unit
}

type Dog = Animal {
  def bark: Unit
}

case class DogClass() extends Animal:
  def bark: Unit    = println("Class Woof!")
  def legCount: Int = 4

val duck: Duck = new Animal:
  def quack    = println("Quack!")
  def legCount = 2

val dog: Dog = new Animal:
  def bark     = println("Woof!")
  def legCount = 4

val dogClass: DogClass = DogClass()

def useDog(dog: Dog) =
  dog.bark
  println(s"Dog has ${dog.legCount} legs")

@main
def structuralMain =
  useDog(dog)
  useDog(dogClass)
  duck.quack
  dog.bark
