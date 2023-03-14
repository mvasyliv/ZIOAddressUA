package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import java.util.UUID
import com.mvasyliv.addressua.domain.AppError.RepositoryError
import com.mvasyliv.addressua.domain.model.Country

trait CountryRepository {
  def findAll(): ZStream[Any, RepositoryError, Country]

  def findById(id: UUID): ZIO[Any, RepositoryError, Country]

  def add(customer: Country): ZIO[Any, RepositoryError, Unit]

  def add(customer: List[Country]): ZIO[Any, RepositoryError, Int]
}

object CountryRepository {
  def findAll(): ZStream[CountryRepository, RepositoryError, Country] =
    ZStream.serviceWithStream[CountryRepository](_.findAll())

  def findById(id: UUID): ZIO[CountryRepository, RepositoryError, Country] =
    ZIO.serviceWithZIO[CountryRepository](_.findById(id))

  def add(customer: Country): ZIO[CountryRepository, RepositoryError, Unit] =
    ZIO.serviceWithZIO[CountryRepository](_.add(customer))

  def add(
      customer: List[Country]
  ): ZIO[CountryRepository, RepositoryError, Int] =
    ZIO.serviceWithZIO[CountryRepository](_.add(customer))
}
