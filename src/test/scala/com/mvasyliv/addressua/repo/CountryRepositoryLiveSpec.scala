package com.mvasyliv.addressua.repo

import zio.test._
import zio.test.Assertion._
import zio.test.TestAspect._
import java.util.UUID
import com.mvasyliv.addressua.domain.model.Country
import com.mvasyliv.addressua.repo.container.PostgresContainer
import zio.ZLayer
import zio.sql.ConnectionPool

object CountryRepositoryLiveSpec extends ZIOSpecDefault {
  val countryId1 = UUID.randomUUID
  val countryFullName1 = "Ukraine"
  val countries = List(
    Country(countryId1, countryFullName1, "UA", Some("Ukraine test")),
    Country(UUID.randomUUID(), "USA", "USA", None),
    Country(UUID.randomUUID(), "Germany", "GD", None)
  )

  val testLayer = ZLayer.make[CountryRepository](
    CountryRepositoryImpl.live,
    PostgresContainer.connectionPoolConfigLayer,
    ConnectionPool.live,
    PostgresContainer.createContainer
  )

  override def spec = suite(
    "country repository test with postgres test container"
  )(
    test("count all countries") {
      for {
        count <- CountryRepository.findAll().runCount
      } yield assert(count)(equalTo(4L))
    },
    test("insert 3 new countries") {
      for {
        newCountries <- CountryRepository.add(countries)
        countCountries <- CountryRepository.findAll().runCount
      } yield assert(newCountries)(equalTo(3)) && assert(countCountries)(
        equalTo(7L)
      )
    },
    test("get inserted country") {
      for {
        country <- CountryRepository.findById(countryId1)
      } yield assert(country.fullName)(equalTo(countryFullName1))
    }
  ).provideLayerShared(testLayer.orDie) @@ sequential
}
