package dean.org.gameofphones.model

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
data class House(val url:           String,
                 val name:          String,
                 val region:        String,
                 val currentLord:   String,
                 val words:         String,
                 val swornMembers:  Set<String>)

data class Character(val url:       Uri,
                     val name:      String,
                     val gender:    String,
                     val culture:   String)