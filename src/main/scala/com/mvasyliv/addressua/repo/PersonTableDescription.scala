package com.mvasyliv.addressua.repo

import zio.schema.DeriveSchema
import zio.sql.postgresql.PostgresJdbcModule
import java.util.UUID

trait PersonTableDescription extends PostgresJdbcModule {

  final case class Person(id: UUID, fName: String, lName: String)
  implicit val personSchema = DeriveSchema.gen[Person]
  val persons               = defineTableSmart[Person]
  val (id, fName, lName)    = persons.columns
}
