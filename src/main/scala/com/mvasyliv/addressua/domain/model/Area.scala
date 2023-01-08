package com.mvasyliv.addressua.domain.model

import zio.schema.{DeriveSchema, Schema}

final case class Area(
    id: Int,
    countryId: Int,
    name: String,
    description: Option[String] = None
)

object Area {
  implicit val schemaArea: Schema[Area] = DeriveSchema.gen[Area]
}
