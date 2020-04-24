package com.soares.core.excel

import java.io.File
import java.io.FileInputStream

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

abstract class ExcelHandler {

  protected[core] def handleXlsx(file: File): String = {
    val myExcelBook = new XSSFWorkbook(new FileInputStream(file))
    ""
  }

  protected[core] def handleXls(file: File): String = {
    val myExcelBook = new HSSFWorkbook(new FileInputStream(file));
    ""
  }

}

// https://javarevisited.blogspot.com/2015/06/how-to-read-write-excel-file-java-poi-example.html
