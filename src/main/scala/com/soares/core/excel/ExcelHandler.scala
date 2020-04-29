package com.soares.core.excel

import java.io.{File, FileInputStream}
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook

abstract class ExcelHandler extends TagHandler {

  protected[core] def handleXlsx(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] = {
    val excelBook = new XSSFWorkbook(new FileInputStream(file))
    createXmlBasedOnSheetName(excelBook, sheetName)
  }

  protected[core] def handleXls(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] = {
    val excelBook = new HSSFWorkbook(new FileInputStream(file))
    createXmlBasedOnSheetName(excelBook, sheetName)
  }

}
