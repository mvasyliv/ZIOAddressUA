package com.mvasyliv.addressua.repo

import zio.test._
import zio.test.Assertion._
import zio.test.TestAspect._
import java.util.UUID
import com.mvasyliv.addressua.domain.model.Area
import com.mvasyliv.addressua.repo.container.PostgresContainer
import zio.ZLayer
import zio.sql.ConnectionPool
object AreaRepositoryLiveSpec extends ZIOSpecDefault {
  val areaIdTest = UUID.randomUUID
  val areaCountryIdTest = "97f25d3f-1f81-43b1-8a9f-7133cf0a4121"
  val areaFullNameTest = "Івано-Франкфівська"
  val areas: List[Area] = List(
    Area(
      areaIdTest,
      UUID.fromString(areaCountryIdTest),
      areaFullNameTest,
      None
    ),
    Area(UUID.randomUUID, UUID.fromString(areaCountryIdTest), "Київська", None)
  )

  val testLayer = ZLayer.make[AreaRepository](
    AreaRepositoryImpl.live,
    PostgresContainer.connectionPoolConfigLayer,
    ConnectionPool.live,
    PostgresContainer.createContainer
  )

  override def spec =
    suite("Area repository test with postgres test container")(
      test("Count all areas") {
        for {
          count <- AreaRepository.findAll().runCount
        } yield assert(count)(equalTo(2L))
      },
      test("Insert 2 new areas") {
        for {
          newAreas <- AreaRepository.add(areas)
          countAreas <- AreaRepository.findAll().runCount
        } yield assert(newAreas)(equalTo(2)) && assert(countAreas)(equalTo(4L))
      }
    ).provideLayerShared(testLayer.orDie) @@ sequential
}

