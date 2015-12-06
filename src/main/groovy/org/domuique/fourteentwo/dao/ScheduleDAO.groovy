package org.domuique.fourteentwo.dao

import org.domuique.fourteentwo.model.Team;;

class ScheduleDAO {

    String schedule

    ScheduleDAO() {
        this.schedule = UPLUtility.getText 'http://www.m8pool.com/pdfs/advsunsched.pdf'
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
                def team = ScheduleDAO.extractTeamFromLine(scheduleLine, teamName)
                if ( team ) { map << [ (team.listing): team ] }
            }
        }
        map
    }

    private Map<Integer, Map> getListings(Collection teams) {
        def teamNames = teams.collect([]) { it['name'] }
        ScheduleDAO.extractTeams(this.schedule, teamNames)
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
        def match = ScheduleDAO.extractHomeMatchFromLine(line, listing) ?: ScheduleDAO.extractAwayMatchFromLine(line, listing)
    }

    private List<Map> getMatchList(Integer listing) {
        List<Map> matches = new ArrayList<Map>()
        this.schedule.eachLine { matches << ScheduleDAO.extractMatchFromLine(it, listing) }
        matches.removeAll([null])
        matches
    }

    public List<Map> getMatches(Collection<Team> teams, Team team) {
        Map<Integer, Map> listings = this.getListings(teams)
        listings.each { listing ->
            teams.each { aTeam ->
                if ( listing.value['name'] == aTeam.name ) { listing.value['id'] = aTeam.id }
            }
        }
        def listing = listings?.find{ it.value.id == team['id'] }?.value['listing']
        def matches = this.getMatchList listing
        matches.each { match ->
            match['home'] = (listings[match['home']])['id']
            match['away'] = (listings[match['away']])['id']
        }
    }

}
