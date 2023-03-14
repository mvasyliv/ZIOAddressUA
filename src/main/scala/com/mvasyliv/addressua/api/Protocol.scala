package com.mvasyliv.addressua.api

import zio._
import zio.json._
import com.mvasyliv.addressua.domain.model.Country

object Protocol {
  final case class Countries(countries: Chunk[Country])
  implicit val countryEncoders: JsonEncoder[Country] =
    DeriveJsonEncoder.gen[Country]
  implicit val countryDecoder: JsonDecoder[Country] =
    DeriveJsonDecoder.gen[Country]
}
