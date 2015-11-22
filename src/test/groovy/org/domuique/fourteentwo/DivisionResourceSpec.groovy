package org.domuique.fourteentwo

import spock.lang.Specification

class DivisionResourceSpec extends Specification {

    private File getResourceAsFile(String resource) {
        URL url = this.getClass().getResource(resource)
        String filepath = url.getFile().replace('%20', ' ')
        new File(filepath)
    }

    def 'these tests can load a Fall 2015 Advanced Sunday schedule test file'() {
        when:
            String contents = this.getClass().getResource('/2015/fall/advsundiv.txt').text
        then:
            contents
    }

    def 'we can divide standings by division'() {
        when:
            def standings = this.getResourceAsFile('/2015/fall/advsundiv.txt').text
            Collection<String> divisions = DivisionResource.divide(standings)
        then:
            divisions.count { true } == 4
    }

}
