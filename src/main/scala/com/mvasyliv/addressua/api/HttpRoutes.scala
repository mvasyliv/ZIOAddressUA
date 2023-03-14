package com.mvasyliv.addressua.api
import zio.http._
import zio.json._
import com.mvasyliv.addressua.repo._
import com.mvasyliv.addressua.api.Protocol._
import zio.http.model._
object HttpRoutes {
  val app: HttpApp[CountryRepository, Response] =
    Http.collectZIO[Request] {

      case Method.GET -> !! / "countries" =>
        CountryRepository
          .findAll()
          .runCollect
          .either
          .map {
            case Right(c) => Response.json(c.toJson)
            case Left(_)  => Response.status(Status.InternalServerError)
          }

      case Method.GET -> !! / "countries" / zio.http.uuid(id) =>
        CountryRepository
          .findById(id)
          .either
          .map {
            case Right(country) => Response.json(country.toJson)
            case Left(e)        => Response.text(e.getMessage())
          }
    }
}
