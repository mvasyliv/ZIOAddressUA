package com.mvasyliv.addressua.domain.model

import zio.json._

final case class PostCode(zipCode: String)

object PostCode {
  implicit val postCodeEncoders: JsonEncoder[PostCode] =
    DeriveJsonEncoder.gen[PostCode]
  implicit val postCodeDecoder: JsonDecoder[PostCode] =
    DeriveJsonDecoder.gen[PostCode]
}
