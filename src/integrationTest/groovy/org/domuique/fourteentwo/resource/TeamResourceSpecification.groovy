package org.domuique.fourteentwo.resource

import spock.lang.*
import org.domuique.fourteentwo.model.Team

class TeamResourceSpecification extends Specification {

    @Shared
    TeamResource resource = new TeamResource()

    def 'can get teams'() {
        when:
            def teams = resource.teams
        then:
            teams instanceof Collection<Team>
            teams.contains(team)
        where:
            team << [ new Team(id: '72103', name: "14 Balls & a Rack"),
                      new Team(id: '72105', name: "Hell's Face") ]
    }

}
