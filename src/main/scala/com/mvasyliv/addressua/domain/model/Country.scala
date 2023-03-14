package com.mvasyliv.addressua.domain.model
import java.util.UUID

final case class Country(
    id: UUID,
    fullName: String,
    shortName: String,
    descriptionShort: Option[String]
)
