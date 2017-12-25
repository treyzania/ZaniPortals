package com.treyzania.mc.zaniportals.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.treyzania.mc.zaniportals.cmd.AbstractPortalCommand;

public class SpongeCommandAdapter implements CommandCallable {

	private AbstractPortalCommand command;
	
	public SpongeCommandAdapter(AbstractPortalCommand cmd) {
		this.command = cmd;
	}
	
	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		
		String[] args = arguments.split(" +"); // Match at least one space.
		boolean succ = this.command.execute(new SpongeCommandSender(source), args);
		return succ ? CommandResult.success() : CommandResult.empty();
		
	}

	@Override
	public List<String> getSuggestions(CommandSource source, String arguments, Location<World> targetPosition) throws CommandException {
		return new ArrayList<>(); // We don't have support for this, so ignore it.
	}

	@Override
	public boolean testPermission(CommandSource source) {
		return true; // The commands are supposed to handle permission on their own, so at worse players will see commands they can't actually run themselves.
	}

	@Override
	public Optional<Text> getShortDescription(CommandSource source) {
		return Optional.of(Text.of("ZaniPortals command " + this.command.getName()));
	}

	@Override
	public Optional<Text> getHelp(CommandSource source) {
		return Optional.empty();
	}

	@Override
	public Text getUsage(CommandSource source) {
		return Text.of("/" + this.command.getName() + " <arguments...>");
	}

}
