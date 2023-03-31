package com.mvasyliv.addressua.domain.model

import java.util.UUID
import zio.json._
final case class Settlement(
    id: UUID,
    regionId: UUID,
    typeSettlementId: Int,
    fullName: String,
    descriptionShort: Option[String] = None
)

object Settlement {
  implicit val settlementEncoder: JsonEncoder[Settlement] =
    DeriveJsonEncoder.gen[Settlement]
  implicit val settlementDecoder: JsonDecoder[Settlement] =
    DeriveJsonDecoder.gen[Settlement]
}
