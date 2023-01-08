package com.mvasyliv.addressua.repo.tabledescription

import com.mvasyliv.addressua.domain.model.TypeSettlement
import zio.sql.postgresql.PostgresJdbcModule

trait TypeSettlementTableDescription extends PostgresJdbcModule {
  val typeSettlements = defineTableSmart[TypeSettlement]
  val (id, name)      = typeSettlements.columns
}
