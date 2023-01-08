package com.mvasyliv.addressua.domain.model

import zio.schema.{DeriveSchema, Schema}

final case class TypeSettlement(
    id: Option[Int],
    name: String
)
object TypeSettlement {
  implicit val schemaTypeSettlement: Schema[TypeSettlement] =
    DeriveSchema.gen[TypeSettlement]
}
