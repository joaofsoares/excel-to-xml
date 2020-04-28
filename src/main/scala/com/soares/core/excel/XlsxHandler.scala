package com.soares.core.excel

import java.io.{File, FileInputStream}

import org.apache.poi.ss.usermodel.SheetVisibility
import org.apache.poi.xssf.usermodel.{XSSFCell, XSSFSheet, XSSFWorkbook}

trait XlsxHandler extends TagHandler {

  protected[core] def handleXlsx(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] = {

    val excelBook = new XSSFWorkbook(new FileInputStream(file))

    createXmlBasedOnSheetName(excelBook, sheetName)
  }

  private def createXmlBasedOnSheetName(
      excelBook: XSSFWorkbook,
      sheetName: Option[String]
  ): List[(String, String)] =
    sheetName match {
      case None =>
        val visibleSheets = getVisibleSheets(excelBook)
        visibleSheets map processSheet
      case Some(s) => List(processSheet(excelBook.getSheet(s)))
    }

  private def getVisibleSheets(
      excelBook: XSSFWorkbook
  ): List[XSSFSheet] =
    (0 until excelBook.getNumberOfSheets)
      .filter(i => excelBook.getSheetVisibility(i) == SheetVisibility.VISIBLE)
      .map(i => excelBook.getSheetAt(i))
      .toList

  private def processSheet(
      sheet: XSSFSheet
  ): (String, String) = {

    val uniqueNode = getUniqueNode(sheet)

    val mapRow = createMapRow(sheet)

    val xml = buildTags(uniqueNode, mapRow)

    (sheet.getSheetName, xml)
  }

  private def getUniqueNode(sheet: XSSFSheet): List[String] =
    (2 to sheet.getLastRowNum)
      .map(i => sheet.getRow(i).getCell(0).toString)
      .filter(node => node != null || node.toString().isEmpty)
      .distinct
      .toList

  private def createMapRow(
      sheet: XSSFSheet
  ): List[(XSSFCell, XSSFCell, XSSFCell)] =
    (2 until sheet.getLastRowNum)
      .map(i =>
        (
          sheet.getRow(i).getCell(0),
          sheet.getRow(i).getCell(1),
          sheet.getRow(i).getCell(2)
        )
      )
      .toList

  private def buildTags(
      uniqueNode: List[String],
      mapRow: List[(XSSFCell, XSSFCell, XSSFCell)]
  ): String =
    uniqueNode
      .flatMap(un => {
        val nodeFields = mapRow.filter(mr => un == mr._1.toString)
        openTag(un) ::
          nodeFields
            .map(nf => {
              val k = nf._2.toString
              val v = nf._3.toString
              s"\t<$k>$v</$k>"
            })
            .mkString("\n") ::
          closeTag(un) :: Nil
      })
      .mkString("\n")

}
