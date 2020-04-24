package com.soares

import java.io.File

import com.soares.core.excel.ExcelConverter

class ConvertExcelToXml(file: File) extends ExcelConverter {

  def convertExcelToXmlFile(outputPath: String): Unit = {
    convertToXml(file, outputPath)
  }

  def convertExcelToXmlString(): String = {
    convertToXml(file)
  }

}
