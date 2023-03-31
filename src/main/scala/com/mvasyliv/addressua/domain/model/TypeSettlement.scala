package com.mvasyliv.addressua.domain.model

import zio.json.JsonEncoder
import zio.json._
final case class TypeSettlement(
    id: Int,
    fullName: String
)
object TypeSettlement {
  implicit val typeSettlementEncoder: JsonEncoder[TypeSettlement] =
    DeriveJsonEncoder.gen[TypeSettlement]
  implicit val typeSettlementDecoder: JsonDecoder[TypeSettlement] =
    DeriveJsonDecoder.gen[TypeSettlement]
}
