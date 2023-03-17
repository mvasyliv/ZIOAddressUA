package com.mvasyliv.addressua.domain.model
import zio.json._
import java.util.UUID

final case class Country(
    id: UUID,
    fullName: String,
    shortName: String,
    descriptionShort: Option[String]
)
object Country {
  implicit val countryEncoders: JsonEncoder[Country] =
    DeriveJsonEncoder.gen[Country]
  implicit val countryDecoder: JsonDecoder[Country] =
    DeriveJsonDecoder.gen[Country]

}
