package by.arhor.university.database

import java.util.function.Consumer

import by.arhor.university.core.pattern.composite.{Composite, Leaf, Node}
import by.arhor.university.database.xml.{Dependency, Module}

final case class Parser(private val modules: Set[Module]) extends Composite[Module] {

  modules.foreach { case module if !module.resolved =>

  }

  private def resolve(module: Module): Unit = if (!module.resolved) {
    module.forEachDependency { case Dependency(name) =>
      val nested = findModule(name)
      resolve(nested)
    }
    module.resolved = true
  }

  private def findModule(name: String): Module = {
    val xs = modules.filter(_.name == name)
    if (xs.isEmpty)  throw new RuntimeException(s"there is no satisfying dependency: [$name]")
    if (xs.size > 1) throw new RuntimeException(s"multiple modules with name: [$name]")
    xs.head
  }

  private def parse(module: Module): Composite[Module] = {
    if (module.dependencies == null) {
      Leaf.leaf(module)
    } else {
      val node: Node[Module] = Node.node()
      module.forEachDependency { case Dependency(name) =>
        val xs = modules.filter(_.name == name)
        if (xs.isEmpty)  throw new RuntimeException(s"there is no satisfying dependency: [$name]")
        if (xs.size > 1) throw new RuntimeException(s"multiple modules with name: [$name]")
        node.add(parse(xs.head))
      }
      node
    }
  }

  override def execute(action: Consumer[Module]): Unit = ???
}
