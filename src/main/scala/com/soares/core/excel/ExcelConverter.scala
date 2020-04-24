package com.soares.core.excel

import java.io.File
import java.nio.file.NoSuchFileException

import com.soares.core.FileHandler

abstract class ExcelConverter extends FileHandler {

  protected[soares] def convertToXml(file: File, outputPath: String): Unit = {
    if (file.exists) {
      saveFile(createXml(file), outputPath)
    } else
      throw new NoSuchFileException("File not found.")
  }

  protected[soares] def convertToXml(file: File): String = {
    if (file.exists)
      createXml(file)
    else
      throw new NoSuchFileException("File not found.")
  }

}
