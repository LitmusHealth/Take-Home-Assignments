package com.litmushealth.data

import java.lang.reflect.Field
import com.google.appengine.api.datastore.Entity


abstract class DataRecord {
  var id: Option[Either[Long, String]] = None
  val kind: String
}

object DataRecord {

  // Your code goes here.
  
}
