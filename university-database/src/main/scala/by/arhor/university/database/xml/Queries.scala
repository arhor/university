package by.arhor.university.database.xml

import java.io.Serializable
import java.util
import java.util.function.Consumer

import javax.xml.bind.annotation.XmlAccessType

@xmlRootElement(name = "queries")
@xmlAccessorType(XmlAccessType.FIELD)
case class Queries(

    @xmlAttribute
    var context: String,

    // annotations passed as annotation arguments should be used as regular type
    @xmlElements(Array(
        new xmlElement(name = "drop",   `type` = classOf[DropQuery]),
        new xmlElement(name = "create", `type` = classOf[CreateQuery]),
        new xmlElement(name = "util",   `type` = classOf[UtilQuery]),
        new xmlElement(name = "insert", `type` = classOf[InsertQuery])))
    var list: util.List[Query]

) extends Serializable {

  def this() = this(null, null)

  def forEach(queryConsumer: Consumer[Query]): Unit = {
    if (list != null) list.forEach(queryConsumer)
  }
}
