package org.domuique.fourteentwo

class ScheduleResource {

    String schedule

    ScheduleResource() {
        this.schedule = UPL.getText 'http://www.m8pool.com/pdfs/advsunsched.pdf'
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

    public Map<Integer, Map> getListings(Collection teams) {
        def teamNames = teams.collect([]) { it['name'] }
        ScheduleResource.extractTeams(this.schedule, teamNames)
    }

    private static Map extractHomeMatchFromLine(String line, Integer home) {
        def expression = "^.+\\s+(\\d{1,2}/\\d{1,2}/\\d{2}).+${ -> home}-(\\d{1,2}).+\$"
        def match = (line=~expression).collect { regexMatch, date, away ->
            [ 'date': date, 'home': home, 'away': away as Integer ]
        }
        match.home ? match.first() : null
    }

    private static Map extractAwayMatchFromLine(String line, Integer away) {
        def expression = "^.+\\s+(\\d{1,2}/\\d{1,2}/\\d{2}).*\\s+(\\d{1,2})-${ -> away}.+\$"
        def match = (line=~expression).collect { regexMatch, date, home ->
           [ 'date': date, 'home': home as Integer, 'away': away as Integer ]
        }
        match.home ? match.first() : null
    }

    private static Map extractMatchFromLine(String line, Integer listing) {
        def match = ScheduleResource.extractHomeMatchFromLine(line, listing) ?: ScheduleResource.extractAwayMatchFromLine(line, listing)
    }

    private List<Map> getMatches(Integer listing) {
        List<Map> matches = new ArrayList<Map>()
        this.schedule.eachLine { matches << ScheduleResource.extractMatchFromLine(it, listing) }
        matches.removeAll([null])
        matches
    }

    private List<Map> getMatches(Collection<Team> teams, Team team) {
        def listings = this.getListings(teams)
        // TODO: HERE is where you add the (class-unavailable) team ID to the listings map, before you need it below
        def listing = listings?.find{ it.value.name == team['name'] }?.value['listing']
        def matches = this.getMatches listing
    }

}
