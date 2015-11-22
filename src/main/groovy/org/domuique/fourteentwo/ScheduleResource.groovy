package org.domuique.fourteentwo

class ScheduleResource {

    private static String getSchedule() {
        UPL.getText 'http://www.m8pool.com/pdfs/advsunsched.pdf'
    }

    private static Map extractTeam(String line, String teamName = '14 Balls & a Rack') {
        def expression = "^(\\d{1,2}?)\\s+${ -> teamName}\\s+(.*?)\\s\\(.*\$"
        def team = (line=~expression).collect { match, listing, home ->
           [ 'listing': listing as Integer, 'name':teamName, 'home': home ]
        }
        team.listing ? team.first() : null
    }

    public static String getTeams() {
        def allMatches = UPL.divide(ScheduleResource.schedule)
        allMatches.first()
    }

}
