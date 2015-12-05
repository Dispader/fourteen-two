package org.domuique.fourteentwo.dao

import spock.lang.Specification

class UPLUtilitySpec extends Specification {

    private File getResourceAsFile(String resource) {
        URL url = this.getClass().getResource(resource)
        String filepath = url.getFile().replace('%20', ' ')
        new File(filepath)
    }

    def 'we can divide UPL report files by division'() {
        when:
            def standings = this.getResourceAsFile('/2015/fall/advsundiv.txt').text
            Collection<String> divisions = UPLUtility.divide(standings)
        then:
            divisions.count { true } == 4
    }

}
