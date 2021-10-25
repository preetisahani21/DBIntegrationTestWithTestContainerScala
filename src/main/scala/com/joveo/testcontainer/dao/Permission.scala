package com.joveo.testcontainer.dao

import org.mongodb.scala.bson.annotations.BsonProperty

/** @param id
 * _id
 * @param name
 * Name of permission, should be unique
 * @param description
 * Brief description of permission
 * @param isAllowed
 * If false, permission is negative permission
 * @param createdBy
 * Creator of the permission, by default , it is System
 */
case class Permission(
                       @BsonProperty("_id") id: String,
                       name: String,
                       description: String,
                       isAllowed: Boolean = true,
                       createdBy: String = "System"
                     )
