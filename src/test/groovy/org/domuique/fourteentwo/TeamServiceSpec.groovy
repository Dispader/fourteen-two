package org.domuique.fourteentwo

import org.junit.Ignore;

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
            File scheduleFile = new File(this.getClass().getResource('/2015-Fall-advsundiv.txt').getFile())
            Collection<Team> teams = TeamFactory.get(scheduleFile)
        then:
            teams?.size() == 36
    }

}
