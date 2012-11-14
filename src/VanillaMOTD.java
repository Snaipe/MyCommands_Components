import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.exception.CommandException;
import org.spout.api.plugin.Plugin;
import org.spout.vanilla.configuration.VanillaConfiguration;

public class VanillaMOTD {
    
    @SuppressWarnings("unused")
    private Plugin plugin;
    
    public VanillaMOTD(Plugin plugin)
    {
        this.plugin = plugin;
    }
    
    @Command(aliases = {"motd"}, desc = "Gets or sets the MOTD")
    @CommandPermissions("mycommands.motd")
    public void motd(CommandContext args, CommandSource source) throws CommandException
    {
        if ( args.length() == 0 )
            source.sendMessage(ChatStyle.CYAN, VanillaConfiguration.MOTD.getString());
        else
        {
            String motd = args.getJoinedString(0).getPlainString();
            VanillaConfiguration.MOTD.setValue(motd);
            source.sendMessage(ChatStyle.CYAN, "Message of the day set to : " + motd);
        }
    }
}
