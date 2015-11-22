package org.domuique.fourteentwo

import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.BINARY

class UPL {

    private static final String PAGE_BREAK_EXPRESSION = /M8 POOL LEAGUE/

    static String getText(String url) {
        UPL.ocr(UPL.download(url))
    }

    private static File download(String url) {
        def downloadUrl = new URL(url)
        def file = File.createTempFile('temp', 'pdf')
        def outputStream = file.newOutputStream()
        outputStream << downloadUrl.openStream()
        outputStream.close()
        file
    }

    private static String ocr(File pdfFile) {
        def client = new RESTClient( 'http://beta.offenedaten.de:9998/tika' )
        def response = client.put( requestContentType: BINARY, body: pdfFile.bytes )
        response.data.text
    }

    static Collection<String> divide(String standings) {
        Collection<String> pages = new ArrayList<String>()
        String page = ''
        standings.eachLine {
            if ( (it=~PAGE_BREAK_EXPRESSION) && !page.empty ) {
                pages << page
                page = ''
            }
            if ( !it.empty ) { page += "${it}\n" }
        }
        pages << page
    }

}
