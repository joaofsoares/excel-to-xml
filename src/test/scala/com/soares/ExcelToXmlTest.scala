package com.soares

import java.io.File
import org.scalatest.funsuite.AnyFunSuite

class ExcelToXmlTest extends AnyFunSuite {
  test("ExcelToXml Properties") {
    val excelToXml = new ConvertExcelToXml(new File("Formularios.xlsx"))
  }
}
