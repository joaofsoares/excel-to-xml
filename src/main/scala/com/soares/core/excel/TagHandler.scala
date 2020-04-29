package com.soares.core.excel

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.{Workbook, Sheet, Cell, SheetVisibility}

abstract class TagHandler {

  private def openTag(s: String) = s"<$s>"

  private def closeTag(s: String) = s"</$s>"

  protected[excel] def createXmlBasedOnSheetName(
      excelBook: Workbook,
      sheetName: Option[String]
  ): List[(String, String)] =
    sheetName match {
      case None =>
        val visibleSheets = getVisibleSheets(excelBook)
        visibleSheets map processSheet
      case Some(s) => List(processSheet(excelBook.getSheet(s)))
    }

  private def getVisibleSheets[A](
      excelBook: Workbook
  ): List[Sheet] =
    (0 until excelBook.getNumberOfSheets)
      .filter(i => excelBook.getSheetVisibility(i) == SheetVisibility.VISIBLE)
      .map(i => excelBook.getSheetAt(i))
      .toList

  private def processSheet(
      sheet: Sheet
  ): (String, String) = {

    val uniqueNode = getUniqueNode(sheet)

    val mapRow = createMapRow(sheet)

    val xml = buildTags(uniqueNode, mapRow)

    (sheet.getSheetName, xml)
  }

  private def getUniqueNode(sheet: Sheet): List[String] =
    (2 to sheet.getLastRowNum)
      .map(i => sheet.getRow(i).getCell(0).toString)
      .filter(node => node != null || node.toString().isEmpty)
      .distinct
      .toList

  private def createMapRow(
      sheet: Sheet
  ): List[(Cell, Cell, Cell)] =
    (2 to sheet.getLastRowNum)
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
      mapRow: List[(Cell, Cell, Cell)]
  ): String =
    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
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
