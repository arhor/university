package by.arhor.university.database.model

import java.io.Serializable

import javax.xml.bind.annotation._

@xmlRootElement(name = "module")
@xmlAccessorType(XmlAccessType.FIELD)
case class Module(
    @xmlAttribute var name: String,
    @xmlElement   var dependencies: Dependencies,
    @xmlElement   var queries: Queries,
    @xmlTransient var resolved: Boolean,
    @xmlTransient var executionCost: Int
) extends Serializable with Comparable[Module] {
  def this() = this(null, null, null, false, 0)

  override def compareTo(that: Module): Int = this.executionCost - that.executionCost
}
