package com.mvasyliv.addressua.repo

import com.mvasyliv.addressua.domain.model.Country
import zio.sql.postgresql.PostgresJdbcModule

trait CountryTableDescription extends PostgresJdbcModule {
  val countries                          = defineTableSmart[Country]
  val (id, name, shortName, description) = countries.columns
}
