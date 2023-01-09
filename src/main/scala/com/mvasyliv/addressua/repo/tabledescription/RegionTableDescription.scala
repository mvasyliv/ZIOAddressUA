package com.mvasyliv.addressua.repo.tabledescription

import com.mvasyliv.addressua.domain.model.Region
import zio.sql.postgresql.PostgresJdbcModule

trait RegionTableDescription extends PostgresJdbcModule {
  val regions                         = defineTableSmart[Region]
  val (id, areaId, name, description) = regions.columns
}
