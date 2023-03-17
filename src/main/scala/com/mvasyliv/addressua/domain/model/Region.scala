package com.mvasyliv.addressua.domain.model

import zio.json._
import java.util.UUID

final case class Region(
    id: UUID,
    areaId: UUID,
    fullName: String,
    descriptionShort: Option[String] = None
)
object Region {
  implicit val regionEncoder: JsonEncoder[Region] =
    DeriveJsonEncoder.gen[Region]
  implicit val regionDecoder: JsonDecoder[Region] =
    DeriveJsonDecoder.gen[Region]
}
