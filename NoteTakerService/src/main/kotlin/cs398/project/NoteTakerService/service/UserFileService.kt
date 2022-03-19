package cs398.project.NoteTakerService.service

import cs398.project.NoteTakerService.repo.UserFile
import cs398.project.NoteTakerService.repo.UserFileRepo
import org.springframework.stereotype.Service

@Service
class UserFileService(val db: UserFileRepo) {
    fun findFilesByUser(userID: String):List<UserFile> = db.findFilesByUser(userID)
}