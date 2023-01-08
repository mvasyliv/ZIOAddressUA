package com.mvasyliv.addressua.domain.model

import zio.schema.DeriveSchema

final case class TypeSettlement
  (
    id:   Int,
    name: String
  )
object TypeSettlement {
  implicit val schemaTypeSettlement = DeriveSchema.gen[TypeSettlement]
}
