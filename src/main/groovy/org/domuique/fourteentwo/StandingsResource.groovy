package org.domuique.fourteentwo

class StandingsResource {

    private static final String DIVISION_LINE_EXPRESSION = /Division #(\d{3})/ 
    private static final String TEAM_STANDING_LINE_EXPRESSION = /(\d{3,}?)\s(.*?)\s\d+.*/

    private static String getStandings() {
        UPL.getText 'http://www.m8pool.com/pdfs/advsundiv.pdf'
    }

    private static Team extract(String line) {
        def team = (line=~TEAM_STANDING_LINE_EXPRESSION).collect { match, id, name ->
           [ 'id': id, 'name': name ]
        }
        team.id ? team : null
    }

    public static Collection<Team> getTeams() {
        def teams = new ArrayList<Team>()
        def standings = StandingsResource.standings
        // the following line may add multi-division support
        def divisionStandings = UPL.divide(standings).first()
        divisionStandings.eachLine { teams << extract(it) }
        teams.removeAll([null])
        teams
    }

}
