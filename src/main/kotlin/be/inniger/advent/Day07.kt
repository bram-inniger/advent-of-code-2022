package be.inniger.advent

object Day07 {

    fun solveFirst(commands: List<String>) = dirSizes(commands).filter { it <= 100_000 }.sum()
    fun solveSecond(commands: List<String>): Long {
        val sizes = dirSizes(commands)
        val spaceRequired = 30_000_000 - (70_000_000 - sizes.last())

        return sizes.filter { it >= spaceRequired }.minOf { it }
    }

    private fun dirSizes(commandsInput: List<String>): List<Long> {
        val root = RootDir()
        val commands = commandsInput.joinToString("\n")
            .split("$ ")
            .map { it.removeSuffix("\n") }
            .filter { it.isNotBlank() }
            .map { Command.of(it) }

        var currentDir: Directory = root
        for (command in commands) {
            when (command) {
                CdRoot -> currentDir = root

                CdUp -> currentDir = when (currentDir) {
                    is SubDir -> currentDir.parent
                    is RootDir -> error("Cannot move up a directory from root")
                }

                is Cd -> {
                    val subDir = currentDir.children()
                        .find { it.name() == command.subDir }

                    when (subDir) {
                        null -> error("Could not find sub-directory ${command.subDir}")
                        is File -> error("Could not cd into file ${command.subDir}")
                        is Directory -> currentDir = subDir
                    }
                }

                is Ls -> {
                    if (currentDir.children().isEmpty()) {
                        currentDir.children().addAll(command.contents
                            .map { it.split(" ") }
                            .map { child ->
                                when {
                                    child[0] == "dir" -> SubDir(currentDir, child[1])
                                    else -> File(currentDir, child[1], child[0].toLong())
                                }
                            })
                    }
                }
            }
        }

        val sizes = mutableListOf<Long>()
        calculateSizes(root, sizes)

        return sizes
    }

    private fun calculateSizes(directory: Directory, sizes: MutableList<Long>): Long {
        var size = 0L
        for (child in directory.children()) {
            size += when (child) {
                is File -> child.size
                is Directory -> calculateSizes(child, sizes)
            }
        }
        sizes.add(size)
        return size
    }

    private sealed interface Node {
        fun name(): String
        fun parent(): Directory
    }

    private sealed interface Directory {
        fun name(): String
        fun children(): MutableList<Node>
    }

    private class RootDir(val children: MutableList<Node> = mutableListOf()) : Directory {
        override fun name() = "/"
        override fun children() = children
    }

    private class SubDir(
        val parent: Directory, val name: String, val children: MutableList<Node> = mutableListOf()
    ) : Node, Directory {
        override fun name() = name
        override fun parent() = parent
        override fun children() = children
    }

    private class File(val parent: Directory, val name: String, val size: Long) : Node {
        override fun parent() = parent
        override fun name() = name
    }

    private sealed interface Command {
        companion object {
            fun of(command: String) = when {
                command == "cd /" -> CdRoot
                command == "cd .." -> CdUp
                command.startsWith("cd ") -> Cd(command.removePrefix("cd "))
                command.startsWith("ls") -> Ls(command.removePrefix("ls\n").split("\n"))
                else -> error("Unknown command: $command")
            }
        }
    }

    private object CdRoot : Command
    private object CdUp : Command
    private data class Cd(val subDir: String) : Command
    private data class Ls(val contents: List<String>) : Command
}
