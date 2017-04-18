package de.hdm.wim

import scala.util.Random
import com.google.gson.GsonBuilder
import EVENT_TYPE.EVENT_TYPE
import org.joda.time.DateTime
import org.joda.time.DateTimeZone


/**
  * Created by ben on 16/04/2017.
  */

object EVENT_TYPE extends Enumeration {
  type EVENT_TYPE = Value
  val FEEDBACK, REQUEST, TIME, USER = Value
}

class EventLog(
                val userId: Int,
                val eventId: String,
                //val eventSource: String,
                //val eventType: EVENT_TYPE,
                val timestamp: Long
              ) extends Serializable {

  def this(userId: Int, eventId: String) =
    this(userId, eventId, DateTime.now(DateTimeZone.UTC).getMillis)

  def toJson: String = {
    val gson = new GsonBuilder().serializeNulls().create()
    gson.toJson(this)
  }
}

object EventLog {

  val EVENT_TYPES =
    Seq(
      "request",
      "time",
      "action"
    )

  def fromJson(json: String): EventLog = {
    import org.json4s._
    import org.json4s.native.JsonMethods._

    implicit val formats = DefaultFormats
    parse(json,false).extract[EventLog]
  }

  def getDummy(maxUserId: Int = 10): EventLog = {
    val userId = new Random().nextInt(maxUserId) + 1
    val eventId = EVENT_TYPES.apply(new Random().nextInt(3))
    new EventLog(userId, eventId)
  }
}
