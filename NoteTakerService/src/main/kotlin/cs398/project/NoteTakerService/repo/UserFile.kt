package cs398.project.NoteTakerService.repo

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.CrudRepository
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.query.Param

interface UserFileRepo : CrudRepository<UserFile, Int> {
    @Query("SELECT * FROM UserFiles WHERE userID = :userID")
    fun findFilesByUser(
        @Param("userID") userId: String
    ): List<UserFile>
}

@Table("USERFILES")
data class UserFile (
    @Id val fileID: String?,
    val userID: String,
    val createdOn: Long,
    val text: String,
)