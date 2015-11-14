package org.domuique.fourteentwo

class TeamFactory {

    static Collection<Team> get(File file) {
        def expression = /(\d+?)\s(.*?)\s\d+.*/
        def teams = file.findAll { (it=~expression).matches() }.collect { (it=~expression).collect { match, id, name ->
           [ 'id': id, 'name': name ]
        } }
        teams
    }

}
