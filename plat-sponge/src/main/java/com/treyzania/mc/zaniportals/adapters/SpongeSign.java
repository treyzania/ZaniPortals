package com.treyzania.mc.zaniportals.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableDirectionalData;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableSignData;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.world.Axis;
import com.treyzania.mc.zaniportals.world.Face;

public class SpongeSign extends SpongeBlock implements PortalSign {

	public SpongeSign(Location<World> loc) {
		super(loc);
	}

	@Override
	public void setLine(int line, String text) {
		
		// First we have to fix the text information.
		Text t = TextSerializers.FORMATTING_CODE.deserialize(text);
		
		// Now pull out the sign data itself.
		BlockState state = this.loc.getBlock();
		Optional<ImmutableSignData> oisd = state.get(ImmutableSignData.class);
		
		// Magic.
		if (oisd.isPresent()) {
			
			SignData sd = oisd.get().asMutable();
			List<Text> updated = new ArrayList<>(sd.asList());
			updated.set(line, t); // This is where we actually update it.
			sd.setElements(updated);
			this.loc.setBlock(state.with(sd.asImmutable()).get(), Cause.source(ZaniPortals.plugin).build());
			
		}
		
	}

	@Override
	public String getLine(int line) {
		
		BlockState state = this.loc.getBlock();
		Optional<ImmutableSignData> oisd = state.get(ImmutableSignData.class);
		
		if (oisd.isPresent()) {
			return TextSerializers.FORMATTING_CODE.serialize(oisd.get().get(line).get());
		} else {
			return null; // This *probably shouldn't* ever happen.
		}
		
	}

	@Override
	public Axis getLockedAxis() {
		return this.getFace().getAxis();
	}

	@Override
	public Face getFace() {

		BlockState state = this.loc.getBlock();
		Optional<ImmutableDirectionalData> oidd = state.get(ImmutableDirectionalData.class);
		
		if (oidd.isPresent()) {
			
			// Now we have to do a hacky little mapping of enums.
			switch (oidd.get().direction().get()) {
				case NORTH:
					return Face.NORTH;
				case SOUTH:
					return Face.SOUTH;
				case EAST:
					return Face.EAST;
				case WEST:
					return Face.WEST;
				case UP:
					return Face.UP;
				case DOWN:
					return Face.DOWN;
				default:
					return null;
			}
			
		} else {
			return null; // This *probably shouldn't* ever happen either.
		}
		
	}

	@Override
	public PortalBlock getBlockAttachedOnto() {
		// TODO Auto-generated method stub
		return null;
	}

}
