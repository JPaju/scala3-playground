import zio.*
import zio.stm.*

// Playground for implementing and fixing concurrency bugs

val iterations = 1000

// When concurrenty updating a mutable variable, race conditions can occur and updates can be lost
object IncrementBugs extends ZIOAppDefault:
  var counter = 0

  val increment = ZIO.replicate(iterations)(ZIO.succeed(counter += 1))

  val run = for
    _ <- ZIO.debug(s"Counter initially: $counter")
    // _ <- ZIO.collectAll(increment) // When executed sequentially, works fine
    _ <- ZIO.collectAllPar(increment) // When executed in parallel, race conditions occur
    _ <- ZIO.debug(s"Counter initially: $counter")
  yield ()

// When using a data type that supports synchronized/atomic updates, the issue is mitigated
object IncrementCorrect extends ZIOAppDefault:
  def increment(iterations: Int, counter: Ref[Int]) =
    ZIO.replicate(iterations)(counter.update(_ + 1)) // Ref.update is atomic

  val run = for
    counter <- Ref.make(0)
    _       <- counter.get.debug(s"Counter initially")
    _       <- ZIO.collectAllPar(increment(iterations, counter)) // Works correctly even when executed in parallel
    _       <- counter.get.debug(s"Counter finally")
  yield ()

// When concurrent atomic updates must span across multiple references, just a atomic update operation is not enough
// It might take couple of runs to replicate the bug (balance of an account ends up negative)
object TransactionBug extends ZIOAppDefault:
  type BankAccount = Ref[Int]

  def transfer(from: BankAccount, to: BankAccount, amount: Int) =
    for
      // When this part is executed concurrently, the balance can end up negative
      fromBalance <- from.get
      _           <- if amount <= fromBalance then to.update(_ + amount)
                     else ZIO.fail("Insufficient funds")
      _           <- from.update(_ - amount)
    yield ()

  val run = for
    fromAccount  <- Ref.make(iterations / 10)
    toAccount    <- Ref.make(0)
    makeTransfers = ZIO.replicate(iterations)(transfer(from = fromAccount, to = toAccount, 1).either)
    _            <- ZIO.collectAllPar(makeTransfers) // Execute transfers in parallel
    _            <- fromAccount.get.debug("From balance")
    _            <- toAccount.get.debug("To balance")
  yield ()

// Software transactional memory (STM) is a way to implement atomic updates across multiple references
object TransactionCorrect extends ZIOAppDefault:
  type BankAccount = TRef[Int]

  def transfer(from: BankAccount, to: BankAccount, amount: Int) =
    // Everything inside STM.atomically is executed (surprise, surprise) atomically
    // Which means that the balance of an account stays consistent
    STM.atomically {
      for
        fromBalance <- from.get
        _           <- if amount <= fromBalance then to.update(_ + amount)
                       else STM.fail("Insufficient funds")
        _           <- from.update(_ - amount)
      yield ()
    }

  val run = for
    fromAccount  <- TRef.make(iterations / 4).commit
    toAccount    <- TRef.make(0).commit
    makeTransfers = ZIO.replicate(iterations)(transfer(from = fromAccount, to = toAccount, 1).either)
    _            <- ZIO.collectAllPar(makeTransfers) // Execute transfers in parallel
    _            <- fromAccount.get.commit.debug("From balance")
    _            <- toAccount.get.commit.debug("To balance")
  yield ()
