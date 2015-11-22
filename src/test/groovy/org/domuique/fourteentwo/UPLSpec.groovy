package org.domuique.fourteentwo

import spock.lang.Specification

class UPLSpec extends Specification {

    private File getResourceAsFile(String resource) {
        URL url = this.getClass().getResource(resource)
        String filepath = url.getFile().replace('%20', ' ')
        new File(filepath)
    }

    def 'we can divide UPL report files by division'() {
        when:
            def standings = this.getResourceAsFile('/2015/fall/advsundiv.txt').text
            Collection<String> divisions = UPL.divide(standings)
        then:
            divisions.count { true } == 4
    }

}
