package org.domuique.fourteentwo

import spock.lang.Specification;

class OCRSpecification extends Specification {

    private File getResourceAsFile(String resource) {
        URL url = this.getClass().getResource(resource)
        String filepath = url.getFile().replace('%20', ' ')
        new File(filepath)
    }

    def 'the OCR utility can process files'() {
        when:
            File pdf = this.getResourceAsFile('/2015/fall/advsundiv.pdf')
            def response = OCR.ocr(pdf)
            def lines = response.split(System.getProperty('line.separator')).length
        then:
            notThrown(Exception)
            response instanceof String
            lines == 94
    }

}
