package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import java.util.UUID
import com.mvasyliv.addressua.domain.AppError.RepositoryError
import com.mvasyliv.addressua.domain.model.Country

trait CountryRepository {
  def findAll(): ZStream[Any, RepositoryError, Country]

  def findById(id: UUID): ZIO[Any, RepositoryError, Country]

  def add(country: Country): ZIO[Any, RepositoryError, Unit]

  def add(country: List[Country]): ZIO[Any, RepositoryError, Int]
}

object CountryRepository {
  def findAll(): ZStream[CountryRepository, RepositoryError, Country] =
    ZStream.serviceWithStream[CountryRepository](_.findAll())

  def findById(id: UUID): ZIO[CountryRepository, RepositoryError, Country] =
    ZIO.serviceWithZIO[CountryRepository](_.findById(id))

  def add(country: Country): ZIO[CountryRepository, RepositoryError, Unit] =
    ZIO.serviceWithZIO[CountryRepository](_.add(country))

  def add(
      country: List[Country]
  ): ZIO[CountryRepository, RepositoryError, Int] =
    ZIO.serviceWithZIO[CountryRepository](_.add(country))
}
