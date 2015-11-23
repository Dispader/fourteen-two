package org.domuique.fourteentwo

import spock.lang.Specification

class ScheduleResourceSpec extends Specification {

    def 'we can extract a team from a line of a schedule'() {
        given:
            def team = ScheduleResource.extractTeamFromLine(line, name)
        expect:
            team['listing'] == listing
            team['name'] == name
            team['home'] == home
        where:
            name                | line                                                                                      | listing  | home
            "Tire Checkers"     | "1 Tire Checkers Hexagon Bar (612)722-3454 2600 27th Av. S Minneapolis MN"                | 1        | "Hexagon Bar"
            "We're Hexed"       | "2 We're Hexed Hexagon Bar (612)722-3454 2600 27th Av. S Minneapolis MN"                  | 2        | "Hexagon Bar"
            "14 Balls & a Rack" | "3 14 Balls & a Rack Cardinal Tavern (612)724-5837 2920 E 38th St. Minneapolis MN"        | 3        | "Cardinal Tavern"
            "Da Ezoob Code"     | "4 Da Ezoob Code Cardinal Tavern (612)724-5837 2920 E 38th St. Minneapolis MN"            | 4        | "Cardinal Tavern"
            "Hell's Face"       | "5 Hell's Face Station 280 (651)233-2165 2554 Como Av. St. Paul MN"                       | 5        | "Station 280"
            "Shoot 'em Dead"    | "6 Shoot 'em Dead Tavern on Avenue (651)227-6315 825 Jefferson Av. St. Paul MN"           | 6        | "Tavern on Avenue"
            "PBA"               | "7 PBA Shooter's Billiards (952)894-1100 1934 Highway 13 East Burnsville MN"              | 7        | "Shooter's Billiards"
            "Brooklyn Saints"   | "8 Brooklyn Saints Shooter's Billiards (952)894-1100 1934 Highway 13 East Burnsville MN"  | 8        | "Shooter's Billiards"
            "Unleash the Fury"  | "9 Unleash the Fury Shooter's Billiards (952)894-1100 1934 Highway 13 East Burnsville MN" | 9        | "Shooter's Billiards"
    }

    def 'we can extract a list of teams from a schedule'() {
        given:
            def schedule = """
1 Tire Checkers Hexagon Bar (612)722-3454 2600 27th Av. S Minneapolis MN
2 We're Hexed Hexagon Bar (612)722-3454 2600 27th Av. S Minneapolis MN
3 14 Balls & a Rack Cardinal Tavern (612)724-5837 2920 E 38th St. Minneapolis MN
4 Da Ezoob Code Cardinal Tavern (612)724-5837 2920 E 38th St. Minneapolis MN
5 Hell's Face Station 280 (651)233-2165 2554 Como Av. St. Paul MN
6 Shoot 'em Dead Tavern on Avenue (651)227-6315 825 Jefferson Av. St. Paul MN
7 PBA Shooter's Billiards (952)894-1100 1934 Highway 13 East Burnsville MN
8 Brooklyn Saints Shooter's Billiards (952)894-1100 1934 Highway 13 East Burnsville MN
9 Unleash the Fury Shooter's Billiards (952)894-1100 1934 Highway 13 East Burnsville MN"""
            def names = 
[ "Tire Checkers", 
  "We're Hexed",
  "14 Balls & a Rack",
  "Da Ezoob Code",
  "Hell's Face",
  "Shoot 'em Dead",
  "PBA",
  "Brooklyn Saints",
  "Unleash the Fury", ]
        when:
            def teams = ScheduleResource.extractTeams(schedule, names)
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
