package com.solly.commonspring.util

import org.springframework.web.multipart.MultipartFile
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.apply
import kotlin.collections.forEachIndexed
import kotlin.collections.isNotEmpty
import kotlin.collections.map
import kotlin.collections.toTypedArray
import kotlin.jvm.Throws

class FileUtil(
    private val dirPath: String
) {

    private val path: Path by lazy {
        Paths.get(dirPath).toAbsolutePath().normalize()
    }

    /**
     * 멀티 파트 파일 여러개 저장
     */
    @Throws(IOException::class)
    fun saveMultipartFileList(_saveFileName: String, _files: List<MultipartFile>) {
        createDirectories()

        _files.forEachIndexed { index, multipartFile ->
            val saveName = "${_saveFileName}_$index"
            val ext = getFileExt(multipartFile.contentType)

            multipartFile
                .inputStream
                .copyFile(getFileAbsolutePath("$saveName.$ext"))
        }
    }

    /**
     * 외부 URL의 파일을 저장
     * REPLACE_EXISTING : 덮어쓰기 허용
     */
    @Throws(IOException::class)
    fun saveUrlFile(_url: String, _fileName: String) {
        createDirectories()

        URL(_url)
            .openStream()
            .copyFile(getFileAbsolutePath(_fileName))
    }

    @Throws(IOException::class)
    fun writeTextFile(_fileName: String, text: String) {
        createDirectories()

        val file = getFile(_fileName)
        var isCreated = false

        if (!file.exists()) {
            file.createNewFile()
            isCreated = true
        }

        BufferedWriter(FileWriter(file, true)).apply {
            if (!isCreated) {
                newLine()
            }
            append(text)
            close()
        }
    }

    /**
     * 파일 및 하위 폴더 삭제
     */
    fun deleteFiles(_fileName: String): Boolean {
        val file = getFile(_fileName)
        val fileCount = searchDirFileList(file)
        return if (fileCount.isNotEmpty()) {
            deleteDir(file)
        } else {
            true
        }
    }

    /**
     * 해당 디렉토리에 파일 리스트 조회
     */
    fun getDirectoryFiles(): Array<out File> {
        return path.toFile().listFiles() ?: arrayOf()
    }

    /**
     * 경로에 해당하는 폴더가 없으면 생성한다.
     */
    @Throws(IOException::class)
    private fun createDirectories() {
        Files.createDirectories(path)
    }

    /**
     * 파일 삭제 재귀함수
     */
    private fun deleteDir(_dir: File): Boolean {
        if (_dir.isDirectory) {
            val children = _dir.list()
            for (i in children) {
                val success = deleteDir(File(_dir, i))
                if (!success) {
                    return false
                }
            }
        }

        return _dir.delete()
    }

    /**
     * 특정 폴더 내의 파일 리스트 조회
     */
    private fun searchDirFileList(dir: File): Array<String> {
        return dir.listFiles()?.map { it.path }?.toTypedArray() ?: arrayOf()
    }

    /**
     * 상대 경로에 있는 파일 가져오기
     */
    private fun getFile(_fileName: String): File = getFileAbsolutePath(_fileName).toFile()

    private fun getFileAbsolutePath(_fileName: String): Path = path.resolve(_fileName)

    /**
     * 파일 확장자
     */
    private fun getFileExt(contentType: String?): String {
        return when (contentType) {
            "image/png" -> "png"
            "image/jpeg", "image/jpg" -> "jpg"
            "image/gif" -> "gif"
            "video/mp4" -> "mp4"
            "application/pdf" -> "pdf"
            "image/svg+xml" -> "svg"
            "application/zip" -> "zip"
            "audio/mpeg" -> "mp3"
            else -> ""
        }
    }

    @Throws(IOException::class)
    private fun InputStream.copyFile(_path: Path, _option: StandardCopyOption = StandardCopyOption.REPLACE_EXISTING) {
        Files.copy(this, _path, _option)
    }
}
