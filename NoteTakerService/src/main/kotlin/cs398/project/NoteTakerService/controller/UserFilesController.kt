package cs398.project.NoteTakerService.controller

import cs398.project.NoteTakerService.repo.UserFile
import cs398.project.NoteTakerService.repo.UserFileRepo
import cs398.project.NoteTakerService.service.UserFileService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/userFiles")
class UserFilesController(val service: UserFileService) {
    @GetMapping
    fun getUserFiles(
        @RequestParam userID: String
    ): List<UserFile> = service.findFilesByUser(userID)

}