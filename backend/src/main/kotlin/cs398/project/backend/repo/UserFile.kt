package cs398.project.NoteTakerService.repo

import org.springframework.context.annotation.Primary
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserFileRepo : CrudRepository<UserFile, Int> {
    @Query("SELECT * FROM USERFILES WHERE user_Id = :Id")
    fun findFilesByUserId(
        @Param("Id") userId: String
    ): List<UserFile>

    @Modifying
    @Query("DELETE FROM USERFILES WHERE user_Id = :Id")
    fun deleteUserFiles(
        @Param("Id") userId: String
    )
}

data class UserFiles (
    val files: List<UserFile>,
    val userId: String,
)

@Table("USERFILES")
data class UserFile (
    @Id
    val id: Int,
    @Column
    val userId: String,
    @Column
    val fileName: String,
    @Column
    val folderName: String,
    @Column
    val createdOn: Long,
    @Column
    val text: String,
)