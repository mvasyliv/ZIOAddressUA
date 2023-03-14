package com.mvasyliv.addressua
import zio._
import zio.http._
import com.mvasyliv.addressua.config.ServerConfig
import com.mvasyliv.addressua.config.DbConfig
import com.mvasyliv.addressua.api.HttpRoutes
import com.mvasyliv.addressua.healthcheck.Healthcheck
import com.mvasyliv.addressua.repo._
import zio.sql.ConnectionPool
object Main extends ZIOAppDefault {
  def run =
    zio.http.Server
      .serve(HttpRoutes.app ++ Healthcheck.expose)
      .provide(
        ServerConfig.layer,
        ZLayer
          .fromZIO(zio.config.getConfig[ServerConfig])
          .flatMap(c =>
            zio.http.ServerConfig
              .live(http.ServerConfig.default.port(c.get.port))
          ),
        Server.live,
        CountryRepositoryImpl.live,
        DbConfig.layer,
        ConnectionPool.live,
        DbConfig.connectionPoolConfig
      )
}
