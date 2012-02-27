/**
 * TerrisWarn - A warning plugin for Bukkit.
 * Copyright (C) 2012 Terris Coding Team <vein@terrismc.info>
 * @author Terris Coding Team
 * @authors inspireVeiN, krisdestruction, dungeonmaster372
 * 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package info.terrismc.TerrisWarn;

import java.util.logging.Logger;

/**
 *
 * @author melvin
 */
public class TerrisWarn {
    private SuperPermissions superperms;
    public static final Logger log = Logger.getLogger("Minecraft");

    public enum Type { WARN, FREEZE };

    public enum PluginPermission {
        WARN("TerrisWarn.warn"),
        FREEZE("TerrisWarn.freeze");
        private final String permissionName;

        PluginPermission(String permissionName) {
            this.permissionName = permissionName;
        }

        public String getPermissionName() {
            return this.permissionName;
        }
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        getServer().getScheduler().cancelTasks(this);
        log.info("[" + pdfFile.getName() + "] Version "
                + pdfFile.getVersion() + " disabled.");
    }

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        PluginDescriptionFile pdfFile = this.getDescription();

        log.info("[" + pdfFile.getName() + "] Version "
                + pdfFile.getVersion() + " enabled.");

        TerrisWarnTempPlayerListener playerListener = new TerrisWarnTempPlayerListener(this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);

    }

    // @author andersonhc
    public Player findPlayer(String name) {
        if (name != null) {
            Player player = getServer().getPlayer(name);
            if (player != null) {
                return player;
            }

            int nameLength = name.length();
            Boolean found = false;
            for (Player current : getServer().getOnlinePlayers()) {
                if (current.getName().substring(0, nameLength - 1).equalsIgnoreCase(name)) {
                    if (found) {
                        return null;
                    }
                    found = true;
                    player = current;
                }
            }
            return player;
        }
        return null;
    }
    
    public boolean hasPermission(CommandSender sender, PluginPermission perm) {
        return superperms.hasPerm(sender, perm);
    }
}
