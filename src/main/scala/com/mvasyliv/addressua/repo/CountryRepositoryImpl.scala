package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import zio.ZLayer
import zio.sql.ConnectionPool
import com.mvasyliv.addressua.domain.AppError._
import com.mvasyliv.addressua.domain.model.Country
import java.util.UUID
final class CountryRepositoryImpl(pool: ConnectionPool)
    extends CountryRepository
    with PostgresTableDescription {

  lazy val driverLayer =
    ZLayer.make[SqlDriver](SqlDriver.live, ZLayer.succeed(pool))

  override def findAll(): ZStream[Any, RepositoryError, Country] = {
    val sqlSelectAll =
      select(countryId, fullName, shortName, descriptionShort)
        .from(countries)

    ZStream.fromZIO(
      ZIO.logInfo(s"Query to execute findAll is ${renderRead(sqlSelectAll)}")
    ) *>
      execute(sqlSelectAll.to((Country.apply _).tupled))
        .provideDriver(driverLayer)
  }

  override def findById(id: UUID): ZIO[Any, RepositoryError, Country] = {
    val sqlSelectById = select(countryId, fullName, shortName, descriptionShort)
      .from(countries)
      .where(countryId === id)

    ZIO.logInfo(s"Query to execute findById is ${renderRead(sqlSelectById)}") *>
      execute(sqlSelectById.to((Country.apply _).tupled))
        .findFirst(driverLayer, id)
  }

  override def add(country: Country): ZIO[Any, RepositoryError, Unit] = {
    val query =
      insertInto(countries)(countryId, fullName, shortName, descriptionShort)
        .values(
          (
            country.id,
            country.fullName,
            country.shortName,
            country.descriptionShort
          )
        )

    ZIO.logInfo(s"Query to insert country is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
        .unit
  }

  override def add(country: List[Country]): ZIO[Any, RepositoryError, Int] = {
    val data =
      country.map(c => (c.id, c.fullName, c.shortName, c.descriptionShort))

    val query =
      insertInto(countries)(countryId, fullName, shortName, descriptionShort)
        .values(data)

    ZIO.logInfo(s"Query to insert countries is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
  }

}
object CountryRepositoryImpl {
  val live: ZLayer[ConnectionPool, Throwable, CountryRepository] =
    ZLayer.fromFunction(new CountryRepositoryImpl(_))
}
