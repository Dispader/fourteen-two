package org.domuique.fourteentwo

import spock.lang.Specification

class StandingsResourceSpec extends Specification {

    private File getResourceAsFile(String resource) {
        URL url = this.getClass().getResource(resource)
        String filepath = url.getFile().replace('%20', ' ')
        new File(filepath)
    }

    def 'unit tests can load a Fall 2015 Advanced Sunday schedule test file'() {
        when:
            String contents = this.getClass().getResource('/2015/fall/advsundiv.txt').text
        then:
            contents
    }

    def 'the team resource can process a Fall 2015 Advanced Sunday schedule test file'() {
        when:
            File file = this.getResourceAsFile('/2015/fall/advsundiv.txt')
            Collection<Team> teams = StandingsResource.get(file)
        then:
            teams?.size() == 36
    }

    def 'the team resource can extract teams from lines of a schedule file'() {
        when:
            Team extractedTeam = StandingsResource.extract(line)
        then:
            extractedTeam == new Team(id: teamId, name: teamName)
        where:
            line                                      | teamId  | teamName
            '72103 14 Balls & a Rack 412 3438 28 123' | '72103' | '14 Balls & a Rack'
            '72105 Hell\'s Face 441 3619 30 121'      | '72105' | 'Hell\'s Face'
            //'72307 Stoopid 2 937 4114 27 152'         | '72307' | 'Stoopid 2' // fails with final #
    }

}
