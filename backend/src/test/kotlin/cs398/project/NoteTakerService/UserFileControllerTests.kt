package cs398.project.NoteTakerService

import cs398.project.NoteTakerService.controller.UserFilesController
import cs398.project.NoteTakerService.repo.UserFileRepo
import cs398.project.NoteTakerService.repo.UserFiles
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class UserFileControllerTests {
	@MockBean
	private lateinit var repo: UserFileRepo

	@Autowired
	private lateinit var controller: UserFilesController

	@Test
	fun get() {
		assertThat(controller).isNotNull

		val res = controller.getUserFiles("1")

		assert(res.isEmpty())
	}

	@Test
	fun post() {
		controller.updateUserFiles(UserFiles(listOf(), "1"))
	}
}
