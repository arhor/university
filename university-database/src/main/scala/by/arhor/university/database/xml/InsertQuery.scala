package by.arhor.university.database.xml

import javax.xml.bind.annotation.XmlAccessType

@xmlRootElement(name = "insert")
@xmlAccessorType(XmlAccessType.FIELD)
case class InsertQuery() extends Query
