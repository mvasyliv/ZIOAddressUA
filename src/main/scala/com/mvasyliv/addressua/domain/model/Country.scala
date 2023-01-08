package com.mvasyliv.addressua.domain.model

import zio.schema.DeriveSchema

finally case class Country(
  id: Int,
  name: String,
  shortName: String,
  description: Option[String]
)
object Country {
  implicit val schemaCountry = DeriveSchema.gen[Country]
}
