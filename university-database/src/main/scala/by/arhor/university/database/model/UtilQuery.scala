package by.arhor.university.database.model

import javax.xml.bind.annotation.XmlAccessType

@xmlRootElement(name = "util")
@xmlAccessorType(XmlAccessType.FIELD)
case class UtilQuery() extends Query
