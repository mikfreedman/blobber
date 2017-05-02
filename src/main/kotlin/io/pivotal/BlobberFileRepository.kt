package io.pivotal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Component
import java.util.*


@Component
class BlobberFileRepository(val jdbcTemplate : JdbcTemplate, @Value("\${tableName:files}") val tableName : String) {
    fun put(file: ByteArray, contentType: String) : String
    {
        var id = UUID.randomUUID().toString();

        jdbcTemplate.update("INSERT INTO $tableName (id, content_bytes, content_type) VALUES (?, ?, ?)",
                id, file, contentType)

        return id;
    }

    fun  get(id: String) : BlobberFile? {
        val blobberFile = jdbcTemplate.query("SELECT id, content_bytes, content_type FROM $tableName WHERE id = ?", resultSetExtractor, id)
        return blobberFile
    }

    private val resultSetExtractor = ResultSetExtractor { rs ->
        if (rs.next()) {
            BlobberFile(
                    id = rs.getString(1),
                    contentBytes = rs.getBytes(2),
                    contentType = rs.getString(3)
            )
        } else {
            null
        }
    }
}