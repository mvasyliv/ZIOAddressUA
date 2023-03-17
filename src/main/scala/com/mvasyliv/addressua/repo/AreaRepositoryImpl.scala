package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import zio.ZLayer
import zio.sql.ConnectionPool
import com.mvasyliv.addressua.domain.AppError._
import com.mvasyliv.addressua.domain.model.Area
import java.util.UUID
class AreaRepositoryImpl(pool: ConnectionPool)
    extends AreaRepository
    with PostgresTableDescription {

  lazy val driverLayer =
    ZLayer.make[SqlDriver](SqlDriver.live, ZLayer.succeed(pool))

  override def findAll(): ZStream[Any, RepositoryError, Area] = {
    val sqlSelectAll =
      select(areaId, areaCountryId, areaName, areaDescription)
        .from(areas)

    ZStream.fromZIO(
      ZIO.logInfo(s"Query to execute findAll is ${renderRead(sqlSelectAll)}")
    ) *>
      execute(sqlSelectAll.to((Area.apply _).tupled))
        .provideDriver(driverLayer)
  }

  override def findById(id: UUID): ZIO[Any, RepositoryError, Area] = {
    val sqlSelectById = select(areaId, areaCountryId, areaName, areaDescription)
      .from(areas)
      .where(areaId === id)

    ZIO.logInfo(s"Query to execute findById is ${renderRead(sqlSelectById)}") *>
      execute(sqlSelectById.to((Area.apply _).tupled))
        .findFirst(driverLayer, id)
  }

  override def add(area: Area): ZIO[Any, RepositoryError, Unit] = {
    val query =
      insertInto(areas)(areaId, areaCountryId, areaName, areaDescription)
        .values(
          (
            area.id,
            area.countryId,
            area.fullName,
            area.descriptionShort
          )
        )

    ZIO.logInfo(s"Query to insert area is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
        .unit
  }

  override def add(area: List[Area]): ZIO[Any, RepositoryError, Int] = {
    val data =
      area.map(a => (a.id, a.countryId, a.fullName, a.descriptionShort))

    val query =
      insertInto(areas)(areaId, areaCountryId, areaName, areaDescription)
        .values(data)

    ZIO.logInfo(s"Query to insert areas is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
  }

}

object AreaRepositoryImpl {
  val live: ZLayer[ConnectionPool, Throwable, AreaRepository] =
    ZLayer.fromFunction(new AreaRepositoryImpl(_))
}
