import net.minekingdom.MyCommands.MyCommands;
import net.minekingdom.MyCommands.annotated.CommandPlatform;

import org.spout.api.Platform;
import org.spout.api.Server;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.exception.CommandException;
import org.spout.api.plugin.Plugin;
import org.spout.vanilla.VanillaPlugin;
import org.spout.vanilla.data.configuration.VanillaConfiguration;

public class VanillaMOTD {

    private MyCommands plugin;
    
    public VanillaMOTD(Plugin plugin) {
        this.plugin = (MyCommands) plugin;
    }
    
    @Command(aliases = {"motd"}, desc = "Gets or sets the MOTD")
    @CommandPermissions("mycommands.motd")
    @CommandPlatform(Platform.SERVER)
    public void motd(CommandContext args, CommandSource source) throws CommandException
    {
        if ( args.length() == 0 ) {
            source.sendMessage(ChatStyle.CYAN, VanillaConfiguration.MOTD.getString());
        } else {
            String motd = args.getJoinedString(0).getPlainString();
            VanillaConfiguration.MOTD.setValue(motd);
            VanillaPlugin.getInstance().getConfig().save();
            
            ((Server) plugin.getEngine()).broadcastMessage(ChatStyle.CYAN, source.getName() + " has set the message of the day to : " + motd);
        }
    }
}
