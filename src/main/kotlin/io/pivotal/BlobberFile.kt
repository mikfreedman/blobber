package io.pivotal

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*


@Entity
data class BlobberFile (@Lob @Basic(fetch = FetchType.LAZY)
                   @Column(length=100000) @JsonIgnore val contentBytes : ByteArray, @Id val id : String, val contentType : String) {
}
