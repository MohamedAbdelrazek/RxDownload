package zlc.season.rxdownload4.watcher

import zlc.season.rxdownload4.task.Task
import zlc.season.rxdownload4.utils.getFile

class WatcherImpl : Watcher {
    @Synchronized
    override fun watch(task: Task) {
        if (taskMap[task.tag()] != null) {
            throw IllegalStateException("Task [${task.tag()} is exists!")
        }

        val filePath = task.getFile().canonicalPath
        if (fileMap[filePath] != null) {
            throw IllegalStateException("File [$filePath] is occupied!")
        }

        taskMap[task.tag()] = task.tag()
        fileMap[filePath] = filePath
    }

    @Synchronized
    override fun unwatch(task: Task) {
        taskMap.remove(task.tag())
        fileMap.remove(task.getFile().canonicalPath)
    }

    companion object {
        private val taskMap = mutableMapOf<String, String>()
        private val fileMap = mutableMapOf<String, String>()
    }
}