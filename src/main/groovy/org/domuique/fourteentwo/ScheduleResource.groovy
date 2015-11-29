package org.domuique.fourteentwo

class ScheduleResource {

    private static String getSchedule() {
        UPL.getText 'http://www.m8pool.com/pdfs/advsunsched.pdf'
    }

    private static Map extractTeamFromLine(String line, String teamName = '14 Balls & a Rack') {
        def expression = "^(\\d{1,2}?)\\s+${ -> teamName}\\s+(.*?)\\s\\(.*\$"
        def team = (line=~expression).collect { regexMatch, listing, home ->
           [ 'listing': listing as Integer, 'name': teamName, 'home': home ]
        }
        team.listing ? team.first() : null
    }

    private static Map<Integer, Map> extractTeams(String schedule, List<String> teamNames) {
        def map = [:]
        schedule.eachLine { scheduleLine ->
            teamNames.each { teamName ->
                def team = ScheduleResource.extractTeamFromLine(scheduleLine, teamName)
                if ( team ) { map << [ (team.listing): team ] }
            }
        }
        map
    }

    public static Map<Integer, Map> getTeams() {
        def standingsTeams = StandingsResource.teams
        def standingsTeamNames = standingsTeams.collect([]) { it['name'] }
        ScheduleResource.extractTeams(ScheduleResource.schedule, standingsTeamNames)
    }

    private static Map extractHomeMatchFromLine(String line, Integer home = 3) {
        def expression = "^.+\\s+(\\d{1,2}/\\d{1,2}/\\d{2}).+${ -> home}-(\\d{1,2}).+\$"
        def match = (line=~expression).collect { regexMatch, date, away ->
            [ 'date': date, 'home': home, 'away': away as Integer ]
        }
        match.home ? match.first() : null
    }

    private static Map extractAwayMatchFromLine(String line, Integer away = 3) {
        def expression = "^.+\\s+(\\d{1,2}/\\d{1,2}/\\d{2}).*\\s+(\\d{1,2})-${ -> away}.+\$"
        def match = (line=~expression).collect { regexMatch, date, home ->
           [ 'date': date, 'home': home as Integer, 'away': away as Integer ]
        }
        match.home ? match.first() : null
    }

    private static Map extractMatchFromLine(String line, Integer team = 3) {
        def match = ScheduleResource.extractHomeMatchFromLine(line, team) ?: ScheduleResource.extractAwayMatchFromLine(line, team)
    }

    private static List<Map> extractMatches(String schedule, Integer team = 3) {
        List<Map> matches = new ArrayList<Map>()
        schedule.eachLine { matches << ScheduleResource.extractMatchFromLine(it, team) }
        matches.removeAll([null])
        matches
    }

}
