package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import com.mvasyliv.addressua.domain.AppError.RepositoryError
import com.mvasyliv.addressua.domain.model.PostCode
trait PostCodeRepository {
  def findAll(): ZStream[Any, RepositoryError, PostCode]
  def findByCode(pCode: String): ZIO[Any, RepositoryError, PostCode]
  def add(pCode: PostCode): ZIO[Any, RepositoryError, Unit]
  def add(pCode: List[PostCode]): ZIO[Any, RepositoryError, Int]
}

object PostCodeRepository {
  def findAll(): ZStream[PostCodeRepository, RepositoryError, PostCode] =
    ZStream.serviceWithStream[PostCodeRepository](_.findAll())
  def findByCode(
      pCode: String
  ): ZIO[PostCodeRepository, RepositoryError, PostCode] =
    ZIO.serviceWithZIO[PostCodeRepository](_.findByCode(pCode))
  def add(pCode: PostCode): ZIO[PostCodeRepository, RepositoryError, Unit] =
    ZIO.serviceWithZIO[PostCodeRepository](_.add(pCode))
  def add(
      pCode: List[PostCode]
  ): ZIO[PostCodeRepository, RepositoryError, Int] =
    ZIO.serviceWithZIO[PostCodeRepository](_.add(pCode))
}
