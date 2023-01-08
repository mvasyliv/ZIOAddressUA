package com.mvasyliv.addressua.domain.model

import zio.schema.{DeriveSchema, Schema}

final case class PostCode(zipCode: String)
object PostCode {
  implicit val schemaPostCode = DeriveSchema.gen[PostCode]
}
