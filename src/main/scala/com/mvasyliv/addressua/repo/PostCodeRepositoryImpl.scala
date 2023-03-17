package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import zio.ZLayer._
import zio.sql.ConnectionPool
import com.mvasyliv.addressua.domain.AppError._
import com.mvasyliv.addressua.domain.model.PostCode

final class PostCodeRepositoryImpl(pool: ConnectionPool)
    extends PostCodeRepository
    with PostgresTableDescription {

  lazy val driverLayer =
    ZLayer.make[SqlDriver](SqlDriver.live, ZLayer.succeed(pool))

  override def findAll(): ZStream[Any, RepositoryError, PostCode] = ???
  /* {
    val sqlSelectAll = select(postIndex).from(postcodes)

    ZStream.fromZIO(
      ZIO.logInfo(s"Query to execute findAll is ${renderRead(sqlSelectAll)}")
    ) *>
      execute(sqlSelectAll.to((PostCode.apply _).tupled))
        .provideDriver(driverLayer)
  } */

  override def findByCode(
      pCode: String
  ): ZIO[Any, RepositoryError, PostCode] = ???
  /* {
    val sqlSelectByCode = select(postIndex)
      .from(postcodes)
      .where(postIndex === pCode)

    ZIO.logInfo(
      s"Query to execute findByCode is ${renderRead(sqlSelectByCode)}"
    ) *>
      execute(sqlSelectByCode.to((PostCode.apply _).tupled))
        .findFirst(driverLayer, postIndex)
  } */

  override def add(pCode: PostCode): ZIO[Any, RepositoryError, Unit] = ???
  /* {
    val query =
      insertInto(postcodes)(postIndex).values((pCode.zipCode))

    ZIO.logInfo(s"Query to insert country is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
        .unit
  } */

  override def add(pCode: List[PostCode]): ZIO[Any, RepositoryError, Int] = ???
  /* {
    val data = pCode.map(p => (p.zipCode))

    val query =
      insertInto(postcodes)(postIndex).values(data)

    ZIO.logInfo(s"Query to insert countries is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
  }
   */
}
object PostCodeRepositoryImpl {
  val live: ZLayer[ConnectionPool, Throwable, PostCodeRepository] =
    ZLayer.fromFunction(new PostCodeRepositoryImpl(_))
}
