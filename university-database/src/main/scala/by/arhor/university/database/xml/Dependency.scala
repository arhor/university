package by.arhor.university.database.xml

import java.io.Serializable

import javax.xml.bind.annotation.XmlAccessType

@xmlRootElement(name = "dependency")
@xmlAccessorType(XmlAccessType.FIELD)
case class Dependency(
    @xmlAttribute var name: String
) extends Serializable {
  def this() = this(null)
}
