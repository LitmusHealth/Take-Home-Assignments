package com.litmushealth.data.test

import com.google.appengine.api.datastore.Entity
import com.google.appengine.tools.development.testing.{LocalServiceTestHelper, LocalDatastoreServiceTestConfig}
import com.litmushealth.data.DataRecord
import org.scalatest.{BeforeAndAfter, FlatSpec}

class DataRecordSpec extends FlatSpec with BeforeAndAfter {
  final val helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())

  before {
    helper.setUp()
  }

  after {
    helper.tearDown()
  }

  case class SimpleRecord(a: String, b: Int) extends DataRecord {
    override val kind = "Simple"
  }

  // This method forces the compiler to look for implicit conversions that
  // can be applied to any parameter passed to it, that is not an Entity
  def takesAnEntity(e: Entity): Entity = e

  lazy val SimpleRecordWithoutID = SimpleRecord("foo", 10)

  lazy val SimpleRecordWithLongId: SimpleRecord = {
    val sr = SimpleRecord("foo", 10)
    sr.id = Some(Left(1000000000))
    sr
  }

  lazy val SimpleRecordWithStringId: SimpleRecord = {
    val sr = SimpleRecord("foo", 10)
    sr.id = Some(Right("StringKey"))
    sr
  }

  "A SimpleRecord without an ID" should "implicitly convert to an Entity" in {
    val e = takesAnEntity(SimpleRecordWithoutID)
    assert(e.getKind == "Simple")
    assert(e.getProperty("a").asInstanceOf[String] == "foo")
    assert(e.getProperty("b").asInstanceOf[Int] == 10)
    assert(e.getProperties.keySet().size == 2)
    assert(!e.getKey.isComplete)
  }

  "A SimpleRecord with a Long ID" should "implicitly convert to an Entity" in {
    val e = takesAnEntity(SimpleRecordWithLongId)
    assert(e.getKind == "Simple")
    assert(e.getProperty("a").asInstanceOf[String] == "foo")
    assert(e.getProperty("b").asInstanceOf[Int] == 10)
    assert(e.getProperties.keySet().size == 2)
    assert(e.getKey.getId == 1000000000)
  }

  "A SimpleRecord with a String name" should "implicitly convert to an Entity" in {
    val e = takesAnEntity(SimpleRecordWithStringId)
    assert(e.getKind == "Simple")
    assert(e.getProperty("a").asInstanceOf[String] == "foo")
    assert(e.getProperty("b").asInstanceOf[Int] == 10)
    assert(e.getProperties.keySet().size == 2)
    assert(e.getKey.getName == "StringKey")
  }
}
