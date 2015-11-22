package org.domuique.fourteentwo

import spock.lang.Specification

class ScheduleResourceSpecification extends Specification {

    def 'the schedule resource can scheduled teams in text format'() {
        when:
            def teams = ScheduleResource.teams
        then:
            teams instanceof String
            teams.split(System.getProperty('line.separator')).length > 5
    }

}
