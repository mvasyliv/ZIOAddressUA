package com.mvasyliv.addressua.repo.tabledescription

import com.mvasyliv.addressua.domain.model.Area
import zio.sql.postgresql.PostgresJdbcModule

trait AreaTableDescription extends PostgresJdbcModule {
  val areas                              = defineTableSmart[Area]
  val (id, countryId, name, description) = areas.columns
}
