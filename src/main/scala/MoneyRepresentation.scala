def sumNTimes[N: Numeric](sumCount: Int, toSum: N): N =
  List.fill(sumCount)(toSum).sum

def doubleMoney() =
  val dime       = 0.1
  val threeDimes = sumNTimes(3, dime)
  val manyDimes  = sumNTimes(1000, dime)

  println(s"Three dimes: $threeDimes")
  println(s"Many dimes: $manyDimes")

def bigDecimal() =
  val dime       = BigDecimal(0.1)
  val threeDimes = sumNTimes(3, dime)
  val manyDimes  = sumNTimes(1000, dime)

  println(s"Three dimes: $threeDimes")
  println(s"Many dimes: $manyDimes")

@main def moneyMain =
  // doubleMoney()
  bigDecimal()
