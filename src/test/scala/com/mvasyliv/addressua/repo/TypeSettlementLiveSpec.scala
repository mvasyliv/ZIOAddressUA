package com.mvasyliv.addressua.repo
import zio.test.ZIOSpecDefault
import zio.test._
import zio.test.Assertion._
import zio.test.TestAspect._
import com.mvasyliv.addressua.repo.container.PostgresContainer
import zio.ZLayer
import zio.sql.ConnectionPool

object TypeSettlementLiveSpec extends ZIOSpecDefault {
  val typeSettlementIdTest = 1
  val typeSettlementFullNameTest = "село"

  val testLayer = ZLayer.make[TypeSettlementRepository](
    TypeSettlementRepositoryImpl.live,
    PostgresContainer.connectionPoolConfigLayer,
    ConnectionPool.live,
    PostgresContainer.createContainer
  )

  override def spec =
    suite("TypeSettlement repository test with postgres test container")(
      test("Count all type settlements") {
        for {
          count <- TypeSettlementRepository.findAll().runCount
        } yield assert(count)(equalTo(1L))
      }
      // ,
      // test("Get type settlement use Id") {
      //   for {
      //     count <- TypeSettlementRepository
      //       .findById(typeSettlementIdTest)
      //   } yield assert(count)(equalTo(1))
      // }
    ).provideLayerShared(testLayer.orDie) @@ sequential

}
