package com.soares.core.excel

abstract class TagHandler {
  protected[excel] def openTag(s: String) = s"<$s>"
  protected[excel] def closeTag(s: String) = s"</$s>"
}
