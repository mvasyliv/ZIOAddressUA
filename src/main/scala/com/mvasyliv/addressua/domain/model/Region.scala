package com.mvasyliv.addressua.domain.model

import zio.schema.{DeriveSchema, Schema}

final case class Region(
    id: Int,
    areaId: Int,
    name: String,
    description: Option[String] = None
)
object Region {
  implicit val schemaRegion = DeriveSchema.gen[Region]
}
