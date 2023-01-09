package com.mvasyliv.addressua.repo.tabledescription

import com.mvasyliv.addressua.domain.model.Settlement
import zio.sql.postgresql.PostgresJdbcModule

trait SettlementTableDescription extends PostgresJdbcModule {
  val settlements                                         = defineTableSmart[Settlement]
  val (id, regionId, typeSettlementId, name, description) = settlements.columns
}
