package org.domuique.fourteentwo

import spock.lang.Specification

class ScheduleResourceSpecification extends Specification {

    def 'the schedule resource can obtain a schedule in text format'() {
        when:
            def teams = ScheduleResource.schedule
        then:
            teams instanceof String
            teams.split(System.getProperty('line.separator')).length > 5
    }

    def 'the schedule resource can get teams'() {
        when:
            def teams = ScheduleResource.teams
        then:
            teams[listing] == [ 'listing': listing, 'name': name, 'home': home ]
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

    def 'we can extract matches from a schedule for a listing'() {
        when:
            def matches = ScheduleResource.extractMatches()
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

}
