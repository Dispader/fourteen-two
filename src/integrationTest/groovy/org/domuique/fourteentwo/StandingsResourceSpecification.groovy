package org.domuique.fourteentwo

import spock.lang.Specification

class StandingsResourceSpecification extends Specification {

    def 'the standings resource and download standings from UPL'() {
        when:
            def file = StandingsResource.downloadDevisionStandings() 
        then:
            notThrown(Exception)
            file != null
    }

    def 'the standings resource can obtain a text representation of standings from UPL'() {
        when:
            def standings = StandingsResource.downloadAndConvertDivisionStandings()
            def lines = standings.split(System.getProperty('line.separator')).length
        then:
            notThrown(Exception)
            standings instanceof String
            lines > 0
    }

}
