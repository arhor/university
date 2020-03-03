package by.arhor.university.database.xml

import javax.xml.bind.annotation.XmlAccessType

@xmlRootElement(name = "util")
@xmlAccessorType(XmlAccessType.FIELD)
case class UtilQuery() extends Query
