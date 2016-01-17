package org.domuique.fourteentwo.model

import groovy.transform.*

@EqualsAndHashCode(includeFields=true)
@ToString(includePackage=false, includeFields=true, includeNames=true)
class Match {
    Date time
    Date otherVar
    String homeTeamId, awayTeamId
}
