package com.mvasyliv.addressua.domain.model

import zio.schema.DeriveSchema

final case class Settlement(
  id: Int,
  regionId: Int,
  typeSettlementId: Int,
  name: String,
  description: Option[String] = None
)

object Settlement {
  implicit val schemaSettlement = DeriveSchema.gen[Settlement]
}
