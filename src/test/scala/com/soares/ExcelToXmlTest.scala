package com.soares

import java.io.File

import org.scalatest.funsuite.AnyFunSuite

class ExcelToXmlTest extends AnyFunSuite {

  test("ExcelToXml getXML") {
    val resourcePath = getClass.getResource("/Formularios.xlsx")
    val excelToXml = new ConvertExcelToXml(new File(resourcePath.getPath))

    val result = excelToXml.convertExcelToXmlString("AES_I")

    if (result.length == 1) {
      println(s"Sheet: ${result(0)._1}")
      println(s"XML: ${result(0)._2}")
    }
  }

  // test("ExcelToXml getXML") {
  //   val resourcePath = getClass.getResource("/Formularios.xlsx")
  //   val excelToXml = new ConvertExcelToXml(new File(resourcePath.getPath))

  //   val result = excelToXml.convertExcelToXmlString

  //   if (result.length > 0) {
  //     result.foreach(r => {
  //       println(s"Sheet: ${r._1}")
  //       println(s"XML: ${r._2}")
  //     })
  //   }
  // }

  // test("ExcelToXml saveFile") {
  //   val resourcePath = getClass.getResource("/Formularios.xlsx")
  //   val excelToXml = new ConvertExcelToXml(new File(resourcePath.getPath))

  //   excelToXml.convertExcelToXmlFile("/tmp", "AES_I")
  // }

  // test("ExcelToXml saveFile") {
  //   val resourcePath = getClass.getResource("/Formularios.xlsx")
  //   val excelToXml = new ConvertExcelToXml(new File(resourcePath.getPath))

  //   excelToXml.convertExcelToXmlFile("/tmp")
  // }

}
