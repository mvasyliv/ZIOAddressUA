package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import com.mvasyliv.addressua.domain.AppError.RepositoryError
import com.mvasyliv.addressua.domain.model.{
  Country,
  PostCode,
  Area,
  Region,
  TypeSettlement,
  TypeSettlementName,
  Settlement
}
import zio.schema.DeriveSchema
import zio.sql.postgresql.PostgresJdbcModule

trait PostgresTableDescription extends PostgresJdbcModule {

  implicit val countrySchema = DeriveSchema.gen[Country]
  val countries = defineTableSmart[Country]
  val (countryId, fullName, shortName, descriptionShort) = countries.columns

  implicit val postCodeSchema = DeriveSchema.gen[PostCode]
  val postcodes = defineTableSmart[PostCode]
  val (postIndex) = postcodes.columns

  implicit val areaSchema = DeriveSchema.gen[Area]
  val areas = defineTableSmart[Area]
  val (areaId, areaCountryId, areaName, areaDescription) = areas.columns

  implicit val regionSchema = DeriveSchema.gen[Region]
  val regions = defineTable[Region]("regions")
  val (regionId, regionAreaId, regionName, regionDescription) = regions.columns

  implicit val typeSettlementSchema = DeriveSchema.gen[TypeSettlement]
  val typeSettlements = defineTable[TypeSettlement]("type_settlement")
  val (typeSettlementId, typeSettlementName) = typeSettlements.columns

  implicit val typeSettlementNameSchema = DeriveSchema.gen[TypeSettlementName]
  val typeSettlementsName = defineTable[TypeSettlementName]("type_settlement")
  val (typeSettlementNameName) = typeSettlementsName.columns

  implicit val settlementSchema = DeriveSchema.gen[Settlement]
  val settlements = defineTable[Settlement]("settlement")
  val (
    settlementId,
    settlementRegionId,
    settlementTypeSettlementId,
    settlementName,
    settlementDescription
  ) = settlements.columns

  implicit class ZStreamSqlExt[T](zstream: ZStream[SqlDriver, Exception, T]) {
    def provideDriver(
        driver: ULayer[SqlDriver]
    ): ZStream[Any, RepositoryError, T] =
      zstream
        .tapError(e => ZIO.logError(e.getMessage()))
        .mapError(e => RepositoryError(e.getCause()))
        .provideLayer(driver)

    def findFirst(
        driver: ULayer[SqlDriver],
        id: java.util.UUID
    ): ZIO[Any, RepositoryError, T] =
      zstream.runHead.some
        .tapError {
          case None    => ZIO.unit
          case Some(e) => ZIO.logError(e.getMessage())
        }
        .mapError {
          case None =>
            RepositoryError(
              new RuntimeException(s"Order with id $id does not exists")
            )
          case Some(e) => RepositoryError(e.getCause())
        }
        .provide(driver)

    def findFirst(
        driver: ULayer[SqlDriver],
        id: Int
    ): ZIO[Any, RepositoryError, T] =
      zstream.runHead.some
        .tapError {
          case None    => ZIO.unit
          case Some(e) => ZIO.logError(e.getMessage())
        }
        .mapError {
          case None =>
            RepositoryError(
              new RuntimeException(s"Order with id $id does not exists")
            )
          case Some(e) => RepositoryError(e.getCause())
        }
        .provide(driver)
  }

  implicit class ZioSqlExt[T](zio: ZIO[SqlDriver, Exception, T]) {
    def provideAndLog(driver: ULayer[SqlDriver]): ZIO[Any, RepositoryError, T] =
      zio
        .tapError(e => ZIO.logError(e.getMessage()))
        .mapError(e => RepositoryError(e.getCause()))
        .provide(driver)
  }
}
