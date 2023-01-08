package com.mvasyliv.addressua.repo.tabledescription

import com.mvasyliv.addressua.domain.model.PostCode
import zio.sql.postgresql.PostgresJdbcModule

trait PostCodeTableDescription extends PostgresJdbcModule {
  val postcodes = defineTableSmart[PostCode]
  val (zipCode) = postcodes.columns
}
