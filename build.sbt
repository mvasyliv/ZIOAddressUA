ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.mvasyliv"
ThisBuild / organizationName := "mvasyliv"

val zioVersion                 = "2.0.5"
val zioSqlVersion              = "0.1.1"
val zioSchemaVersion           = "0.4.1"
val zioHttpVersion             = "2.0.0-RC4"
val zioConfigVersion           = "3.0.6"
val zioJsonVersion             = "0.4.2"
val logbackVersion             = "1.2.7"
val testcontainersVersion      = "1.17.6"
val testcontainersScalaVersion = "0.40.12"

lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        name         := "ZIOAddressUA",
        organization := "mvasyliv",
        version      := "0.0.1",
        scalaVersion := "2.13.7"
      )
    ),
    name                             := "ZIOAddressUA",
    libraryDependencies ++= Seq(
      // Core
      "dev.zio"           %% "zio"                             % zioVersion,
      "dev.zio"           %% "zio-schema"                      % zioSchemaVersion,
      "dev.zio"           %% "zio-schema-derivation"           % zioSchemaVersion,
      // SQL
      "dev.zio"           %% "zio-sql-postgres"                % zioSqlVersion,
      "org.postgresql"     % "postgresql"                      % "42.0.0",
      "org.flywaydb"       % "flyway-core"                     % "4.1.2",
      // HTTP
      "io.d11"            %% "zhttp"                           % zioHttpVersion,
      "io.d11"            %% "zhttp-test"                      % zioHttpVersion             % Test,
      // config
      "dev.zio"           %% "zio-config"                      % zioConfigVersion,
      "dev.zio"           %% "zio-config-typesafe"             % zioConfigVersion,
      "dev.zio"           %% "zio-config-magnolia"             % zioConfigVersion,
      // json
      "dev.zio"           %% "zio-json"                        % zioJsonVersion,
      // test dependencies
      "dev.zio"           %% "zio-test"                        % zioVersion                 % Test,
      "dev.zio"           %% "zio-test-sbt"                    % zioVersion                 % Test,
      "dev.zio"           %% "zio-test-junit"                  % zioVersion                 % Test,
      "com.dimafeng"      %% "testcontainers-scala-postgresql" % testcontainersScalaVersion % Test,
      "org.testcontainers" % "testcontainers"                  % testcontainersVersion      % Test,
      "org.testcontainers" % "database-commons"                % testcontainersVersion      % Test,
      "org.testcontainers" % "postgresql"                      % testcontainersVersion      % Test,
      "org.testcontainers" % "jdbc"                            % testcontainersVersion      % Test
    ),
    dependencyOverrides += "dev.zio" %% "zio" % zioVersion,
    resolvers ++= Resolver.sonatypeOssRepos("snapshots")
  )
  .settings(testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"))
