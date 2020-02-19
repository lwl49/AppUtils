package com.allen.apputils.utils

import java.io.File
import java.util.Comparator

/**
 * @author Allen
 * @date 2019/11/15 17:16
 * @describe
 */
class FileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.name.compareTo(file2.name)
    }
}
