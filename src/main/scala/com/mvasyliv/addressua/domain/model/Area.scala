package com.mvasyliv.addressua.domain.model

import zio.json._
import java.util.UUID

final case class Area(
    id: UUID,
    countryId: UUID,
    fullName: String,
    descriptionShort: Option[String] = None
)

object Area {
  implicit val areaEncoders: JsonEncoder[Area] =
    DeriveJsonEncoder.gen[Area]
  implicit val areaDecoder: JsonDecoder[Area] =
    DeriveJsonDecoder.gen[Area]
}
