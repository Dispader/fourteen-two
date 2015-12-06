package org.domuique.fourteentwo.resource

import java.text.SimpleDateFormat
import org.domuique.fourteentwo.dao.ScheduleDAO
import org.domuique.fourteentwo.model.Match
import org.domuique.fourteentwo.model.Team

class MatchResource {

    private SimpleDateFormat format = new SimpleDateFormat('MM/dd/yy')

    List<Match> matches

    private populateCache() {
        TeamResource teamResource = TeamResource.getInstance()
        Collection<Team> teamCollection = teamResource?.getTeams()
        Team forTeam = teamResource?.getTeam('72103')
        ScheduleDAO scheduleDao = new ScheduleDAO()
        List<Map> scheduleMatchList = scheduleDao.getMatches(teamCollection, forTeam)
        this.matches = scheduleMatchList.collect(new ArrayList<Match>()) {
            Date date = this.format.parse(it['date'])
            def time = date.copyWith(hourOfDay: 18)
            new Match(time: time, homeTeamId: it.home, awayTeamId: it.away)
        }
    }

    public List<Match> getMatches() {
        if ( !(this.matches) ) {
            this.populateCache()
        } 
        this.matches
    }

    public Match getNext(Date after = new Date()) {
        def matchList = this.getMatches()
        matchList.find { it.time > after }
    }

}
