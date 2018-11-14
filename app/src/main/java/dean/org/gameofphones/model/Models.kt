package dean.org.gameofphones.model

import android.net.Uri

data class House(val url:           Uri,
                 val name:          String,
                 val region:        String,
                 val currentLord:   Uri,
                 val swornMembers:  Set<Uri>)

data class Character(val url:       Uri,
                     val name:      String,
                     val gender:    String,
                     val culture:   String)