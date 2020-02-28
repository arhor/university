package by.arhor.university.database

import javax.xml.bind.annotation.{XmlAccessorType, XmlAttribute, XmlElement, XmlElements, XmlRootElement, XmlTransient, XmlValue}

import scala.annotation.meta.{companionClass, field}

// required to make XML annotations visible for JAXB context
package object model {
  type xmlAccessorType = XmlAccessorType @ companionClass
  type xmlRootElement  = XmlRootElement  @ companionClass
  type xmlAttribute    = XmlAttribute    @ field
  type xmlTransient    = XmlTransient    @ field
  type xmlElements     = XmlElements     @ field
  type xmlElement      = XmlElement      @ field
  type xmlValue        = XmlValue        @ field
}
