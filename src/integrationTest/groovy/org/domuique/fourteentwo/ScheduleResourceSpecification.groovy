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

}
