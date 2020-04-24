package com.soares.core.excel

import java.io.File
import java.io.FileInputStream

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.ss.usermodel.SheetVisibility

trait XlsHandler extends TagHandler {

  protected[core] def handleXls(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] = {
    val excelBook = new HSSFWorkbook(new FileInputStream(file))
    createXmlBasedOnSheetName(excelBook, sheetName)
  }

  private def createXmlBasedOnSheetName(
      excelBook: HSSFWorkbook,
      sheetName: Option[String]
  ): List[(String, String)] =
    sheetName match {
      case None =>
        val visibleSheets = getVisibleSheets(excelBook)
        visibleSheets map (vs => processSheet(vs._1, vs._2))
      case Some(s) => List(processSheet(s, excelBook.getSheet(s)))
    }

  private def getVisibleSheets(
      excelBook: HSSFWorkbook
  ): List[(String, HSSFSheet)] =
    (0 until excelBook.getNumberOfSheets())
      .filter(i => excelBook.getSheetVisibility(i) == SheetVisibility.VISIBLE)
      .map(i => (excelBook.getSheetName(i), excelBook.getSheetAt(i)))
      .toList

  private def processSheet(
      sheetName: String,
      sheet: HSSFSheet
  ): (String, String) = {
    val uniqueNode = getUniqueNode(sheet)

    val mapRow = createMapRow(sheet)

    val xml = buildTags(uniqueNode, mapRow)

    (sheetName, xml)
  }

  private def getUniqueNode(sheet: HSSFSheet): List[String] =
    (1 until sheet.getLastRowNum())
      .map(i => sheet.getRow(i).getCell(0).getStringCellValue())
      .distinct
      .toList

  private def createMapRow(
      sheet: HSSFSheet
  ): List[(HSSFCell, HSSFCell, HSSFCell)] =
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
      mapRow: List[(HSSFCell, HSSFCell, HSSFCell)]
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
