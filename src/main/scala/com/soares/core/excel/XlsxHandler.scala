package com.soares.core.excel

import java.io.{File, FileInputStream}

import org.apache.poi.ss.usermodel.SheetVisibility
import org.apache.poi.xssf.usermodel.XSSFWorkbook

trait XlsxHandler extends TagHandler {

  protected[core] def handleXlsx(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] = {
    val excelBook = new XSSFWorkbook(new FileInputStream(file))
    createXmlBasedOnSheetName(excelBook, sheetName)
  }

}
