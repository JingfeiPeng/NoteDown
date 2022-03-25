package business.command

interface Command {
    fun runCommand(): Boolean
}