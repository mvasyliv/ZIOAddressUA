package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import zio.ZLayer
import zio.sql.ConnectionPool
import com.mvasyliv.addressua.domain.AppError._
import com.mvasyliv.addressua.domain.model.{TypeSettlement, TypeSettlementName}

class TypeSettlementRepositoryImpl(pool: ConnectionPool)
    extends TypeSettlementRepository
    with PostgresTableDescription {
  lazy val driverLayer =
    ZLayer.make[SqlDriver](SqlDriver.live, ZLayer.succeed(pool))

  override def findAll(): ZStream[Any, RepositoryError, TypeSettlement] = {
    val sqlSelectAll =
      select(typeSettlementId, typeSettlementName)
        .from(typeSettlements)

    ZStream.fromZIO(
      ZIO.logInfo(s"Query to execute findAll is ${renderRead(sqlSelectAll)}")
    ) *>
      execute(sqlSelectAll.to((TypeSettlement.apply _).tupled))
        .provideDriver(driverLayer)
  }

  override def findById(id: Int): ZIO[Any, RepositoryError, TypeSettlement] = {
    val sqlSelectById =
      select(typeSettlementId, typeSettlementName)
        .from(typeSettlements)
        .where(typeSettlementId === id)

    ZIO.logInfo(s"Query to execute findById is ${renderRead(sqlSelectById)}") *>
      execute(sqlSelectById.to((TypeSettlement.apply _).tupled))
        .findFirst(driverLayer, id)
  }

  override def add(
      typeSettlementName: TypeSettlementName
  ): ZIO[Any, RepositoryError, Unit] = {
    val query =
      insertInto(typeSettlementsName)(typeSettlementNameName)
        .values(
          (typeSettlementName.fullName)
        )

    ZIO.logInfo(s"Query to insert type settlement is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
        .unit
  }
}

object TypeSettlementRepositoryImpl {
  val live: ZLayer[ConnectionPool, Throwable, TypeSettlementRepository] =
    ZLayer.fromFunction(new TypeSettlementRepositoryImpl(_))
}
