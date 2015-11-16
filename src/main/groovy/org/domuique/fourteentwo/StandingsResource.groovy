package org.domuique.fourteentwo

class StandingsResource {

    private static final String EXPRESSION = /(\d+?)\s(.*?)\s\d+.*/

    static Collection<Team> get(File file) {
        def teams = file.findAll { (it=~EXPRESSION).matches() }.collect { extract(it) }
        teams
    }

    static Team extract(String line) { (line=~EXPRESSION).collect { match, id, name ->
           [ 'id': id, 'name': name ]
        }
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

}
