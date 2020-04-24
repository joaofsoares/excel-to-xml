package com.soares

import java.io.File

import com.soares.core.excel.ExcelConverter

class ConvertExcelToXml(file: File) extends ExcelConverter {

  def convertExcelToXmlFile(
      outputPath: String,
      sheetName: Option[String]
  ): Unit =
    convertToXml(file, outputPath, sheetName)

  def convertExcelToXmlFile(outputPath: String): Unit =
    convertExcelToXmlFile(outputPath, None)

  def convertExcelToXmlString(
      sheetName: Option[String]
  ): List[(String, String)] =
    convertToXml(file, sheetName)

  def convertExcelToXmlString(): List[(String, String)] =
    convertExcelToXmlString(None)

}
