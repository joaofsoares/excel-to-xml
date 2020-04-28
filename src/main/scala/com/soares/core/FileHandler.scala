package com.soares.core

import java.io.{File, FileWriter}

import com.soares.core.excel.ExcelHandler

abstract class FileHandler extends ExcelHandler {

  protected[core] def createXml(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] =
    if (isXlsx(file.getName))
      createXmlFromXlsx(file, sheetName)
    else
      createXmlFromXls(file, sheetName)

  protected[core] def saveFile(
      xml: List[(String, String)],
      outputPath: String
  ): Unit = {
    xml.foreach(x => {
      val outputFile =
        new FileWriter(s"$outputPath/${x._1}_${System.currentTimeMillis}.xml")
      outputFile.write(x._2)
      outputFile.close
    })
  }

  private def isXlsx(fileName: String): Boolean =
    fileName.endsWith(".xlsx")

  private def createXmlFromXlsx(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] =
    handleXlsx(file, sheetName)

  private def createXmlFromXls(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] =
    handleXls(file, sheetName)

}
