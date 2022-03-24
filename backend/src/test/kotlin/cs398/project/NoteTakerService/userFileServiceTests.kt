package cs398.project.NoteTakerService

import cs398.project.NoteTakerService.repo.UserFile
import cs398.project.NoteTakerService.repo.UserFileRepo
import cs398.project.NoteTakerService.repo.UserFiles
import cs398.project.NoteTakerService.service.UserFileService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean


@SpringBootTest
class UserFileServiceTests {

    @MockBean
    private lateinit var repo: UserFileRepo

    @Autowired
    private lateinit var srv: UserFileService

    @Test
    fun testFindEmptyUserFilesByID() {
        val res = srv.findFilesByUserId("123")

        assert(res.isEmpty())
    }

    @Test
    fun testUpdateUserFilesByID() {
        val userID = "1"
        val file = UserFile(
            id = 1,
            userId = userID,
            fileName = "file1",
            folderName = "folder1",
            createdOn = 100L,
            text = "text",
        )
        val files = listOf(file)
        srv.updateUsersFiles(UserFiles(
            files, userID
        ))
    }

    @Test
    fun getUserFilesByUserID(){
        val userID = "1"
        val file = UserFile(
            id = 1,
            userId = userID,
            fileName = "file1",
            folderName = "folder1",
            createdOn = 100L,
            text = "text",
        )
        val files = listOf(file)
        Mockito.`when`(repo.findFilesByUserId(userID)).thenReturn(files)


        val res = srv.findFilesByUserId(userID)


        assert(res.size ==  1)
        assert(res[0] == file)
    }

}
