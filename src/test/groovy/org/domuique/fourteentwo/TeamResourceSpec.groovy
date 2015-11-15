package org.domuique.fourteentwo

import spock.lang.Specification

class TeamResourceSpec extends Specification {

    private File getResourceAsFile(String resource) {
        URL url = this.getClass().getResource(resource)
        String filepath = url.getFile().replace('%20', ' ')
        new File(filepath)
    }

    def 'unit tests can load a Fall 2015 Advanced Sunday schedule test file'() {
        when:
            String contents = this.getClass().getResource('/2015-Fall-advsundiv.txt').text
        then:
            contents
    }

    def 'the team resource can process a Fall 2015 Advanced Sunday schedule test file'() {
        when:
            File file = this.getResourceAsFile('/2015-Fall-advsundiv.txt')
            Collection<Team> teams = TeamResource.get(file)
        then:
            teams?.size() == 36
    }

}
