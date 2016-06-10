package de.unifreiburg.cs.proglang.jgs.jimpleutils

import de.unifreiburg.cs.proglang.jgs.constraints.TypeDomain
import de.unifreiburg.cs.proglang.jgs.constraints.TypeDomain._
import de.unifreiburg.cs.proglang.jgs.jimpleutils.Casts.{CxCast, ValueCast}
import de.unifreiburg.cs.proglang.jgs.jimpleutils.CastsFromMapping._
import de.unifreiburg.cs.proglang.jgs.signatures.parse.AnnotationParser
import soot.SootMethod
import soot.jimple.StaticInvokeExpr

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

import CastUtils._


/**
  * Create a cast module from mappings of fully qualified method names to casts
  */
case class CastsFromMapping[Level](valueCasts: Map[String, Conversion[Level]],
                                   cxCastBegins: Map[String, Conversion[Level]],
                                   cxCastEnd: String)
  extends Casts[Level] {

  override def detectValueCastFromCall(e: StaticInvokeExpr): Option[ValueCast[Level]] = {
    val key = e.getMethod.toString
    valueCasts.get(key)
      .map(c => new ValueCast[Level](c.source, c.dest, getCallArgument(e))
    )
  }

  override def detectContextCastEndFromCall(e: StaticInvokeExpr): Boolean = {
    val key = e.getMethod.toString
    key == cxCastEnd
  }

  override def detectContextCastStartFromCall(e: StaticInvokeExpr): Option[CxCast[Level]] = {
    val key = e.getMethod.toString
    cxCastBegins.get(key)
      .map(c => new CxCast[Level](c.source, c.dest))
  }
}

object CastsFromMapping {

  def apply[Level](valueCasts: java.util.Map[String, Conversion[Level]],
                   cxCastBegins: java.util.Map[String, Conversion[Level]],
                   cxCastEnd: String) : CastsFromMapping[Level] = new CastsFromMapping[Level](valueCasts.asScala.toMap, cxCastBegins.asScala.toMap, cxCastEnd)

  private def getCallArgument(e: StaticInvokeExpr): Option[Var[_]] = {
    if (e.getArgCount != 1) {
      throw new IllegalArgumentException(s"Illegal parameter count on cast method `${e}'. Expected single argument method.")
    }
    Vars.getAll(e.getArg(0)).find(_ => true)
  }

}
