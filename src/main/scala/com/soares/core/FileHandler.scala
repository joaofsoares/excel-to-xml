package com.soares.core

import java.io.File

import com.soares.core.excel.ExcelHandler

abstract class FileHandler extends ExcelHandler {

  protected[core] def createXml(file: File): String =
    if (isXlsx(file.getName))
      createXmlFromXlsx(file)
    else
      createXmlFromXls(file)

  protected[core] def saveFile(xml: String, outputPath: String): Unit = ???

  private def isXlsx(fileName: String): Boolean =
    fileName.endsWith(".xlsx")

  private def createXmlFromXlsx(file: File): String =
    handleXlsx(file)

  private def createXmlFromXls(file: File): String =
    handleXls(file)

}
