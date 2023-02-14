def inferInts[T <: Int](t: T): T = t

def inferSingleton[T <: Singleton](t: T): T = t

def inferValueof[T](t: T)(using vot: ValueOf[T]): T = t

val oneInt: 1    = inferInts(1)
val oneSingleton = inferSingleton(1)
