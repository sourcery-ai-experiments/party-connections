package ca.kittle.pc

import ca.kittle.db.models.PlayerCharacterEntity
import ca.kittle.db.models.toDocument
import ca.kittle.pc.models.PlayerCharacter
import ca.kittle.plugins.dbConnection
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Routing.createPlayerCharacter() {
    post("/character") {
        val pc: PlayerCharacter = call.receive()
        createPlayerCharacter(pc)
        call.respondRedirect("/party/${pc.partyId}")
    }
}

suspend fun createPlayerCharacter(pc: PlayerCharacter): String =
    withContext(Dispatchers.IO) {
        val collection = dbConnection.getCollection(PlayerCharacterEntity.COLLECTION_NAME)
        collection.insertOne(pc.toDocument())
        pc.id
    }
