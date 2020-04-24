package com.soares.core.excel

import java.io.File
import java.io.FileInputStream

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.ss.usermodel.SheetVisibility

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
        visibleSheets map (vs => processSheet(vs._1, vs._2))
      case Some(s) => List(processSheet(s, excelBook.getSheet(s)))
    }

  private def getVisibleSheets(
      excelBook: XSSFWorkbook
  ): List[(String, XSSFSheet)] =
    (0 until excelBook.getNumberOfSheets())
      .filter(i => excelBook.getSheetVisibility(i) == SheetVisibility.VISIBLE)
      .map(i => (excelBook.getSheetName(i), excelBook.getSheetAt(i)))
      .toList

  private def processSheet(
      sheetName: String,
      sheet: XSSFSheet
  ): (String, String) = {
    val uniqueNode = getUniqueNode(sheet)

    val mapRow = createMapRow(sheet)

    val xml = buildTags(uniqueNode, mapRow)

    (sheetName, xml)
  }

  private def getUniqueNode(sheet: XSSFSheet): List[String] =
    (1 until sheet.getLastRowNum())
      .map(i => sheet.getRow(i).getCell(0).getStringCellValue())
      .distinct
      .toList

  private def createMapRow(
      sheet: XSSFSheet
  ): List[(XSSFCell, XSSFCell, XSSFCell)] =
    (1 until sheet.getLastRowNum())
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
      .map(un => {
        val nodeFields = mapRow.filter(mr => un == mr._1.getStringCellValue())
        openTag(un) ::
          nodeFields.map(nf => {
            val k = nf._2.getStringCellValue()
            val v = nf._3.getStringCellValue()
            s"<$k>$v</$k>"
          }) ::
          closeTag(un) :: Nil
      })
      .mkString("\n")

}
