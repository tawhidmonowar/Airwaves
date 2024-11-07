package org.tawhid.airwaves.player


interface PlayerRepository {
    fun helloWorld(): String
    fun name()
}

class PlayerRepositoryImpl(
    private val dbClient: PlayerController
) : PlayerRepository {
    override fun helloWorld(): String {
        return "Hello World!"
    }

    override fun name() {
        dbClient.myName()
    }
}