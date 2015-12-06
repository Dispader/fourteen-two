package org.domuique.fourteentwo.resource

import spock.lang.*
import java.text.SimpleDateFormat
import org.domuique.fourteentwo.model.Match

class MatchResourceSpecification extends Specification {

    @Shared
    private SimpleDateFormat format = new SimpleDateFormat('MM/dd/yy HH')

    @Shared
    MatchResource resource = new MatchResource()

    def 'can get teams'() {
        when:
            def matches = resource.matches
        then:
            matches instanceof List<Match>
            matches.size() == 14
            matches.contains( new Match(time: format.parse("${date} 18"),
                                        homeTeamId: home,
                                        awayTeamId: away ) )
        where:
            date       | home    | away
            '9/13/15'  | '72103' | '72104'
            '9/20/15'  | '72106' | '72103'
            '9/27/15'  | '72103' | '72108'
            '10/04/15' | '72110' | '72103'
            '10/11/15' | '72103' | '72112'
            '10/18/15' | '72102' | '72103'
            '10/25/15' | '72101' | '72103'
            '11/01/15' | '72111' | '72103'
            '11/08/15' | '72103' | '72109'
            '11/15/15' | '72103' | '72107'
            '11/22/15' | '72105' | '72103'
            '11/29/15' | '72103' | '72102'
            '12/06/15' | '72104' | '72103'
            '12/13/15' | '72103' | '72106'
    }

}
