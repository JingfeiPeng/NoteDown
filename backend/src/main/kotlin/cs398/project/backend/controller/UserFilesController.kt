package cs398.project.NoteTakerService.controller

import cs398.project.NoteTakerService.repo.UserFile
import cs398.project.NoteTakerService.repo.UserFiles
import cs398.project.NoteTakerService.service.UserFileService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/userFiles")
class UserFilesController(val service: UserFileService) {
    @GetMapping
    fun getUserFiles(
        @RequestParam userId: String
    ): List<UserFile> = service.findFilesByUserId(userId)

    @PostMapping
    fun updateUserFiles(
        @RequestBody userFiles: UserFiles
    ): String {
        try {
            service.updateUsersFiles(userFiles)
        } catch (e: Exception) {
            return e.localizedMessage
        }
        return "Success"
    }
}