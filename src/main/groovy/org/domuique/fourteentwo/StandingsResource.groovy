package org.domuique.fourteentwo

import java.util.Collection;

class StandingsResource {

    private static final String PAGE_BREAK_EXPRESSION = /UPL\/M8 POOL LEAGUE/ 
    private static final String DIVISION_LINE_EXPRESSION = /Division #(\d{3})/ 
    private static final String TEAM_STANDING_LINE_EXPRESSION = /(\d{3,}?)\s(.*?)\s\d+.*/
    
    static File downloadStandings() {
        def url = new URL('http://www.m8pool.com/pdfs/advsundiv.pdf')
        def file = File.createTempFile('advsundiv', 'pdf')
        def outputStream = file.newOutputStream()
        outputStream << url.openStream()
        outputStream.close()
        file
    }

    static String standings() {
        OCR.ocr(StandingsResource.downloadStandings())
    }

    static Collection<String> divide(String standings) {
        Collection<String> divisions = new ArrayList<String>()
        String page = ''
        standings.eachLine {
            if ( (it=~PAGE_BREAK_EXPRESSION) && !page.empty ) {
                divisions << page
                page = ''
            }
            if ( !it.empty ) { page += "${it}\n" }
        }
        divisions << page
    }

    static Team extract(String line) {
        def team = (line=~TEAM_STANDING_LINE_EXPRESSION).collect { match, id, name ->
           [ 'id': id, 'name': name ]
        }
        team.id ? team : null
    }

    public static Collection<Team> getTeams() {
        def teams = new ArrayList<Team>()
        def standings = standings()
        def divisionStandings = divide(standings).first()
        divisionStandings.eachLine { teams << extract(it) }
        teams.removeAll([null])
        teams
    }

}
