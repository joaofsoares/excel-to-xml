package com.soares.core.excel

import java.io.File
import java.nio.file.NoSuchFileException

import com.soares.core.FileHandler

abstract class ExcelConverter extends FileHandler {

  protected[soares] def convertToXml(
      file: File,
      outputPath: String,
      sheetName: Option[String]
  ): Unit =
    if (file.exists)
      saveFile(createXml(file, sheetName), outputPath)
    else
      throw new NoSuchFileException("File not found.")

  protected[soares] def convertToXml(
      file: File,
      sheetName: Option[String]
  ): List[(String, String)] =
    if (file.exists)
      createXml(file, sheetName)
    else
      throw new NoSuchFileException("File not found.")

}
