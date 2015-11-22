package org.domuique.fourteentwo

class DivisionResource {

    private static final String PAGE_BREAK_EXPRESSION = /UPL\/M8 POOL LEAGUE/ 
    private static final String DIVISION_LINE_EXPRESSION = /Division #(\d{3})/ 

    static Collection<String> divide(String standings) {
        Collection<String> divisions = new ArrayList<String>()
        String page = ''
        standings.eachLine {
            if ( (it=~PAGE_BREAK_EXPRESSION) && !page.empty ) {
                divisions << page
                page = ''
            }
            if ( !it.empty ) { page += "${it}\n" }
        }
        divisions << page
    }

}
