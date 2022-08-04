package net.caradr42.fumofumo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//debug command to spawn a cirno fumo
public class fumocommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage("Only players can use that command");
            return true;
        }
        Player player = (Player) commandSender;
        if (command.getName().equalsIgnoreCase("givecirno")){
            player.getInventory().addItem(manager.cirno);
        }
        return true;
    }
}
