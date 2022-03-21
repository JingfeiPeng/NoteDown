package cs398.project.NoteTakerService.controller

import cs398.project.NoteTakerService.repo.UserFile
import cs398.project.NoteTakerService.service.UserFileService
import org.springframework.web.bind.annotation.*


data class UserFiles (
    val files: List<UserFile>,
    val userID: String,
)

@RestController
@RequestMapping("/userFiles")
class UserFilesController(val service: UserFileService) {
    @GetMapping
    fun getUserFiles(
        @RequestParam userID: String
    ): List<UserFile> = service.findFilesByUser(userID)

    @PostMapping
    fun updateUserFIles(
        @RequestBody userFiles: UserFiles
    ): String {
        println(userFiles.files.size)
        return "Success"
    }
}