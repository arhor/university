package by.arhor.university.database.xml

import java.io.Serializable

import javax.xml.bind.annotation._

@xmlTransient
@xmlAccessorType(XmlAccessType.FIELD)
abstract class Query(
  @xmlAttribute var context: String,
  @xmlValue     var content: String,
) extends Serializable {
  def this() = this(null, null)
}
