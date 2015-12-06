package org.domuique.fourteentwo.resource

import org.domuique.fourteentwo.model.Team
import org.domuique.fourteentwo.dao.StandingsDAO;

// TODO: MAKE TeamResource a factory instance
class TeamResource {

    private Collection<Team> teams

    private static TeamResource instance = new TeamResource()
    private TeamResource() { }
    public static TeamResource getInstance() { this.instance }

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
