package com.mvasyliv.addressua.domain

sealed trait AppError extends Throwable

object AppError {
  final case class RepositoryError(cause: Throwable) extends AppError
  final case class DecodingError(message: String) extends AppError
}
