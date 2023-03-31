package com.mvasyliv.addressua.repo
import zio._
import zio.stream._
import com.mvasyliv.addressua.domain.AppError.RepositoryError
import com.mvasyliv.addressua.domain.model.{TypeSettlement, TypeSettlementName}

trait TypeSettlementRepository {
  def findAll(): ZStream[Any, RepositoryError, TypeSettlement]

  def findById(id: Int): ZIO[Any, RepositoryError, TypeSettlement]

  def add(
      typeSettlementName: TypeSettlementName
  ): ZIO[Any, RepositoryError, Unit]
}
object TypeSettlementRepository {
  def findAll()
      : ZStream[TypeSettlementRepository, RepositoryError, TypeSettlement] =
    ZStream.serviceWithStream[TypeSettlementRepository](_.findAll())

  def findById(
      id: Int
  ): ZIO[TypeSettlementRepository, RepositoryError, TypeSettlement] =
    ZIO.serviceWithZIO[TypeSettlementRepository](_.findById(id))

  def add(
      typeSettlementName: TypeSettlementName
  ): ZIO[TypeSettlementRepository, RepositoryError, Unit] =
    ZIO.serviceWithZIO[TypeSettlementRepository](_.add(typeSettlementName))
}
