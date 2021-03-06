import java.util.HashMap;
import java.util.Map;

import net.minekingdom.MyCommands.annotated.NestedCommand;

import org.spout.api.command.CommandArguments;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.Permissible;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.geo.discrete.Point;

public class Waypoint {
	
	@Command(aliases = {"waypoint", "wp"}, desc = "The main waypoint command", max = 1)
	@NestedCommand(value = WaypointSubCommands.class, ignoreBody = false)
	public void waypoint(CommandSource source, CommandArguments args) throws CommandException {
	    if (source instanceof Player && args.length() == 1) {
	        source.processCommand("waypoint", "tpto", args.getString(0));
	    }
	}
	
	public static class WaypointSubCommands {
	    
	    private Map<String, Point> waypoints;

	    public WaypointSubCommands() {
	        this.waypoints = new HashMap<String, Point>();
	    }
	    
	    @Command(aliases = {"create", "make", "mk"}, desc = "Creates a new waypoint", min = 1, max = 1)
	    @Permissible("plugintest.waypoint.create")
	    public void create(CommandSource source, CommandArguments args) throws CommandException {
	        if (!(source instanceof Player)) {
	            throw new CommandException("Only players may create waypoints.");
	        }
	        
	        String name = args.getString(0);
	        Player player = (Player) source;
	        
	        if (this.waypoints.containsKey(name)) {
	            throw new CommandException("The \"" + name + "\" waypoint already exists.");
	        }
	        
	        this.waypoints.put(name, player.getScene().getPosition());
	        player.sendMessage("Waypoint \"" + name + "\" has been created.");
	    }
	    
	    @Command(aliases = {"list", "ls"}, desc = "Lists the existing waypoints", min = 0, max = 0)
	    @Permissible("plugintest.waypoint.list")
	    public void list(CommandSource source, CommandArguments args) throws CommandException {
	        for (Map.Entry<String, Point> entry : this.waypoints.entrySet()) {
	            Point p = entry.getValue();
	            source.sendMessage(entry.getKey() + " -> (" + p.getWorld().getName() + ":" + p.getX() + "," + p.getY() + "," + p.getZ() + ")");
	        }
	    }
	    
	    @Command(aliases = {"remove", "rm"}, desc = "Removes an existing waypoint", min = 1, max = 1)
	    @Permissible("plugintest.waypoint.remove")
	    public void remove(CommandSource source, CommandArguments args) throws CommandException {
	        String name = args.getString(0);
	        if (this.waypoints.containsKey(name)) {
	            throw new CommandException("The \"" + name + "\" waypoint does not exist.");
	        }
	        this.waypoints.remove(name);
	        source.sendMessage("Waypoint \"" + name + "\" has been removed.");
	    }
	    
	    @Command(aliases = {"teleportto", "tpto"}, desc = "Teleports the player to the waypoint", min = 1, max = 1)
	    @Permissible("plugintest.waypoint.tpto")
	    public void tpto(CommandSource source, CommandArguments args) throws CommandException {
	        if (!(source instanceof Player)) {
	            throw new CommandException("Only players may teleport to waypoints, silly.");
	        }
	        
	        String name = args.getString(0);
	        Player player = (Player) source;
	        Point destination = this.waypoints.get(name);
	        
	        if (destination == null) {
	            throw new CommandException("The \"" + name + "\" waypoint does not exist.");
	        }
	        
	        player.teleport(destination);
	        player.sendMessage("Teleported to waypoint.");
	    }
	}
}
