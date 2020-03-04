package by.arhor.university.database.xml

import javax.xml.bind.annotation.XmlAccessType

@xmlRootElement(name = "drop")
@xmlAccessorType(XmlAccessType.FIELD)
case class DropQuery(
  @xmlAttribute var target: String,
) extends Query {
  def this() = this(null)
}
