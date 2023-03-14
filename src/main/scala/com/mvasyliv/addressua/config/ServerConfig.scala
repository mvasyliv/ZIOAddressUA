package com.mvasyliv.addressua.config

import com.typesafe.config.ConfigFactory
import zio._
import zio.config._
import zio.config.ConfigDescriptor._
import zio.config.typesafe.TypesafeConfigSource

final case class ServerConfig(port: Int)

object ServerConfig {

  private val serverConfigDescription = nested("server-config") {
    int("port").default(8080)
  }.to[ServerConfig]

  val layer = ZLayer(
    ZIO
      .attempt(
        TypesafeConfigSource.fromTypesafeConfig(
          ZIO.attempt(ConfigFactory.defaultApplication())
        )
      )
      .map(source => serverConfigDescription from source)
      .flatMap(config => read(config))
      .orDie
  )
}
