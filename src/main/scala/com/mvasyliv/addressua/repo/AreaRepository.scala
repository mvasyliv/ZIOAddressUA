package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import java.util.UUID
import com.mvasyliv.addressua.domain.AppError.RepositoryError
import com.mvasyliv.addressua.domain.model.Area
trait AreaRepository {
  def findAll(): ZStream[Any, RepositoryError, Area]

  def findById(id: UUID): ZIO[Any, RepositoryError, Area]

  def add(area: Area): ZIO[Any, RepositoryError, Unit]

  def add(area: List[Area]): ZIO[Any, RepositoryError, Int]
}

object AreaRepository {
  def findAll(): ZStream[AreaRepository, RepositoryError, Area] =
    ZStream.serviceWithStream[AreaRepository](_.findAll())

  def findById(id: UUID): ZIO[AreaRepository, RepositoryError, Area] =
    ZIO.serviceWithZIO[AreaRepository](_.findById(id))

  def add(area: Area): ZIO[AreaRepository, RepositoryError, Unit] =
    ZIO.serviceWithZIO[AreaRepository](_.add(area))

  def add(
      area: List[Area]
  ): ZIO[AreaRepository, RepositoryError, Int] =
    ZIO.serviceWithZIO[AreaRepository](_.add(area))
}
