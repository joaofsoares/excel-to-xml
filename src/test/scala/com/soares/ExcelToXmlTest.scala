package com.soares

import java.io.File

import org.scalatest.funsuite.AnyFunSuite

class ExcelToXmlTest extends AnyFunSuite {

  test("ExcelToXml - with file and sheet") {
    val resourcePath = getClass.getResource("/file_test.xlsx")
    val excelToXml = new ConvertExcelToXml(new File(resourcePath.getPath))

    val result = excelToXml.convertExcelToXmlString("AES_I")

    assert(result.length == 1)

    if (result.length == 1) {

      val sheetName = result(0)._1
      assert(!sheetName.isEmpty)

      val xml = result(0)._2
      assert(!xml.isEmpty)
    }
  }

  test("ExcelToXml - with file and for all sheets") {
    val resourcePath = getClass.getResource("/file_test.xlsx")
    val excelToXml = new ConvertExcelToXml(new File(resourcePath.getPath))

    val result = excelToXml.convertExcelToXmlString

    assert(result.length > 0)

    if (result.length > 0) {
      result.foreach(r => {

        val sheetName = r._1
        assert(!sheetName.isEmpty)

        val xml = r._2
        assert(!xml.isEmpty)
      })
    }
  }

  test("ExcelToXml - with file, directory and sheet saving in /tmp") {
    val resourcePath = getClass.getResource("/file_test.xlsx")
    val excelToXml = new ConvertExcelToXml(new File(resourcePath.getPath))

    excelToXml.convertExcelToXmlFile("src/test/resources/output", "AES_I")
  }

  test("ExcelToXml saveFile") {
    val resourcePath = getClass.getResource("/file_test.xlsx")
    val excelToXml = new ConvertExcelToXml(new File(resourcePath.getPath))

    excelToXml.convertExcelToXmlFile("src/test/resources/output")
  }

}
