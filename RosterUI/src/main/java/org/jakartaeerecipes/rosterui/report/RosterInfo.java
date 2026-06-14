package org.jakartaeerecipes.rosterui.report;

import java.util.List;
import org.jakartaeerecipes.rosterui.model.Roster;

/**
 *
 * @author Juneau
 */
public record RosterInfo(String team, List<Roster> players) {
}
