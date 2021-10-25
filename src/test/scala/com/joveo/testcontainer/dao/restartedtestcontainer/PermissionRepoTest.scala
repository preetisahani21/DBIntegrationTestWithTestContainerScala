package com.joveo.testcontainer.dao.restartedtestcontainer

import com.dimafeng.testcontainers.{ForAllTestContainer, MongoDBContainer}
import com.joveo.testcontainer.dao.{Permission, PermissionRepo}
import org.scalatest.{AsyncFreeSpec, Matchers}
import org.testcontainers.utility.DockerImageName



class PermissionRepoTest extends AsyncFreeSpec with ForAllTestContainer with Matchers {
  override val container: MongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
  container.start()
  val permissionRepo = new PermissionRepo(container.replicaSetUrl)
//  container.host
//  container.exposedPorts

  override def beforeStop(): Unit = {
    // your code
  }

  override def afterStart(): Unit = {
    // your code
    permissionRepo.addPermission(
      Permission(
        id = "1",
        name = "DummyPermission1",
        description = "Dummy Permission created for testing",
        createdBy = "Preeti Sahani"
      )
    )
  }


  "getPermissionById from PermissionRepo" - {
    "when given permission id" - {
      "should successfully return permission object" in {
        print("gaaa")
        print(container.replicaSetUrl)
        print("baaa")
        val resFut           = permissionRepo.getPermissionById("1")

        resFut.map(
          res => res should be(
            Some(
              Permission(
                id = "1",
                name = "DummyPermission1",
                description = "Dummy Permission created for testing",
                createdBy = "Preeti Sahani"
              )
            )
          )
        )
      }
    }
  }

  "getPermissionById from PermissionRepo" - {
    "when given permission id second time" - {
      "should successfully return permission object" in {

        val resFut           = permissionRepo.getPermissionById("1")

        resFut.map(
          res => res should be(
            Some(
              Permission(
                id = "1",
                name = "DummyPermission1",
                description = "Dummy Permission created for testing",
                createdBy = "Preeti Sahani"
              )
            )
          )
        )
      }
    }
  }

}

