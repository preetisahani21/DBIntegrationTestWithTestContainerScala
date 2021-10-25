package com.joveo.testcontainer.dao

// Copyright (C) 2017-2021 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistries._
import org.bson.codecs.{Codec, DecoderContext, EncoderContext}
import org.bson.{BsonReader, BsonWriter}
import org.joda.time.DateTime
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}

import java.util.Date

trait MongoTrait {

  private def customCodeRegistry() = {
    fromProviders(
      Macros.createCodecProviderIgnoreNone[Permission]()
    )
  }


  private val mongoClient: MongoClient    = MongoClient(mongoUri)
  protected val customCodecRegistry =
    CodecRegistries.fromCodecs(new JodaDateTimeCodec, new JavaDateCodec)

  private val codecRegistry = fromRegistries(
    customCodeRegistry(),
    DEFAULT_CODEC_REGISTRY,
    customCodecRegistry
  )

  private val mongoDB: MongoDatabase =
    mongoClient.getDatabase("ums").withCodecRegistry(codecRegistry)

  val permissionsCollection: MongoCollection[Permission] =
    mongoDB.getCollection[Permission]("permissions")


  class JodaDateTimeCodec extends Codec[DateTime] {
    override def encode(
                         writer: BsonWriter,
                         value: DateTime,
                         encoderContext: EncoderContext
                       ): Unit = {
      writer.writeDateTime(value.getMillis)
    }

    override def getEncoderClass: Class[DateTime] = classOf[DateTime]

    override def decode(
                         reader: BsonReader,
                         decoderContext: DecoderContext
                       ): DateTime = {
      new DateTime(reader.readDateTime())
    }
  }

  class JavaDateCodec extends Codec[Date] {
    override def encode(
                         writer: BsonWriter,
                         value: Date,
                         encoderContext: EncoderContext
                       ): Unit = {
      writer.writeDateTime(value.getTime)
    }

    override def getEncoderClass: Class[Date] = classOf[Date]

    override def decode(
                         reader: BsonReader,
                         decoderContext: DecoderContext
                       ): Date = {
      new Date(reader.readDateTime())
    }
  }
  def mongoUri: String = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false"

  def getPermissionCollectionName: String

}

class Mongo extends MongoTrait {
  override def mongoUri: String = {
    "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false"
  }
  override def getPermissionCollectionName: String = "permissions"

}
