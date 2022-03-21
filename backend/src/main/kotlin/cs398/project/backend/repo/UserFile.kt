package cs398.project.NoteTakerService.repo

import org.springframework.data.annotation.Id
import org.springframework.data.jpa.repository.Query
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserFileRepo : CrudRepository<UserFile, Int> {
    @Query("SELECT * FROM UserFiles WHERE userId = :userId")
    fun findFilesByUserId(
        @Param("userId") userId: String
    ): List<UserFile>
}

@Table("USERFILES")
data class UserFile (
    @Id val fileId: String?,
    val userId: String,
    val createdOn: Long,
    val text: String,
)