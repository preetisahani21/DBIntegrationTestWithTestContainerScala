package com.joveo.testcontainer.dao

import org.mongodb.scala._
import org.mongodb.scala.model.Filters._

import scala.concurrent.{ExecutionContext, Future}

class PermissionRepo(mongoUrl: String)(implicit ec: ExecutionContext) extends Mongo {

  override def mongoUri: String = mongoUrl

  private val NAME_FIELD = "name"
  private val ID_FIELD   = "_id"

  def getPermissionByName(
                                    permissionName: String
                                  ): Future[Option[Permission]] = {
    permissionsCollection.find(equal(NAME_FIELD, permissionName)).headOption()
  }

  def getPermissionById(id: String): Future[Option[Permission]] = {
    permissionsCollection.find(equal(ID_FIELD, id)).headOption()
  }

  def addPermission(permission: Permission): Future[String] = {
    for {
      _ <- permissionsCollection.insertOne(permission).toFuture()
    } yield permission.id
  }

  def addPermissions(
                               permissions: List[Permission]
                             ): Future[Boolean] = {
    permissionsCollection.insertMany(permissions).toFuture().map(_ => true)
  }

  def getPermissionsByNames(
                             permissionNames: Seq[String]
                           ): Future[Seq[Permission]] = {
    permissionsCollection.find(in(NAME_FIELD, values = permissionNames: _*)).toFuture()
  }
}
