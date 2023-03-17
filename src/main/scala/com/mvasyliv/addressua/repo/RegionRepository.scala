package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import java.util.UUID
import com.mvasyliv.addressua.domain.AppError.RepositoryError
import com.mvasyliv.addressua.domain.model.Region

trait RegionRepository {
  def findAll(): ZStream[Any, RepositoryError, Region]

  def findById(id: UUID): ZIO[Any, RepositoryError, Region]

  def add(region: Region): ZIO[Any, RepositoryError, Unit]

  def add(region: List[Region]): ZIO[Any, RepositoryError, Int]

}

object RegionRepository {
  def findAll(): ZStream[RegionRepository, RepositoryError, Region] =
    ZStream.serviceWithStream[RegionRepository](_.findAll())

  def findById(id: UUID): ZIO[RegionRepository, RepositoryError, Region] =
    ZIO.serviceWithZIO[RegionRepository](_.findById(id))

  def add(region: Region): ZIO[RegionRepository, RepositoryError, Unit] =
    ZIO.serviceWithZIO[RegionRepository](_.add(region))

  def add(
      region: List[Region]
  ): ZIO[RegionRepository, RepositoryError, Int] =
    ZIO.serviceWithZIO[RegionRepository](_.add(region))
}
