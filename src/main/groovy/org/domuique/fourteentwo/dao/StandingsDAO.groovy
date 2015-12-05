package org.domuique.fourteentwo.dao

import org.domuique.fourteentwo.model.Team;;

class StandingsDAO {

    private static final String DIVISION_LINE_EXPRESSION = /Division #(\d{3})/ 
    private static final String TEAM_STANDING_LINE_EXPRESSION = /(\d{3,}?)\s(.*?)\s\d+.*/

    String standings

    public StandingsDAO() {
        def allStandings = UPLUtility.getText 'http://www.m8pool.com/pdfs/advsundiv.pdf'
        this.standings =  UPLUtility.divide(allStandings).first()
    }

    private static Team extract(String line) {
        def team = (line=~TEAM_STANDING_LINE_EXPRESSION).collect { match, id, name ->
           [ 'id': id, 'name': name ]
        }
        team.id ? team : null
    }

    public Collection<Team> getTeams() {
        def teams = new ArrayList<Team>()
        this.standings.eachLine { teams << extract(it) }
        teams.removeAll([null])
        teams
    }

}
