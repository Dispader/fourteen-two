package org.domuique.fourteentwo

import spock.lang.Specification

class TeamServiceSpec extends Specification {

    def 'unit tests can load a Fall 2015 Advanced Sunday schedule test file'() {
        when:
            String contents = this.getClass().getResource('/2015-Fall-advsundiv.txt').text
        then:
            contents
    }

    def 'the team factory class can process a Fall 2015 Advanced Sunday schedule test file'() {
        when:
            // TODO: REFACTOR this to a getResourceAsFile method for re-use
            URL url = this.getClass().getResource('/2015-Fall-advsundiv.txt')
            String filepath = url.getFile().replace('%20', ' ')
            File file = new File(filepath)
            Collection<Team> teams = TeamFactory.get(file)
        then:
            teams?.size() == 36
    }

}
