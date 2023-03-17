package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import zio.ZLayer
import zio.sql.ConnectionPool
import com.mvasyliv.addressua.domain.AppError._
import com.mvasyliv.addressua.domain.model.Region
import java.util.UUID

class RegionRepositoryImpl(pool: ConnectionPool)
    extends RegionRepository
    with PostgresTableDescription {

  lazy val driverLayer =
    ZLayer.make[SqlDriver](SqlDriver.live, ZLayer.succeed(pool))

  override def findAll(): ZStream[Any, RepositoryError, Region] = {
    val sqlSelectAll =
      select(regionId, regionAreaId, regionName, regionDescription)
        .from(regions)

    ZStream.fromZIO(
      ZIO.logInfo(s"Query to execute findAll is ${renderRead(sqlSelectAll)}")
    ) *>
      execute(sqlSelectAll.to((Region.apply _).tupled))
        .provideDriver(driverLayer)
  }

  override def findById(id: UUID): ZIO[Any, RepositoryError, Region] = {
    val sqlSelectById =
      select(regionId, regionAreaId, regionName, regionDescription)
        .from(regions)
        .where(regionId === id)

    ZIO.logInfo(s"Query to execute findById is ${renderRead(sqlSelectById)}") *>
      execute(sqlSelectById.to((Region.apply _).tupled))
        .findFirst(driverLayer, id)
  }

  override def add(region: Region): ZIO[Any, RepositoryError, Unit] = {
    val query =
      insertInto(regions)(regionId, regionAreaId, regionName, regionDescription)
        .values(
          (
            region.id,
            region.areaId,
            region.fullName,
            region.descriptionShort
          )
        )

    ZIO.logInfo(s"Query to insert region is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
        .unit
  }

  override def add(region: List[Region]): ZIO[Any, RepositoryError, Int] = {
    val data =
      region.map(r => (r.id, r.areaId, r.fullName, r.descriptionShort))

    val query =
      insertInto(regions)(regionId, regionAreaId, regionName, regionDescription)
        .values(data)

    ZIO.logInfo(s"Query to insert regions is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
  }

}

object RegionRepositoryImpl {
  val live: ZLayer[ConnectionPool, Throwable, RegionRepository] =
    ZLayer.fromFunction(new RegionRepositoryImpl(_))
}
