package io.pivotal

import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import javax.servlet.http.HttpServletResponse


@Controller
@RequestMapping(path = arrayOf("/files"))
class MainController(val blobberFileRepository: BlobberFileRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create(@RequestParam("file") file: MultipartFile): ResponseEntity<Map<String, String>> {
        var uuid = blobberFileRepository.put(file.bytes, file.contentType)
        val map: HashMap<String, String> = hashMapOf("uuid" to uuid)

        if (uuid != null) {
            return ResponseEntity(map, HttpStatus.CREATED)

        } else {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun get(@PathVariable("id") id: String, response: HttpServletResponse) {
        val blobberFile = blobberFileRepository.get(id)
        if(blobberFile != null) {
            IOUtils.copy(ByteArrayInputStream(blobberFile.contentBytes), response.outputStream)
            response.contentType = blobberFile.contentType
        }
    }
}