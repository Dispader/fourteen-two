package org.domuique.fourteentwo

class ScheduleResource {

    private static String getSchedule() {
        UPL.getText 'http://www.m8pool.com/pdfs/advsunsched.pdf'
    }

    private static Map extractTeamFromLine(String line, String teamName = '14 Balls & a Rack') {
        def expression = "^(\\d{1,2}?)\\s+${ -> teamName}\\s+(.*?)\\s\\(.*\$"
        def team = (line=~expression).collect { match, listing, home ->
           [ 'listing': listing as Integer, 'name':teamName, 'home': home ]
        }
        team.listing ? team.first() : null
    }

    private static Map<Integer, Map> extractTeams(String standings, List<String> teamNames) {
        def map = [:]
        standings.eachLine { standingsLine ->
            teamNames.each { teamName ->
                def team = ScheduleResource.extractTeamFromLine(standingsLine, teamName)
                if ( team ) { map << [ (team.listing): team ] }
            }
        }
        map
    }

    public static String getTeams() {
        def allMatches = UPL.divide(ScheduleResource.schedule)
        allMatches.first()
    }

}
