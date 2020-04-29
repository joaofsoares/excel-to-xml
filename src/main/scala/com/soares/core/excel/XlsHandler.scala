package com.soares.core.excel

import java.io.{File, FileInputStream}

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.SheetVisibility

trait XlsHandler extends TagHandler {

  protected[core] def handleXls(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] = {
    val excelBook = new HSSFWorkbook(new FileInputStream(file))
    createXmlBasedOnSheetName(excelBook, sheetName)
  }

}
