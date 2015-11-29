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

    def 'we can extract a home match from a line'() {
        given:
            def match = ScheduleResource.extractHomeMatchFromLine(line, 3)
        expect:
            match == [ 'date': date, 'home': 3, 'away': away ]
        where:
            line                                           | date       | away
            'Week 1: 9/13/15 1-2 3-4 5-6 7-8 9-10 11-12'   | '9/13/15'  | 4
            'Week 3: 9/27/15 1-6 3-8 5-10 7-12 9-2 11-4'   | '9/27/15'  | 8
            'Week 5: 10/11/15 1-10 3-12 5-8 7-4 9-6 11-2'  | '10/11/15' | 12
            'Week 9: 11/08/15 1-5 3-9 7-11 6-2 10-4 12-8'  | '11/08/15' | 9
            'Week 10: 11/15/15 5-11 3-7 9-1 12-6 8-4 2-10' | '11/15/15' | 7
            'Week 12: 11/29/15 3-2 8-12 1-4 6-9 10-5 11-7' | '11/29/15' | 2
            'Week 14: 12/13/15 1-8 3-6 5-4 7-2 9-12 11-10' | '12/13/15' | 6
    }

    def 'we can extract an away match from a line'() {
        given:
            def match = ScheduleResource.extractAwayMatchFromLine(line, 3)
        expect:
            match == [ 'date': date, 'home': home, 'away': 3 ]
        where:
            line                                           | date       | home
            'Week 2: 9/20/15 4-1 6-3 2-5 10-7 12-9 8-11'   | '9/20/15'  | 6
            'Week 4: 10/04/15 8-1 10-3 12-5 2-7 4-9 6-11'  | '10/04/15' | 10
            'Week 6: 10/18/15 12-1 2-3 4-5 6-7 8-9 10-11'  | '10/18/15' | 2
            'Week 7: 10/25/15 1-3 5-7 9-11 4-2 8-6 12-10'  | '10/25/15' | 1
            'Week 8: 11/01/15 7-1 9-5 11-3 2-8 6-10 4-12'  | '11/01/15' | 11
            'Week 11: 11/22/15 11-1 7-9 5-3 2-12 10-8 4-6' | '11/22/15' | 5
            'Week 13: 12/06/15 2-1 4-3 6-5 8-7 10-9 12-11' | '12/06/15' | 4
    }

    def 'we can extract a match from a line'() {
        given:
            def match = ScheduleResource.extractMatchFromLine(line, 3)
        expect:
            match == [ 'date': date, 'home': home, 'away': away ]
        where:
            line                                           | date       | home | away
            'Week 1: 9/13/15 1-2 3-4 5-6 7-8 9-10 11-12'   | '9/13/15'  | 3    | 4
            'Week 3: 9/27/15 1-6 3-8 5-10 7-12 9-2 11-4'   | '9/27/15'  | 3    | 8
            'Week 5: 10/11/15 1-10 3-12 5-8 7-4 9-6 11-2'  | '10/11/15' | 3    | 12
            'Week 9: 11/08/15 1-5 3-9 7-11 6-2 10-4 12-8'  | '11/08/15' | 3    | 9
            'Week 10: 11/15/15 5-11 3-7 9-1 12-6 8-4 2-10' | '11/15/15' | 3    | 7
            'Week 12: 11/29/15 3-2 8-12 1-4 6-9 10-5 11-7' | '11/29/15' | 3    | 2
            'Week 14: 12/13/15 1-8 3-6 5-4 7-2 9-12 11-10' | '12/13/15' | 3    | 6
            'Week 2: 9/20/15 4-1 6-3 2-5 10-7 12-9 8-11'   | '9/20/15'  | 6    | 3
            'Week 4: 10/04/15 8-1 10-3 12-5 2-7 4-9 6-11'  | '10/04/15' | 10   | 3
            'Week 6: 10/18/15 12-1 2-3 4-5 6-7 8-9 10-11'  | '10/18/15' | 2    | 3
            'Week 7: 10/25/15 1-3 5-7 9-11 4-2 8-6 12-10'  | '10/25/15' | 1    | 3
            'Week 8: 11/01/15 7-1 9-5 11-3 2-8 6-10 4-12'  | '11/01/15' | 11   | 3
            'Week 11: 11/22/15 11-1 7-9 5-3 2-12 10-8 4-6' | '11/22/15' | 5    | 3
            'Week 13: 12/06/15 2-1 4-3 6-5 8-7 10-9 12-11' | '12/06/15' | 4    | 3
    }

    def 'we can extract matches from a schedule for a listing'() {
        given:
            def schedule = """
Week 1: 9/13/15 1-2 3-4 5-6 7-8 9-10 11-12
Week 2: 9/20/15 4-1 6-3 2-5 10-7 12-9 8-11
Week 3: 9/27/15 1-6 3-8 5-10 7-12 9-2 11-4
Week 4: 10/04/15 8-1 10-3 12-5 2-7 4-9 6-11
Week 5: 10/11/15 1-10 3-12 5-8 7-4 9-6 11-2
Week 6: 10/18/15 12-1 2-3 4-5 6-7 8-9 10-11
Week 7: 10/25/15 1-3 5-7 9-11 4-2 8-6 12-10
Week 8: 11/01/15 7-1 9-5 11-3 2-8 6-10 4-12
Week 9: 11/08/15 1-5 3-9 7-11 6-2 10-4 12-8
Week 10: 11/15/15 5-11 3-7 9-1 12-6 8-4 2-10
Week 11: 11/22/15 11-1 7-9 5-3 2-12 10-8 4-6
Week 12: 11/29/15 3-2 8-12 1-4 6-9 10-5 11-7
Week 13: 12/06/15 2-1 4-3 6-5 8-7 10-9 12-11
Week 14: 12/13/15 1-8 3-6 5-4 7-2 9-12 11-10
Week 15: 1/03/16 PLAY-OFFS ALL TEAMS
Week 16: 1/10/16 PLAY-OFFS"""
        when:
            def matches = ScheduleResource.extractMatches(schedule, 3)
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
