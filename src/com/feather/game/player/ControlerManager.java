package com.feather.game.player;

import java.io.Serializable;

import com.feather.Settings;
import com.feather.game.Entity;
import com.feather.game.GameObject;
import com.feather.game.Tile;
import com.feather.game.item.Item;
import com.feather.game.npc.NPC;
import com.feather.game.player.content.Foods.Food;
import com.feather.game.player.content.Pots.Pot;
import com.feather.game.player.controlers.Controler;
import com.feather.game.player.controlers.ControlerHandler;

public final class ControlerManager implements Serializable {

	private static final long serialVersionUID = 2084691334731830796L;

	private transient Player player;
	private transient Controler controler;
	private transient boolean inited;
	private Object[] lastControlerArguments;

	private String lastControler;

	public ControlerManager() {
		lastControler = /*Settings.HOSTED ? */Settings.START_CONTROLER;//: "TutorialIsland"; // se
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Controler getControler() {
		return controler;
	}
	
	public void startControler(Object key, Object... parameters) {
		if (controler != null)
			forceStop();
		controler = (Controler) (key instanceof Controler ? key : ControlerHandler.getControler(key));
		if (controler == null)
			return;
		controler.setPlayer(player);
		lastControlerArguments = parameters;
		lastControler = (String) key;
		controler.start();
		inited = true;
	}

	public void login() {
		if (lastControler == null)
			return;
		controler = ControlerHandler.getControler(lastControler);
		if (controler == null) {
			forceStop();
			return;
		}
		controler.setPlayer(player);
		if (controler.login())
			forceStop();
		else
			inited = true;
	}

	public void logout() {
		if (controler == null)
			return;
		if (controler.logout())
			forceStop();
	}

	public boolean canMove(int dir) {
		if (controler == null || !inited)
			return true;
		return controler.canMove(dir);
	}

	public boolean checkWalkStep(int lastX, int lastY, int nextX, int nextY) {
		if (controler == null || !inited)
			return true;
		return controler.checkWalkStep(lastX, lastY, nextX, nextY);
	}

	public boolean keepCombating(Entity target) {
		if (controler == null || !inited)
			return true;
		return controler.keepCombating(target);
	}

	public boolean canEquip(int slotId, int itemId) {
		if (controler == null || !inited)
			return true;
		return controler.canEquip(slotId, itemId);
	}

	public boolean canAddInventoryItem(int itemId, int amount) {
		if (controler == null || !inited)
			return true;
		return controler.canAddInventoryItem(itemId, amount);
	}

	public void trackXP(int skillId, int addedXp) {
		if (controler == null || !inited)
			return;
		controler.trackXP(skillId, addedXp);
	}

	public boolean canDeleteInventoryItem(int itemId, int amount) {
		if (controler == null || !inited)
			return true;
		return controler.canDeleteInventoryItem(itemId, amount);
	}

	public boolean canUseItemOnItem(Item itemUsed, Item usedWith) {
		if (controler == null || !inited)
			return true;
		return controler.canUseItemOnItem(itemUsed, usedWith);
	}

	public boolean canAttack(Entity entity) {
		if (controler == null || !inited)
			return true;
		return controler.canAttack(entity);
	}

	public boolean canPlayerOption1(Player target) {
		if (controler == null || !inited)
			return true;
		return controler.canPlayerOption1(target);
	}

	public boolean canHit(Entity entity) {
		if (controler == null || !inited)
			return true;
		return controler.canHit(entity);
	}

	public void moved() {
		if (controler == null || !inited)
			return;
		controler.moved();
	}

	public void magicTeleported(int type) {
		if (controler == null || !inited)
			return;
		controler.magicTeleported(type);
	}

	public void sendInterfaces() {
		if (controler == null || !inited)
			return;
		controler.sendInterfaces();
	}

	public void process() {
		if (controler == null || !inited)
			return;
		controler.process();
	}

	public boolean sendDeath() {
		if (controler == null || !inited)
			return true;
		return controler.sendDeath();
	}

	public boolean canEat(Food food) {
		if (controler == null || !inited)
			return true;
		return controler.canEat(food);
	}

	public boolean canPot(Pot pot) {
		if (controler == null || !inited)
			return true;
		return controler.canPot(pot);
	}

	public boolean useDialogueScript(Object key) {
		if (controler == null || !inited)
			return true;
		return controler.useDialogueScript(key);
	}

	public boolean processMagicTeleport(Tile toTile) {
		if (controler == null || !inited)
			return true;
		return controler.processMagicTeleport(toTile);
	}

	public boolean processItemTeleport(Tile toTile) {
		if (controler == null || !inited)
			return true;
		return controler.processItemTeleport(toTile);
	}

	public boolean processObjectTeleport(Tile toTile) {
		if (controler == null || !inited)
			return true;
		return controler.processObjectTeleport(toTile);
	}

	public boolean processObjectClick1(GameObject object) {
		if (controler == null || !inited)
			return true;
		return controler.processObjectClick1(object);
	}

	public boolean processButtonClick(int interfaceId, int componentId,
			int slotId, int packetId) {
		if (controler == null || !inited)
			return true;
		return controler.processButtonClick(interfaceId, componentId, slotId,
				packetId);
	}

	public boolean processNPCClick1(NPC npc) {
		if (controler == null || !inited)
			return true;
		return controler.processNPCClick1(npc);
	}
	
	public boolean canSummonFamiliar() {
		if (controler == null || !inited)
			return true;
		return controler.canSummonFamiliar();
	}

	public boolean processNPCClick2(NPC npc) {
		if (controler == null || !inited)
			return true;
		return controler.processNPCClick2(npc);
	}
	public boolean processNPCClick3(NPC npc) {
		if (controler == null || !inited)
			return true;
		return controler.processNPCClick3(npc);
	}
	public boolean processObjectClick2(GameObject object) {
		if (controler == null || !inited)
			return true;
		return controler.processObjectClick2(object);
	}

	public boolean processObjectClick3(GameObject object) {
		if (controler == null || !inited)
			return true;
		return controler.processObjectClick3(object);
	}

	public boolean processItemOnNPC(NPC npc, Item item) {
		if (controler == null || !inited)
			return true;
		return controler.processItemOnNPC(npc, item);
	}
	
	public boolean canDropItem(Item item) {
		if (controler == null || !inited)
			return true;
		return controler.canDropItem(item);
	}

	public void forceStop() {
		if (controler != null) {
			controler.forceClose();
			controler = null;
		}
		lastControlerArguments = null;
		lastControler = null;
		inited = false;
	}

	public void removeControlerWithoutCheck() {
		controler = null;
		lastControlerArguments = null;
		lastControler = null;
		inited = false;
	}

	public Object[] getLastControlerArguments() {
		return lastControlerArguments;
	}

	public void setLastControlerArguments(Object[] lastControlerArguments) {
		this.lastControlerArguments = lastControlerArguments;
	}

	public boolean processObjectClick4(GameObject object) {
		return true; //unused atm
	}
	
	public boolean processObjectClick5(GameObject object) {
		if (controler == null || !inited)
			return true;
		return controler.processObjectClick5(object);
	}

	public boolean processItemOnPlayer(Player p2, int junk1) {
		return false;
	}
}
