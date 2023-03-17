package com.mvasyliv.addressua.repo

import zio.test.ZIOSpecDefault
import zio.test._
import zio.test.Assertion._
import zio.test.TestAspect._
import java.util.UUID
import com.mvasyliv.addressua.domain.model.Region
import com.mvasyliv.addressua.repo.container.PostgresContainer
import zio.ZLayer
import zio.sql.ConnectionPool
object RegionRepositoryLiveSpec extends ZIOSpecDefault {
  val regionIdTest = UUID.randomUUID
  val regionAreaIdTest = "97ed6175-2502-4287-993e-ca258aac745d"
  val regionFullNameTest = "Львівський"
  val regions = List(
    Region(
      regionIdTest,
      UUID.fromString(regionAreaIdTest),
      regionFullNameTest,
      None
    ),
    Region(
      UUID.randomUUID,
      UUID.fromString(regionAreaIdTest),
      "Червоноградський",
      None
    )
  )

  val testLayer = ZLayer.make[RegionRepository](
    RegionRepositoryImpl.live,
    PostgresContainer.connectionPoolConfigLayer,
    ConnectionPool.live,
    PostgresContainer.createContainer
  )

  override def spec =
    suite("Region repository test with postgres test container")(
      test("Count all regions") {
        for {
          count <- RegionRepository.findAll().runCount
        } yield assert(count)(equalTo(2L))
      },
      test("Insert 2 new regions") {
        for {
          newRegions <- RegionRepository.add(regions)
          countRegions <- RegionRepository.findAll().runCount
        } yield assert(newRegions)(equalTo(2)) && assert(countRegions)(
          equalTo(4L)
        )
      }
    ).provideLayerShared(testLayer.orDie) @@ sequential

}
