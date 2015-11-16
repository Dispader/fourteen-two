package org.domuique.fourteentwo

import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.BINARY

class OCR {

    static String ocr(File pdfFile) {
        def client = new RESTClient( 'http://beta.offenedaten.de:9998/tika' )
        def response = client.put( requestContentType: BINARY, body: pdfFile.bytes )
        response.data.text
    }

}
