package com.mvasyliv.addressua.domain.model

import zio.json.JsonEncoder
import zio.json._

final case class TypeSettlementName(
    fullName: String
)
object TypeSettlementName {
  implicit val typeSettlementNameEncoder: JsonEncoder[TypeSettlementName] =
    DeriveJsonEncoder.gen[TypeSettlementName]
  implicit val typeSettlementNameDecoder: JsonDecoder[TypeSettlementName] =
    DeriveJsonDecoder.gen[TypeSettlementName]

}
