package com.feather.game.player;

import java.io.Serializable;
import java.util.HashMap;

import com.feather.cache.parser.ItemDefinitions;
import com.feather.game.item.Item;
import com.feather.game.player.content.ItemConstants;
import com.feather.utils.Utils;

public class ChargesManager implements Serializable {

	private static final long serialVersionUID = -5978513415281726450L;

	private transient Player player;
	private final HashMap<Integer, Integer> charges;

	public ChargesManager() {
		charges = new HashMap<>();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void process() {
		Item[] items = player.getEquipment().getItems().getItems();

		for (int slot = 0; slot < items.length; slot++) {
			Item item = items[slot];
			if (item == null)
				continue;

			if (player.getAttackedByDelay() > Utils.currentTimeMillis()) {
				int newId = ItemConstants.getDegradeItemWhenCombating(item.getId());
				if (newId != -1) {
					item.setId(newId);
					player.getEquipment().refresh(slot);
					player.getAppearance().loadAppearanceBlock();
					player.getPackets().sendGameMessage(
							"Your " + item.getDefinitions().getName() + " has degraded."
					);
				}
			}

			int defaultCharges = ItemConstants.getItemDefaultCharges(item.getId());
			if (defaultCharges == -1)
				continue;

			if (ItemConstants.itemDegradesWhileWearing(item.getId()))
				degrade(item.getId(), defaultCharges, slot);
			else if (player.getAttackedByDelay() > Utils.currentTimeMillis())
				degrade(item.getId(), defaultCharges, slot);
		}
	}

	public void die() {
		// Degrade equipment completely on death
		Item[] equipItems = player.getEquipment().getItems().getItems();
		for (int slot = 0; slot < equipItems.length; slot++) {
			if (equipItems[slot] != null && degradeCompletely(equipItems[slot]))
				player.getEquipment().getItems().set(slot, null);
		}

		// Degrade inventory items completely on death
		Item[] invItems = player.getInventory().getItems().getItems();
		for (int slot = 0; slot < invItems.length; slot++) {
			if (invItems[slot] != null && degradeCompletely(invItems[slot]))
				player.getInventory().getItems().set(slot, null);
		}
	}

	public boolean degradeCompletely(Item item) {
		int defaultCharges = ItemConstants.getItemDefaultCharges(item.getId());
		if (defaultCharges == -1)
			return false;

		while (true) {
			if (ItemConstants.itemDegradesWhileWearing(item.getId()) ||
					ItemConstants.itemDegradesWhileCombating(item.getId())) {

				charges.remove(item.getId());

				int newId = ItemConstants.getItemDegrade(item.getId());
				if (newId == -1)
					return ItemConstants.getItemDefaultCharges(item.getId()) != -1;

				item.setId(newId);

			} else {
				int newId = ItemConstants.getItemDegrade(item.getId());
				if (newId != -1) {
					charges.remove(item.getId());
					item.setId(newId);
				}
				break;
			}
		}
		return false;
	}

	public void wear(int slot) {
		Item item = player.getEquipment().getItems().get(slot);
		if (item == null)
			return;

		int newId = ItemConstants.getDegradeItemWhenWear(item.getId());
		if (newId == -1)
			return;

		player.getEquipment().getItems().set(slot, new Item(newId, 1));
		player.getEquipment().refresh(slot);
		player.getAppearance().loadAppearanceBlock();
		player.getPackets().sendGameMessage(
				"Your " + item.getDefinitions().getName() + " has degraded."
		);
	}

	private void degrade(int itemId, int defaultCharges, int slot) {
		Integer currentCharges = charges.remove(itemId);
		if (currentCharges == null)
			currentCharges = defaultCharges;
		else {
			currentCharges--;

			if (currentCharges == 0) {
				int newId = ItemConstants.getItemDegrade(itemId);
				player.getEquipment().getItems().set(slot, newId != -1 ? new Item(newId, 1) : null);

				if (newId == -1)
					player.getPackets().sendGameMessage(
							"Your " + ItemDefinitions.getItemDefinitions(itemId).getName() + " has degraded to dust."
					);
				else
					player.getPackets().sendGameMessage(
							"Your " + ItemDefinitions.getItemDefinitions(itemId).getName() + " has degraded."
					);

				player.getEquipment().refresh(slot);
				player.getAppearance().loadAppearanceBlock();
				return;
			}
		}

		charges.put(itemId, currentCharges);
	}
}
