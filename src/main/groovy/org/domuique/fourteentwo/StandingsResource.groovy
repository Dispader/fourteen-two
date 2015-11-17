package org.domuique.fourteentwo

class StandingsResource {

    private static final String EXPRESSION = /(\d{3,}?)\s(.*?)\s\d+.*/

    static Collection<Team> get(File file) {
        file.findAll { (it=~EXPRESSION).matches() }.collect { extract(it) }
    }

    static def get() {
        def schedule = downloadDevisionStandings()
        schedule.eachLine { new Team() }
        new Team()
        'fishy!'
    }

    static Team extract(String line) {
        def team = (line=~EXPRESSION).collect { match, id, name ->
           [ 'id': id, 'name': name ]
        }
        team.id ? team : null
    }

    static File downloadDevisionStandings() {
        def url = new URL('http://www.m8pool.com/pdfs/advsundiv.pdf')
        def file = File.createTempFile('advsundiv', 'pdf')
        def outputStream = file.newOutputStream()
        outputStream << url.openStream()
        outputStream.close()
        file
    }

    static String downloadAndConvertDivisionStandings() {
        OCR.ocr(StandingsResource.downloadDevisionStandings())
    }

    public static Collection<Team> getTeams() {
        def teams = new ArrayList<Team>()
        def schedule = downloadAndConvertDivisionStandings()
        schedule.eachLine { teams << extract(it) }
        teams.removeAll([null])
        teams
    }

}
