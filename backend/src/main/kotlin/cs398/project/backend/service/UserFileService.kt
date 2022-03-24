package cs398.project.NoteTakerService.service

import cs398.project.NoteTakerService.repo.UserFile
import cs398.project.NoteTakerService.repo.UserFileRepo
import cs398.project.NoteTakerService.repo.UserFiles
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class UserFileService(val db: UserFileRepo) {
    fun findFilesByUserId(userId: String):List<UserFile> = db.findFilesByUserId(userId)

    fun updateUsersFiles(userFiles: UserFiles) {

        throw Exception("lol")
        // clear previous entries
        db.deleteUserFiles(userFiles.userId)
        for (userFile in userFiles.files) {
            db.save(userFile)
        }
    }
}