package org.domuique.fourteentwo.resource

import org.domuique.fourteentwo.model.Team
import org.domuique.fourteentwo.dao.StandingsDAO;

class TeamResource {

    Collection<Team> teams

    private populateCache(StandingsDAO standingsDao) {
        this.teams = standingsDao.getTeams()
    }

    public Collection<Team> getTeams() {
        if ( !(this.teams) ) { populateCache(new StandingsDAO()) }
        this.teams
    }

}
