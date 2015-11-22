package org.domuique.fourteentwo

import spock.lang.Specification;

class UPLSpecification extends Specification {

    private File getResourceAsFile(String resource) {
        URL url = this.getClass().getResource(resource)
        String filepath = url.getFile().replace('%20', ' ')
        new File(filepath)
    }

    def 'the OCR method can process files'() {
        when:
            File pdf = this.getResourceAsFile('/2015/fall/advsundiv.pdf')
            def response = UPL.ocr(pdf)
            def lines = response.split(System.getProperty('line.separator')).length
        then:
            notThrown(Exception)
            response instanceof String
            lines == 94
    }

    def 'the download utility can obtain UPL standings in PDF format'() {
        when:
            def file = UPL.download('http://www.m8pool.com/pdfs/advsundiv.pdf')
        then:
            file
    }

}
