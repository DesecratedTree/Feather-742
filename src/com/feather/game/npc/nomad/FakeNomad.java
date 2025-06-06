package com.feather.game.npc.nomad;

import com.feather.game.Hit;
import com.feather.game.Tile;
import com.feather.game.npc.NPC;

@SuppressWarnings("serial")
public class FakeNomad extends NPC {
	
	private Nomad nomad;
	
	public FakeNomad(Tile tile, Nomad nomad) {
		super(8529, tile, -1, true, true);
		this.nomad = nomad;
		setForceMultiArea(true);
	}
	
	@Override
	public void handleIngoingHit(Hit hit) {
		nomad.destroyCopy(this);
	}
	
}
