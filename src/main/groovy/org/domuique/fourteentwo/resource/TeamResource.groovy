package org.domuique.fourteentwo.resource

import org.domuique.fourteentwo.model.Team
import org.domuique.fourteentwo.dao.StandingsDAO;

// TODO: MAKE TeamResource a factory instance
class TeamResource {

    Collection<Team> teams

    private populateCache(StandingsDAO standingsDao) {
        this.teams = standingsDao.getTeams()
    }

    public Collection<Team> getTeams() {
        if ( !(this.teams) ) { populateCache(new StandingsDAO()) }
        this.teams
    }

    public Team getTeam(String id) {
        if ( !(this.teams) ) { populateCache(new StandingsDAO()) }
        this.teams.find { it.id == id }
    }
}
