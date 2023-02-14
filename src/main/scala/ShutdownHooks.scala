def blue(s: String) =
  Console.BLUE + s + Console.RESET

def red(s: String) =
  Console.RED + s + Console.RESET

// Try out different methods of blocking a thread without busy loop
// Implementations with java.util.concurrent.CountDownLatch and scala.concurrent.Promise

def blockWithlatch(ms: Int): Unit =
  val latch = java.util.concurrent.CountDownLatch(1)

  Thread(() =>
    println(red("Latch thread started"))
    Thread.sleep(ms)
    latch.countDown()
    println(red("Latch thread finished"))
  ).start()

  latch.await()

def blockWithPromise(ms: Int): Unit =
  import scala.concurrent.*
  val promise = Promise[Unit]()

  Thread(() =>
    println(red("Promise thread started"))
    Thread.sleep(ms)
    promise.success(())
    println(red("Promise thread finished"))
  ).start()

  Await.result(promise.future, duration.Duration.Inf)

@main
def shutdownHookMain =
  println(blue("Main thread started"))

  val delayMs = 1300

  val shutdownMsg = blue("Shutdown hook called")
  sys.addShutdownHook {
    println(shutdownMsg)
  }

  // blockWithlatch(delayMs)
  blockWithPromise(delayMs)
