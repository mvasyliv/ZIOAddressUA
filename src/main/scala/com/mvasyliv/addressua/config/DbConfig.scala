package com.mvasyliv.addressua.config

import com.typesafe.config.ConfigFactory
import zio._
import zio.config.ConfigDescriptor._
import zio.config._
import zio.config.magnolia.Descriptor.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.sql.ConnectionPoolConfig

import java.util.Properties

final case class DbConfig(
    host: String,
    port: String,
    dbName: String,
    url: String,
    user: String,
    password: String,
    driver: String,
    connectThreadPoolSize: Int
)

object DbConfig {

  private val dbConfigDescriptor = nested("db-config")(descriptor[DbConfig])

  val layer = ZLayer(
    ZIO
      .attempt(
        TypesafeConfigSource.fromTypesafeConfig(
          ZIO.attempt(ConfigFactory.defaultApplication())
        )
      )
      .map(source => dbConfigDescriptor from source)
      .flatMap(config => read(config))
      .orDie
  )

  val connectionPoolConfig: ZLayer[DbConfig, Throwable, ConnectionPoolConfig] =
    ZLayer(
      for {
        serverConfig <- ZIO.service[DbConfig]
      } yield (ConnectionPoolConfig(
        serverConfig.url,
        connProperties(serverConfig.user, serverConfig.password)
      ))
    )

  private def connProperties(user: String, password: String): Properties = {
    val props = new Properties
    props.setProperty("user", user)
    props.setProperty("password", password)
    props
  }
}
