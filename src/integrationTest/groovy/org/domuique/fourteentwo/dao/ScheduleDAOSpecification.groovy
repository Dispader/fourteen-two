package org.domuique.fourteentwo.dao

import spock.lang.Specification
import org.domuique.fourteentwo.model.Team;

import spock.lang.Shared

class ScheduleDAOSpecification extends Specification {

    @Shared
    ScheduleDAO resource = new ScheduleDAO()

    def 'the schedule resource can obtain a schedule in text format'() {
        when:
            def teams = resource.schedule
        then:
            teams instanceof String
            teams.split(System.getProperty('line.separator')).length > 5
    }

    def 'the schedule resource can get a map of team listings'() {
        given:
            def inputTeams = [ new Team(id: '72111', name: "Holy Ballz"),
                               new Team(id: '72109', name: "Unleash the Fury"),
                               new Team(id: '72107', name: "PBA"),
                               new Team(id: '72101', name: "Tire Checkers"),
                               new Team(id: '72112', name: "Killing Time"),
                               new Team(id: '72104', name: "Da Ezoob Code"),
                               new Team(id: '72110', name: "Bottoms Up"),
                               new Team(id: '72106', name: "Shoot 'em Dead"),
                               new Team(id: '72103', name: "14 Balls & a Rack"),
                               new Team(id: '72105', name: "Hell's Face"),
                               new Team(id: '72108', name: "Brooklyn Saints"),
                               new Team(id: '72102', name: "We're Hexed") ]
        when:
            def listings = resource.getListings inputTeams
        then:
            listings[listing] == [ 'listing': listing, 'name': name, 'home': home ]
        where:
            listing  | name                | home
            1        | "Tire Checkers"     | "Hexagon Bar"
            2        | "We're Hexed"       | "Hexagon Bar"
            3        | "14 Balls & a Rack" | "Cardinal Tavern"
            4        | "Da Ezoob Code"     | "Cardinal Tavern"
            5        | "Hell's Face"       | "Station 280"
            6        | "Shoot 'em Dead"    | "Tavern on Avenue"
            7        | "PBA"               | "Shooter's Billiards"
            8        | "Brooklyn Saints"   | "Shooter's Billiards"
            9        | "Unleash the Fury"  | "Shooter's Billiards"
    }

    def 'we can get a map of matches by listing'() {
        when:
            def matches = resource.getMatchList 3
        then:
            matches.contains( match )
            !matches.contains ( null )
        where:
            match << [ [ 'date': '9/13/15',  'home': 3,  'away': 4  ],
                       [ 'date': '9/27/15',  'home': 3,  'away': 8  ],
                       [ 'date': '10/11/15', 'home': 3,  'away': 12 ],
                       [ 'date': '11/08/15', 'home': 3,  'away': 9  ],
                       [ 'date': '11/15/15', 'home': 3,  'away': 7  ],
                       [ 'date': '11/29/15', 'home': 3,  'away': 2  ],
                       [ 'date': '12/13/15', 'home': 3,  'away': 6  ],
                       [ 'date': '9/20/15',  'home': 6,  'away': 3  ],
                       [ 'date': '10/04/15', 'home': 10, 'away': 3  ],
                       [ 'date': '10/18/15', 'home': 2,  'away': 3  ],
                       [ 'date': '10/25/15', 'home': 1,  'away': 3  ],
                       [ 'date': '11/01/15', 'home': 11, 'away': 3  ],
                       [ 'date': '11/22/15', 'home': 5,  'away': 3  ],
                       [ 'date': '12/06/15', 'home': 4,  'away': 3  ] ]
    }

    def 'we can get a map of matches'() {
        given:
            def inputTeams = [ new Team(id: '72111', name: "Holy Ballz"),
                               new Team(id: '72109', name: "Unleash the Fury"),
                               new Team(id: '72107', name: "PBA"),
                               new Team(id: '72101', name: "Tire Checkers"),
                               new Team(id: '72112', name: "Killing Time"),
                               new Team(id: '72104', name: "Da Ezoob Code"),
                               new Team(id: '72110', name: "Bottoms Up"),
                               new Team(id: '72106', name: "Shoot 'em Dead"),
                               new Team(id: '72103', name: "14 Balls & a Rack"),
                               new Team(id: '72105', name: "Hell's Face"),
                               new Team(id: '72108', name: "Brooklyn Saints"),
                               new Team(id: '72102', name: "We're Hexed") ]
            def inputTeam = new Team(id: '72103', name: "14 Balls & a Rack")
        when:
            def matches = resource.getMatches(inputTeams, inputTeam)
        then:
            matches.contains( match )
            !matches.contains ( null )
        where:
            match << [ [ 'date': '9/13/15',  'home': '72103', 'away': '72104' ],
                       [ 'date': '9/20/15',  'home': '72106', 'away': '72103' ],
                       [ 'date': '9/27/15',  'home': '72103', 'away': '72108' ],
                       [ 'date': '10/04/15', 'home': '72110', 'away': '72103' ],
                       [ 'date': '10/11/15', 'home': '72103', 'away': '72112' ],
                       [ 'date': '10/18/15', 'home': '72102', 'away': '72103' ],
                       [ 'date': '10/25/15', 'home': '72101', 'away': '72103' ],
                       [ 'date': '11/01/15', 'home': '72111', 'away': '72103' ],
                       [ 'date': '11/08/15', 'home': '72103', 'away': '72109' ],
                       [ 'date': '11/15/15', 'home': '72103', 'away': '72107' ],
                       [ 'date': '11/22/15', 'home': '72105', 'away': '72103' ],
                       [ 'date': '11/29/15', 'home': '72103', 'away': '72102' ],
                       [ 'date': '12/06/15', 'home': '72104', 'away': '72103' ],
                       [ 'date': '12/13/15', 'home': '72103', 'away': '72106' ] ]
    }

}
