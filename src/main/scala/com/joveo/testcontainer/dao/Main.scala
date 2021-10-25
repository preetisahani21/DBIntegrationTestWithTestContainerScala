package com.joveo.testcontainer.dao

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object Main extends App {
  val permissionRepo = new PermissionRepo("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false")
  val k = permissionRepo.
    addPermission(
      Permission(
        id = "3",
        name = "DummyPermission3",
        description = "Dummy com.joveo.testcontainer.dao.Permission created for testing",
        createdBy = "Preeti Sahani"
      )
    )
  print(Await.result(k, 10.seconds))

  val name = permissionRepo.getPermissionById("1").map {
    case None => ""
    case Some(permission) => permission.name
  }
  print(Await.result(name, 10.seconds))

}
