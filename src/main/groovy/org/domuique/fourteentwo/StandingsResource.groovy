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

}
