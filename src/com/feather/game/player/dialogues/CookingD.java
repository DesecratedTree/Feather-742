package com.feather.game.player.dialogues;

import com.feather.game.GameObject;
import com.feather.game.player.actions.Cooking;
import com.feather.game.player.actions.Cooking.Cookables;
import com.feather.game.player.content.SkillsDialogue;

public class CookingD extends Dialogue {

	private Cookables cooking;
	private GameObject object;

	@Override
	public void start() {
		this.cooking = (Cookables) parameters[0];
		this.object = (GameObject) parameters[1];

		SkillsDialogue
				.sendSkillsDialogue(
						player,
						SkillsDialogue.COOK,
						"Choose how many you wish to cook,<br>then click on the item to begin.",
						player.getInventory().getItems()
								.getNumberOf(cooking.getRawItem()),
						new int[] { cooking.getProduct().getId() }, null);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		player.getActionManager().setAction(
				new Cooking(object, cooking.getRawItem(), SkillsDialogue
						.getQuantity(player)));
		end();
	}

	@Override
	public void finish() {

	}

}
