package com.mvasyliv.addressua.api
import zio.http._
import zio.json._
import com.mvasyliv.addressua.repo._
import zio.http.model._
object HttpRoutes {
  val app: HttpApp[
    CountryRepository
      with AreaRepository
      with RegionRepository
      with TypeSettlementRepository,
    Response
  ] =
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
      case Method.GET -> !! / "areas" =>
        AreaRepository
          .findAll()
          .runCollect
          .either
          .map {
            case Right(c) => Response.json(c.toJson)
            case Left(_)  => Response.status(Status.InternalServerError)
          }

      case Method.GET -> !! / "areas" / zio.http.uuid(id) =>
        AreaRepository
          .findById(id)
          .either
          .map {
            case Right(area) => Response.json(area.toJson)
            case Left(e)     => Response.text(e.getMessage())
          }
      case Method.GET -> !! / "regions" =>
        RegionRepository
          .findAll()
          .runCollect
          .either
          .map {
            case Right(r) => Response.json(r.toJson)
            case Left(_)  => Response.status(Status.InternalServerError)
          }

      case Method.GET -> !! / "regions" / zio.http.uuid(id) =>
        RegionRepository
          .findById(id)
          .either
          .map {
            case Right(region) => Response.json(region.toJson)
            case Left(e)       => Response.text(e.getMessage())
          }

      case Method.GET -> !! / "typeSettlements" =>
        TypeSettlementRepository
          .findAll()
          .runCollect
          .either
          .map {
            case Right(r) => Response.json(r.toJson)
            case Left(_)  => Response.status(Status.InternalServerError)
          }
      case Method.GET -> !! / "typeSettlements" / zio.http.int(id) =>
        TypeSettlementRepository
          .findById(id)
          .either
          .map {
            case Right(region) => Response.json(region.toJson)
            case Left(e)       => Response.text(e.getMessage())
          }

    }
}
