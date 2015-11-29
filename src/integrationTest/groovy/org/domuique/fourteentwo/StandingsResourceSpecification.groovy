package org.domuique.fourteentwo

import spock.lang.Specification

class StandingsResourceSpecification extends Specification {

    StandingsResource resource = new StandingsResource()

    def 'the standings resource can obtain UPL standings in text format'() {
        when:
            def standings = resource.standings
        then:
            standings instanceof String
            standings.split(System.getProperty('line.separator')).length > 5
    }

    def 'the standings resource can extract division #721 teams from UPL standings'() {
        when:
            def outputTeams = resource.teams
        then:
            outputTeams instanceof Collection<Team>
            outputTeams.contains(team)
        where:
            team << [ new Team(id: '72103', name: "14 Balls & a Rack"),
                      new Team(id: '72105', name: "Hell's Face") ]
    }

}
