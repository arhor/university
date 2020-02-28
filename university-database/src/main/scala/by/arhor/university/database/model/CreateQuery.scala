package by.arhor.university.database.model

import javax.xml.bind.annotation.XmlAccessType

@xmlRootElement(name = "create")
@xmlAccessorType(XmlAccessType.FIELD)
case class CreateQuery(
    @xmlAttribute var target: String
) extends Query {
  def this() = this(null)
}
