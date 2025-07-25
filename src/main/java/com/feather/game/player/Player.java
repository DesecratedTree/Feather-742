package com.feather.game.player;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import com.feather.Settings;
import com.feather.cores.CoresManager;
import com.feather.game.*;
import com.feather.game.Hit.HitLook;
import com.feather.game.item.FloorItem;
import com.feather.game.item.Item;
import com.feather.game.minigames.clanwars.FfaZone;
import com.feather.game.minigames.clanwars.WarControler;
import com.feather.game.minigames.duel.DuelArena;
import com.feather.game.minigames.duel.DuelRules;
import com.feather.game.npc.NPC;
import com.feather.game.npc.familiar.Familiar;
import com.feather.game.npc.godwars.zaros.Nex;
import com.feather.game.npc.pet.Pet;
import com.feather.game.player.actions.PlayerCombat;
import com.feather.game.player.actions.Slayer.Master;
import com.feather.game.player.actions.Slayer.SlayerTask;
import com.feather.game.player.content.FriendChatsManager;
import com.feather.game.player.content.GraveStone;
import com.feather.game.player.content.LodeStones;
import com.feather.game.player.content.MoneyPouch;
import com.feather.game.player.content.Notes;
import com.feather.game.player.content.PlayerDesign;
import com.feather.game.player.content.Pots;
import com.feather.game.player.content.ShootingStar;
import com.feather.game.player.content.SkillCapeCustomizer;
import com.feather.game.player.content.pet.PetManager;
import com.feather.game.player.controlers.CorpBeastControler;
import com.feather.game.player.controlers.CrucibleControler;
import com.feather.game.player.controlers.DTControler;
import com.feather.game.player.controlers.FightCaves;
import com.feather.game.player.controlers.FightKiln;
import com.feather.game.player.controlers.GodWars;
import com.feather.game.player.controlers.NomadsRequiem;
import com.feather.game.player.controlers.QueenBlackDragonController;
import com.feather.game.player.controlers.Wilderness;
import com.feather.game.player.controlers.ZGDControler;
import com.feather.game.player.controlers.castlewars.CastleWarsPlaying;
import com.feather.game.player.controlers.castlewars.CastleWarsWaiting;
import com.feather.game.player.controlers.fightpits.FightPitsArena;
import com.feather.game.player.controlers.pestcontrol.PestControlGame;
import com.feather.game.player.controlers.pestcontrol.PestControlLobby;
import com.feather.game.tasks.WorldTask;
import com.feather.game.tasks.WorldTasksManager;
import com.feather.net.Session;
import com.feather.net.decoders.WorldPacketsDecoder;
import com.feather.net.decoders.handlers.ButtonHandler;
import com.feather.net.encoders.WorldPacketsEncoder;
import com.feather.utils.IsaacKeyPair;
import com.feather.utils.Logger;
import com.feather.utils.MachineInformation;
import com.feather.utils.PkRank;
import com.feather.utils.SerializableFilesManager;
import com.feather.utils.Utils;

public class Player extends Entity {

	public static final int TELE_MOVE_TYPE = 127, WALK_MOVE_TYPE = 1,
			RUN_MOVE_TYPE = 2;

	private static final long serialVersionUID = 2011932556974180375L;
	
	// Starter
	public boolean starter = false;
	
	// Godwars test
	public int bandos, armadyl, saradomin, zamorak = 0;
	
	// Clans
	public boolean inClanChat;
	public int getClanSize = 0;
	
	// Items On Death
	public boolean clickedButton;
	
	// Stars
	public boolean canTalkToSprite, taggedStar;
	
	// Shops
	public boolean isBuying;
	
	// Lodestones
	public boolean activatedDraynor, activatedFalador = false;
	
	// Lending
	public boolean isLendingItem = false;
	
	// Cooks Assistant Quest
	public boolean startedCooksAssistant = false;
	public boolean inProgressCooksAssistant = false;
	public boolean completedCooksAssistantQuest = false;
	public boolean hasGrainInHopper = false;
	public int questPoints = 0;
	
	// MoneyPouch
	private MoneyPouch pouch;
	public boolean hasOpen = false;
	
	// GraveStones
	private GraveStone stone;

	// transient stuff
	private transient String username;
	private transient Session session;
	private transient boolean clientLoadedMapRegion;
	private transient int displayMode;
	private transient int screenWidth;
	private transient int screenHeight;
	private static final int lastlogged = 0;
	public int cluenoreward;

	private static final ShootingStar ShootingStar = null;

	private transient InterfaceManager interfaceManager;
	private transient DialogueManager dialogueManager;
	private transient HintIconsManager hintIconsManager;
	private transient ActionManager actionManager;
	private transient CutscenesManager cutscenesManager;
	private transient PriceCheckManager priceCheckManager;
	private transient CoordsEvent coordsEvent;
	private transient FriendChatsManager currentFriendChat;
	private transient Trade trade;
	private transient DuelRules lastDuelRules;
	private transient IsaacKeyPair isaacKeyPair;
	private transient Pet pet;
	

	// used for packets logic
	private transient ConcurrentLinkedQueue<LogicPacket> logicPackets;

	// used for update
	private transient LocalPlayerUpdate localPlayerUpdate;
	private transient LocalNPCUpdate localNPCUpdate;

	private int temporaryMovementType;
	private boolean updateMovementType;

	// player stages
	private transient boolean started;
	private transient boolean running;

	private transient long packetsDecoderPing;
	private boolean moneyPouchShow;
	private transient boolean resting;
	private transient boolean canPvp;
	private transient boolean cantTrade;
	private transient long lockDelay; // used for doors and stuff like that
	private transient long foodDelay;
	private transient long potDelay;
	private transient long boneDelay;
	private transient Runnable closeInterfacesEvent;
	private transient long lastPublicMessage;
	private transient long polDelay;
	private transient List<Integer> switchItemCache;
	private transient boolean disableEquip;
	private transient MachineInformation machineInformation;
	private transient boolean spawnsMode;
	private transient boolean castedVeng;
	private transient boolean invulnerable;
	private transient double hpBoostMultiplier;
	private transient boolean largeSceneView;
	private transient RouteEvent routeEvent;


	// interface

	// saving stuff
	private String password;
	private int rights;
	private String displayName;
	private String lastIP;
	@SuppressWarnings("unused")
	private long creationDate;
	private long moneyPouch = 0;
	private Appearance appearance;
	private Inventory inventory;
	private Equipment equipment;
	private Skills skills;
	private CombatDefinitions combatDefinitions;
	private Prayer prayer;
	private Master master;
	private SlayerTask slayerTask;
	private Bank bank;
	private ControlerManager controlerManager;
	private MusicsManager musicsManager;
	private EmotesManager emotesManager;
	private FriendsIgnores friendsIgnores;
	private DominionTower dominionTower;
	private Familiar familiar;
	private AuraManager auraManager;
	private QuestManager questManager;
	private PetManager petManager;
	private byte runEnergy;
	private boolean allowChatEffects;
	private boolean mouseButtons;
	private int privateChatSetup;
	private int friendChatSetup;
	private int skullDelay;
	private int skullId;
	private boolean forceNextMapLoadRefresh;
	private long poisonImmune;
	private long fireImmune;
	private boolean killedQueenBlackDragon;
	private int runeSpanPoints;

	private int lastBonfire;
	public int lendMessage;
	private int[] pouches;
	private long displayTime;
	private long muted;
	private long jailed;
	private long banned;
	private boolean permBanned;
	private boolean filterGame;
	private boolean xpLocked;
	private boolean yellOff;

	//game bar status
	private int publicStatus;
	private int clanStatus;
	private int tradeStatus;
	private int assistStatus;

	private boolean donator;
	private boolean extremeDonator;
	private long donatorTill;
	private long extremeDonatorTill;

	//Recovery ques. & ans.
	private String recovQuestion;
	private String recovAnswer;

	private String lastMsg;

	//Used for storing recent ips and password
	private ArrayList<String> passwordList = new ArrayList<String>();
	private ArrayList<String> ipList = new ArrayList<String>();

	// honor
	private int killCount, deathCount;
	private ChargesManager charges;
	// barrows
	private boolean[] killedBarrowBrothers;
	private int hiddenBrother;
	private int barrowsKillCount;
	private int pestPoints;

	// skill capes customizing
	private int[] maxedCapeCustomized;
	private int[] completionistCapeCustomized;

	//completionistcape reqs
	private boolean completedFightCaves;
	private boolean completedFightKiln;
	private boolean wonFightPits;
	
	//crucible
	private boolean talkedWithMarv;
	private int crucibleHighScore;

	private int overloadDelay;
	private int prayerRenewalDelay;

	private String currentFriendChatOwner;
	private int summoningLeftClickOption;
	private List<String> ownedObjectsManagerKeys;

	//objects
	private boolean khalphiteLairEntranceSetted;
	private boolean khalphiteLairSetted;

	//supportteam
	private boolean isSupporter;
	
	//FairyRing
	public int firstColumn = 1, secondColumn = 1, thirdColumn = 1;

	//voting
	private int votes;
	private boolean oldItemsLook;

	private String yellColor = "ff0000";
	
	private long voted;
	
	private boolean isGraphicDesigner;
	
	private boolean isForumModerator;
	// creates Player and saved classes
	public Player(String password) {
		super(/*Settings.HOSTED ? */Settings.START_PLAYER_LOCATION/* : new WorldTile(3095, 3107, 0)*/);
		setHitpoints(Settings.START_PLAYER_HITPOINTS);
		this.password = password;
		appearance = new Appearance();
		inventory = new Inventory();
		equipment = new Equipment();
		skills = new Skills();
		combatDefinitions = new CombatDefinitions();
		prayer = new Prayer();
		bank = new Bank();
		controlerManager = new ControlerManager();
		musicsManager = new MusicsManager();
		emotesManager = new EmotesManager();
		friendsIgnores = new FriendsIgnores();
		dominionTower = new DominionTower();
		charges = new ChargesManager();
		auraManager = new AuraManager();
		questManager = new QuestManager();
		petManager = new PetManager();
		runEnergy = 100;
		allowChatEffects = true;
		mouseButtons = true;
		pouches = new int[4];
		resetBarrows();
		SkillCapeCustomizer.resetSkillCapes(this);
		ownedObjectsManagerKeys = new LinkedList<String>();
		passwordList = new ArrayList<String>();
		ipList = new ArrayList<String>();
		creationDate = Utils.currentTimeMillis();
	}

	public void init(Session session, String username, int displayMode,
			int screenWidth, int screenHeight, MachineInformation machineInformation, IsaacKeyPair isaacKeyPair) {
		// temporary deleted after reset all chars
		if (dominionTower == null) {
			dominionTower = new DominionTower();
		}
		if (auraManager == null) {
			auraManager = new AuraManager();
		}
		if(questManager == null) {
			questManager = new QuestManager();
		}
		if (petManager == null) {
			petManager = new PetManager();
		}
		if (pouch == null) {
			pouch = new MoneyPouch(this);
		}
		this.session = session;
		this.username = username;
		this.displayMode = displayMode;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.machineInformation = machineInformation;
		this.isaacKeyPair = isaacKeyPair;
		notes = new Notes(this);
		interfaceManager = new InterfaceManager(this);
		dialogueManager = new DialogueManager(this);
		hintIconsManager = new HintIconsManager(this);
		priceCheckManager = new PriceCheckManager(this);
		localPlayerUpdate = new LocalPlayerUpdate(this);
		localNPCUpdate = new LocalNPCUpdate(this);
		actionManager = new ActionManager(this);
		cutscenesManager = new CutscenesManager(this);
		trade = new Trade(this);
		// loads player on saved instances
		pouch.setPlayer(this);
		appearance.setPlayer(this);
		inventory.setPlayer(this);
		equipment.setPlayer(this);
		skills.setPlayer(this);
		combatDefinitions.setPlayer(this);
		prayer.setPlayer(this);
		bank.setPlayer(this);
		controlerManager.setPlayer(this);
		musicsManager.setPlayer(this);
		emotesManager.setPlayer(this);
		friendsIgnores.setPlayer(this);
		dominionTower.setPlayer(this);
		auraManager.setPlayer(this);
		charges.setPlayer(this);
		questManager.setPlayer(this);
		petManager.setPlayer(this);
		setDirection(Utils.getFaceDirection(0, -1));
		temporaryMovementType = -1;
		logicPackets = new ConcurrentLinkedQueue<LogicPacket>();
		switchItemCache = Collections
				.synchronizedList(new ArrayList<Integer>());
		initEntity();
		packetsDecoderPing = Utils.currentTimeMillis();
		World.addPlayer(this);
		World.updateEntityRegion(this);
		if (Settings.DEBUG)
			Logger.log(this, "Initiated player: " + username + ", pass: " + password);

		//Do not delete >.>, useful for security purpose. this wont waste that much space..
		if(passwordList == null)
			passwordList = new ArrayList<String>();
		if(ipList == null)
			ipList = new ArrayList<String>();
		updateIPnPass();
	}
	
	
	public void startLobby(Player player) {
		player.sendLobbyConfigs(player);
		friendsIgnores.setPlayer(this);
		friendsIgnores.init();
		player.getPackets().sendFriendsChatChannel();
		friendsIgnores.sendFriendsMyStatus(true);
	}

	public void sendLobbyConfigs(Player player) {
		for (int i = 0; i < Utils.DEFAULT_LOBBY_CONFIGS.length; i++) {
			int val = Utils.DEFAULT_LOBBY_CONFIGS[i];
			if (val != 0) {
				player.getPackets().sendConfig(i, val);
			}
		}
	}
	
	public void init(Session session, String username, IsaacKeyPair isaacKeyPair) {
		this.username = username;
		this.session = session;
		this.isaacKeyPair = isaacKeyPair;
		World.addLobbyPlayer(this);// .addLobbyPlayer(this);
		if (pouch == null)
			pouch = new MoneyPouch(this);
		if (Settings.DEBUG) {
			Logger.log(this, "Initiated player: " + username + ", pass: " + password);
		}
	}

	public void setWildernessSkull() {
		skullDelay = 3000; // 30minutes
		skullId = 0;
		appearance.loadAppearanceBlock();
	}

	public void setFightPitsSkull() {
		skullDelay = Integer.MAX_VALUE; //infinite
		skullId = 1;
		appearance.loadAppearanceBlock();
	}
	
	public void setSkullInfiniteDelay(int skullId) {
		skullDelay = Integer.MAX_VALUE; //infinite
		this.skullId = skullId;
		appearance.loadAppearanceBlock();
	}

	public void removeSkull() {
		skullDelay = -1;
		appearance.loadAppearanceBlock();
	}

	public boolean hasSkull() {
		return skullDelay > 0;
	}

	public int setSkullDelay(int delay) {
		return this.skullDelay = delay;
	}

	public void refreshSpawnedItems() {
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId)
					.getFloorItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if ((item.isInvisible() || item.isGrave())
						&& this != item.getOwner()
						|| item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendRemoveGroundItem(item);
			}
		}
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId)
					.getFloorItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if ((item.isInvisible() || item.isGrave())
						&& this != item.getOwner()
						|| item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendGroundItem(item);
			}
		}
	}

	public void refreshSpawnedObjects() {
		for (int regionId : getMapRegionsIds()) {
			List<WorldObject> spawnedObjects = World.getRegion(regionId)
					.getSpawnedObjects();
			if (spawnedObjects != null) {
				for (WorldObject object : spawnedObjects)
					if (object.getPlane() == getPlane())
						getPackets().sendSpawnedObject(object);
			}
			List<WorldObject> removedObjects = World.getRegion(regionId)
					.getRemovedObjects();
			if (removedObjects != null) {
				for (WorldObject object : removedObjects)
					if (object.getPlane() == getPlane())
						getPackets().sendDestroyObject(object);
			}
		}
	}

	// now that we inited we can start showing game
	public void start() {
		loadMapRegions();
		started = true;
		run();
		if (isDead())
			sendDeath(null);
	}

	public void stopAll() {
		stopAll(true);
	}

	public void stopAll(boolean stopWalk) {
		stopAll(stopWalk, true);
	}

	public void stopAll(boolean stopWalk, boolean stopInterface) {
		stopAll(stopWalk, stopInterface, true);
	}

	// as walk done clientsided
	public void stopAll(boolean stopWalk, boolean stopInterfaces, boolean stopActions) {
		if (stopInterfaces)
			closeInterfaces();
		if (stopWalk){
			coordsEvent = null;
			routeEvent = null;
			resetWalkSteps();
			getPackets().sendResetMinimapFlag();
		}
		if (stopActions)
			actionManager.forceStop();
		combatDefinitions.resetSpells(false);
	}

	@Override
	public void reset(boolean attributes) {
		super.reset(attributes);
		refreshHitPoints();
		hintIconsManager.removeAll();
		skills.restoreSkills();
		combatDefinitions.resetSpecialAttack();
		prayer.reset();
		combatDefinitions.resetSpells(true);
		resting = false;
		skullDelay = 0;
		foodDelay = 0;
		potDelay = 0;
		poisonImmune = 0;
		fireImmune = 0;
		castedVeng = false;
		setRunEnergy(100);
		appearance.loadAppearanceBlock();
	}

	@Override
	public void reset() {
		reset(true);
	}

	public void closeInterfaces() {
		if (interfaceManager.containsScreenInter())
			interfaceManager.closeScreenInterface();
		if (interfaceManager.containsInventoryInter())
			interfaceManager.closeInventoryInterface();
		dialogueManager.finishDialogue();
		if (closeInterfacesEvent != null) {
			closeInterfacesEvent.run();
			closeInterfacesEvent = null;
		}
	}

	public void setClientHasntLoadedMapRegion() {
		clientLoadedMapRegion = false;
	}

	@Override
	public void loadMapRegions() {
		boolean wasAtDynamicRegion = isAtDynamicRegion();
		super.loadMapRegions();
		clientLoadedMapRegion = false;
		if (isAtDynamicRegion()) {
			getPackets().sendDynamicMapRegion(!started);
			if (!wasAtDynamicRegion)
				localNPCUpdate.reset();
		} else {
			getPackets().sendMapRegion(!started);
			if (wasAtDynamicRegion)
				localNPCUpdate.reset();
		}
		forceNextMapLoadRefresh = false;
	}

	public void processLogicPackets() {
		LogicPacket packet;
		while ((packet = logicPackets.poll()) != null)
			WorldPacketsDecoder.decodeLogicPacket(this, packet);
	}

	@Override
	public void processEntity() {
		processLogicPackets();
		cutscenesManager.process();
		if (coordsEvent != null && coordsEvent.processEvent(this))
			coordsEvent = null;
		if (routeEvent != null && routeEvent.processEvent(this))
			routeEvent = null;
		super.processEntity();
		if (musicsManager.musicEnded())
			musicsManager.replayMusic();
		if (hasSkull()) {
			skullDelay--;
			if (!hasSkull())
				appearance.loadAppearanceBlock();
		}
		if (polDelay != 0 && polDelay <= Utils.currentTimeMillis()) {
			getPackets().sendGameMessage("The power of the light fades. Your resistance to melee attacks return to normal.");
			polDelay = 0;
		}
		if (overloadDelay > 0) {
			if (overloadDelay == 1 || isDead()) {
				Pots.resetOverLoadEffect(this);
				return;
			} else if ((overloadDelay - 1) % 25 == 0)
				Pots.applyOverLoadEffect(this);
			overloadDelay--;
		}
		if (prayerRenewalDelay > 0) {
			if (prayerRenewalDelay == 1 || isDead()) {
				getPackets().sendGameMessage("<col=0000FF>Your prayer renewal has ended.");
				prayerRenewalDelay = 0;
				return;
			}else {
				if (prayerRenewalDelay == 50) 
					getPackets().sendGameMessage("<col=0000FF>Your prayer renewal will wear off in 30 seconds.");
				if(!prayer.hasFullPrayerpoints()) {
					getPrayer().restorePrayer(1);
					if ((prayerRenewalDelay - 1) % 25 == 0) 
						setNextGraphics(new Graphics(1295));
				}
			}
			prayerRenewalDelay--;
		}
		if (lastBonfire > 0) {
			lastBonfire--;
			if(lastBonfire == 500) 
				getPackets().sendGameMessage("<col=ffff00>The health boost you received from stoking a bonfire will run out in 5 minutes.");
			else if (lastBonfire == 0) {
				getPackets().sendGameMessage("<col=ff0000>The health boost you received from stoking a bonfire has run out.");
				equipment.refreshConfigs(false);
			}
		}
		charges.process();
		auraManager.process();
		actionManager.process();
		prayer.processPrayer();
		controlerManager.process();

	}

	@Override
	public void processReceivedHits() {
		if (lockDelay > Utils.currentTimeMillis())
			return;
		super.processReceivedHits();
	}

	@Override
	public boolean needMasksUpdate() {
		return super.needMasksUpdate() || temporaryMovementType != -1
				|| updateMovementType;
	}

	@Override
	public void resetMasks() {
		super.resetMasks();
		temporaryMovementType = -1;
		updateMovementType = false;
		if (!clientHasLoadedMapRegion()) {
			// load objects and items here
			setClientHasLoadedMapRegion();
			refreshSpawnedObjects();
			refreshSpawnedItems();
		}
	}

	public void toogleRun(boolean update) {
		super.setRun(!getRun());
		updateMovementType = true;
		if (update)
			sendRunButtonConfig();
	}

	public void setRunHidden(boolean run) {
		super.setRun(run);
		updateMovementType = true;
	}

	@Override
	public void setRun(boolean run) {
		if (run != getRun()) {
			super.setRun(run);
			updateMovementType = true;
			sendRunButtonConfig();
		}
	}

	public void sendRunButtonConfig() {
		getPackets().sendConfig(173, resting ? 3 : getRun() ? 1 : 0);
	}

	public void restoreRunEnergy() {
		if (getNextRunDirection() == -1 && runEnergy < 100) {
			runEnergy++;
			if (resting && runEnergy < 100)
				runEnergy++;
			getPackets().sendRunEnergy();
		}
	}

	public void run() {
		if (World.exiting_start != 0) {
			int delayPassed = (int) ((Utils.currentTimeMillis() - World.exiting_start) / 1000);
			getPackets().sendSystemUpdate(World.exiting_delay - delayPassed);
		}
		lastIP = getSession().getIP();
		interfaceManager.sendInterfaces();
		
		if (starter == false) {
			PlayerDesign.open(this);
		}
		
		getPackets().sendRunEnergy();
		getPackets().sendItemsLook();
		refreshAllowChatEffects();
		refreshMouseButtons();
		refreshPrivateChatSetup();
		refreshOtherChatsSetup();
		sendRunButtonConfig();
		getPackets().sendGameMessage("Welcome to " + Settings.SERVER_NAME + ".");
		//temporary for next 2days
		donatorTill = 0;
		extremeDonatorTill = 0;

		if (extremeDonator || extremeDonatorTill != 0) {
			if (!extremeDonator && extremeDonatorTill < Utils.currentTimeMillis()) {
				getPackets().sendGameMessage("Your extreme donator rank expired.");
				extremeDonatorTill = 0;
			} else
				getPackets().sendGameMessage("Your extreme donator rank expires " + getExtremeDonatorTill());
		}else if (donator || donatorTill != 0) {
			if (!donator && donatorTill < Utils.currentTimeMillis()) {
				getPackets().sendGameMessage("Your donator rank expired.");
				donatorTill = 0;
			}else
				getPackets().sendGameMessage("Your donator rank expires " + getDonatorTill());
		}
		
		if (inProgressCooksAssistant == true) {
			getPackets().sendConfig(29, 1);
		}
		
		if (completedCooksAssistantQuest == true) {
			getPackets().sendConfig(29, 2);
		}
		if (hasOpen == true) {
			getPouch().toggleMoneyPouch();
			getPouch().refreshPouch(true);
		}
		getPackets().sendConfig(101, questPoints); // Quest Points
		isBuying = true;
		sendDefaultPlayersOptions();
		checkMultiArea();
		inventory.init();
		equipment.init();
		skills.init();
		combatDefinitions.init();
		prayer.init();
		friendsIgnores.init();
		refreshHitPoints();
		prayer.refreshPrayerPoints();
		getPoison().refresh();
		LodeStones.checkActivation(this);
		getPackets().sendConfig(281, 1000); // unlock can't do this on tutorial
		getPackets().sendConfig(1160, -1); // unlock summoning orb
		getPackets().sendConfig(1159, 1);
		getPackets().sendConfig(281, 1000); // Quest Drop Menu
		getPackets().sendConfig(1384, 512); // Quest Filter Button
		getPackets().sendConfig(1160, -1);
		getPackets().sendConfig(1384, 512); // Something to do with Quests. Idk
		getPackets().sendUnlockIComponentOptionSlots(190, 15, 0, 201, 0, 1, 2, 3); // Unlocks Quest Interface
		getPackets().sendGameBarStages();
		//getPackets().sendConfig(130, 3); // Black Knights Fortress Progress (Yellow)
		//getPackets().sendConfig(130, 4); // Black Knights Fortress Done (Green)
		getPackets().sendConfig(904, 333); // Maximum Quest Points in 2011 (326)
		getPackets().sendGameBarStages();
		musicsManager.init();
		emotesManager.refreshListConfigs();
		questManager.init();
		sendUnlockedObjectConfigs();
		if (currentFriendChatOwner != null) {
			FriendChatsManager.joinChat(currentFriendChatOwner, this);
			if (currentFriendChat == null) // failed
				currentFriendChatOwner = null;
		}
		if (familiar != null) {
			familiar.respawnFamiliar(this);
		} else {
			petManager.init();
		}
		running = true;
		updateMovementType = true;
		appearance.loadAppearanceBlock();
		controlerManager.login(); // checks what to do on login after welcome
		OwnedObjectManager.linkKeys(this);
		// screen
		if(machineInformation != null)
			machineInformation.sendSuggestions(this);
		Notes.unlock(this);
	}


	private void sendUnlockedObjectConfigs() {
		refreshKalphiteLairEntrance();
		refreshKalphiteLair();
		refreshFightKilnEntrance();
	}

	@SuppressWarnings("unused")
	private void refreshLodestoneNetwork() {
		//unlocks bandit camp lodestone
		getPackets().sendConfigByFile(358, 15);
		//unlocks lunar isle lodestone
		getPackets().sendConfigByFile(2448, 190);
		//unlocks alkarid lodestone
		getPackets().sendConfigByFile(10900, 1);
		//unlocks ardougne lodestone
		getPackets().sendConfigByFile(10901, 1);
		//unlocks burthorpe lodestone
		getPackets().sendConfigByFile(10902, 1);
		//unlocks catherbay lodestone
		getPackets().sendConfigByFile(10903, 1);
		//unlocks edgeville lodestone
		getPackets().sendConfigByFile(10905, 1);
		//unlocks falador lodestone
		getPackets().sendConfigByFile(10906, 1);
		//unlocks port sarim lodestone
		getPackets().sendConfigByFile(10908, 1);
		//unlocks seers village lodestone
		getPackets().sendConfigByFile(10909, 1);
		//unlocks taverley lodestone
		getPackets().sendConfigByFile(10910, 1);
		//unlocks varrock lodestone
		getPackets().sendConfigByFile(10911, 1);
		//unlocks yanille lodestone
		getPackets().sendConfigByFile(10912, 1);
	}


	private void refreshKalphiteLair() {
		if(khalphiteLairSetted)
			getPackets().sendConfigByFile(7263, 1);
	}

	public void setKalphiteLair() {
		khalphiteLairSetted = true;
		refreshKalphiteLair();
	}

	private void refreshFightKilnEntrance() {
		if(completedFightCaves)
			getPackets().sendConfigByFile(10838, 1);
	}

	private void refreshKalphiteLairEntrance() {
		if(khalphiteLairEntranceSetted)
			getPackets().sendConfigByFile(7262, 1);
	}

	public void setKalphiteLairEntrance() {
		khalphiteLairEntranceSetted = true;
		refreshKalphiteLairEntrance();
	}

	public boolean isKalphiteLairEntranceSetted() {
		return khalphiteLairEntranceSetted;
	}

	public boolean isKalphiteLairSetted() {
		return khalphiteLairSetted;
	}

	public void updateIPnPass() {
		if (getPasswordList().size() > 25)
			getPasswordList().clear();
		if (getIPList().size() > 50)
			getIPList().clear();
		if (!getPasswordList().contains(getPassword()))
			getPasswordList().add(getPassword());
		if (!getIPList().contains(getLastIP()))
			getIPList().add(getLastIP());
		return;
	}

	public void sendDefaultPlayersOptions() {
		getPackets().sendPlayerOption("Follow", 2, false);
		getPackets().sendPlayerOption("Trade with", 4, false);
		getPackets().sendPlayerOption("Req Assist", 5, false);
	}

	@Override
	public void checkMultiArea() {
		if (!started)
			return;
		boolean isAtMultiArea = isForceMultiArea() ? true : World
				.isMultiArea(this);
		if (isAtMultiArea && !isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 1);
		} else if (!isAtMultiArea && isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 0);
		}
	}

	/**
	 * Logs the player out.
	 * @param lobby If we're logging out to the lobby.
	 */
	public void logout(boolean lobby) {
		if (!running)
			return;
		long currentTime = Utils.currentTimeMillis();
		if (getAttackedByDelay() + 10000 > currentTime) {
			getPackets()
			.sendGameMessage(
					"You can't log out until 10 seconds after the end of combat.");
			return;
		}
		if (getEmotesManager().getNextEmoteEnd() >= currentTime) {
			getPackets().sendGameMessage(
					"You can't log out while performing an emote.");
			return;
		}
		if (lockDelay >= currentTime) {
			getPackets().sendGameMessage(
					"You can't log out while performing an action.");
			return;
		}
		getPackets().sendLogout(lobby && Settings.MANAGMENT_SERVER_ENABLED);
		running = false;
	}

	public void forceLogout() {
		getPackets().sendLogout(false);
		running = false;
		realFinish();	
	}

	private transient boolean finishing;

	private transient Notes notes;


	@Override
	public void finish() {
		finish(0);
	}

	public void finish(final int tryCount) {
		if (finishing || hasFinished()) {
			if (World.containsPlayer(username)) {
				World.removePlayer(this);
			}
			if (World.containsLobbyPlayer(username)) {
				World.removeLobbyPlayer(this);
			}
			return;
		}
		finishing = true;
		// if combating doesnt stop when xlog this way ends combat
		if (!World.containsLobbyPlayer(username)) {
			stopAll(false, true, !(actionManager.getAction() instanceof PlayerCombat));
		}
		long currentTime = Utils.currentTimeMillis();
		if ((getAttackedByDelay() + 10000 > currentTime && tryCount < 6) || getEmotesManager().getNextEmoteEnd() >= currentTime || lockDelay >= currentTime) {
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						packetsDecoderPing = Utils.currentTimeMillis();
						finishing = false;
						finish(tryCount + 1);
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 10, TimeUnit.SECONDS);
			return;
		}
		realFinish();
	}

	public void realFinish() {
		if (hasFinished()) {
			return;
		}
		if (!World.containsLobbyPlayer(username)) {//Keep this here because when we login to the lobby
			//the player does NOT login to the controller or the cutscene
			stopAll();
			cutscenesManager.logout();
			controlerManager.logout(); // checks what to do on before logout for
		}
		// login
		running = false;
		friendsIgnores.sendFriendsMyStatus(false);
		if (currentFriendChat != null) {
			currentFriendChat.leaveChat(this, true);
		}
		if (familiar != null && !familiar.isFinished()) {
			familiar.dissmissFamiliar(true);
		} else if (pet != null) {
			pet.finish();
		}
		setFinished(true);
		session.setDecoder(-1);
		//this.lastLoggedIn = System.currentTimeMillis();
		SerializableFilesManager.savePlayer(this);
		if (World.containsLobbyPlayer(username)) {
			World.removeLobbyPlayer(this);
		}
		World.updateEntityRegion(this);
		if (World.containsPlayer(username)) {
			World.removePlayer(this);
		}
		if (Settings.DEBUG) {
			Logger.log(this, "Finished Player: " + username + ", pass: " + password);
		}
	}

	@Override
	public boolean restoreHitPoints() {
		boolean update = super.restoreHitPoints();
		if (update) {
			if (prayer.usingPrayer(0, 9))
				super.restoreHitPoints();
			if (resting)
				super.restoreHitPoints();
			refreshHitPoints();
		}
		return update;
	}

	public void refreshHitPoints() {
		getPackets().sendConfigByFile(7198, getHitpoints());
	}

	@Override
	public void removeHitpoints(Hit hit) {
		super.removeHitpoints(hit);
		refreshHitPoints();
	}

	@Override
	public int getMaxHitpoints() {
		return skills.getLevel(Skills.HITPOINTS) * 10
				+ equipment.getEquipmentHpIncrease();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<String> getPasswordList() {
		return passwordList;
	}

	public ArrayList<String> getIPList() {
		return ipList;
	}

	public void setRights(int rights) {
		this.rights = rights;
	}

	public int getRights() {
		return rights;
	}

	public int getMessageIcon() {
		return getRights() == 2 || getRights() == 1 ? getRights() : isForumModerator() ? 10 : isGraphicDesigner() ? 9 : isExtremeDonator() ? 11 : isDonator() ? 8 : getRights();
	}

	public WorldPacketsEncoder getPackets() {
		return session.getWorldPackets();
	}

	public boolean hasStarted() {
		return started;
	}

	public boolean isRunning() {
		return running;
	}

	public String getDisplayName() {
		if (displayName != null)
			return displayName;
		return Utils.formatPlayerNameForDisplay(username);
	}

	public boolean hasDisplayName() {
		return displayName != null;
	}

	public Appearance getAppearance() {
		return appearance;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public int getTemporaryMoveType() {
		return temporaryMovementType;
	}

	public void setTemporaryMoveType(int temporaryMovementType) {
		this.temporaryMovementType = temporaryMovementType;
	}

	public LocalPlayerUpdate getLocalPlayerUpdate() {
		return localPlayerUpdate;
	}

	public LocalNPCUpdate getLocalNPCUpdate() {
		return localNPCUpdate;
	}

	public int getDisplayMode() {
		return displayMode;
	}

	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}

	public void setPacketsDecoderPing(long packetsDecoderPing) {
		this.packetsDecoderPing = packetsDecoderPing;
	}

	public long getPacketsDecoderPing() {
		return packetsDecoderPing;
	}

	public Session getSession() {
		return session;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public boolean clientHasLoadedMapRegion() {
		return clientLoadedMapRegion;
	}

	public void setClientHasLoadedMapRegion() {
		clientLoadedMapRegion = true;
	}

	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Skills getSkills() {
		return skills;
	}

	public byte getRunEnergy() {
		return runEnergy;
	}

	public void drainRunEnergy() {
		setRunEnergy(runEnergy - 1);
	}

	public void setRunEnergy(int runEnergy) {
		this.runEnergy = (byte) runEnergy;
		getPackets().sendRunEnergy();
	}

	public boolean isResting() {
		return resting;
	}

	public void setResting(boolean resting) {
		this.resting = resting;
		sendRunButtonConfig();
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public DialogueManager getDialogueManager() {
		return dialogueManager;
	}

	public CombatDefinitions getCombatDefinitions() {
		return combatDefinitions;
	}

	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0.6;
	}

	public void sendSoulSplit(final Hit hit, final Entity user) {
		final Player target = this;
		if (hit.getDamage() > 0)
			World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
		user.heal(hit.getDamage() / 5);
		prayer.drainPrayer(hit.getDamage() / 5);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				setNextGraphics(new Graphics(2264));
				if (hit.getDamage() > 0)
					World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0, 0);
			}
		}, 0);
	}

	@Override
	public void handleIngoingHit(final Hit hit) {
		if (hit.getLook() != HitLook.MELEE_DAMAGE
				&& hit.getLook() != HitLook.RANGE_DAMAGE
				&& hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		if(invulnerable) {
			hit.setDamage(0);
			return;
		}
		if (auraManager.usingPenance()) {
			int amount = (int) (hit.getDamage() * 0.2);
			if (amount > 0)
				prayer.restorePrayer(amount);
		}
		Entity source = hit.getSource();
		if (source == null)
			return;
		if (polDelay > Utils.currentTimeMillis())
			hit.setDamage((int) (hit.getDamage() * 0.5));
		if (prayer.hasPrayersOn() && hit.getDamage() != 0) {
			if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				if (prayer.usingPrayer(0, 17))
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 7)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2228));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				if (prayer.usingPrayer(0, 18))
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 8)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2229));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				if (prayer.usingPrayer(0, 19))
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 9)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2230));
						setNextAnimation(new Animation(12573));
					}
				}
			}
		}
		if (hit.getDamage() >= 200) {
			if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS]
								/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]
								/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS]
								/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			}
		}
		int shieldId = equipment.getShieldId();
		if (shieldId == 13742) { // elsyian
			if (Utils.getRandom(100) <= 70)
				hit.setDamage((int) (hit.getDamage() * 0.75));
		} else if (shieldId == 13740) { // divine
			int drain = (int) (Math.ceil(hit.getDamage() * 0.3) / 2);
			if (prayer.getPrayerpoints() >= drain) {
				hit.setDamage((int) (hit.getDamage() * 0.70));
				prayer.drainPrayer(drain);
			}
		}
		if (castedVeng && hit.getDamage() >= 4) {
			castedVeng = false;
			setNextForceTalk(new ForceTalk("Taste vengeance!"));
			source.applyHit(new Hit(this, (int) (hit.getDamage() * 0.75),
					HitLook.REGULAR_DAMAGE));
		}
		if (source instanceof Player) {
			final Player p2 = (Player) source;
			if (p2.prayer.hasPrayersOn()) {
				if (p2.prayer.usingPrayer(0, 24)) { // smite
					int drain = hit.getDamage() / 4;
					if (drain > 0)
						prayer.drainPrayer(drain);
				} else {
					if (hit.getDamage() == 0)
						return;
					if (!p2.prayer.isBoostedLeech()) {
						if (hit.getLook() == HitLook.MELEE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 19)) {
								if (Utils.getRandom(4) == 0) {
									p2.prayer.increaseTurmoilBonus(this);
									p2.prayer.setBoostedLeech(true);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 1)) { // sap att
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(0)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your sap curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(0);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Attack from the enemy, boosting your Attack.",
												true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2214));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2215, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2216));
										}
									}, 1);
									return;
								}
							} else {
								if (p2.prayer.usingPrayer(1, 10)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(3)) {
											p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
										} else {
											p2.prayer.increaseLeechBonus(3);
											p2.getPackets()
											.sendGameMessage(
													"Your curse drains Attack from the enemy, boosting your Attack.",
													true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2231,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2232));
													}
												}, 1);
										return;
									}
								}
								getPackets().sendItemsLook();
								if (lendMessage != 0) {
									if (lendMessage == 1)
										getPackets()
												.sendGameMessage(
														"<col=FF0000>An item you lent out has been added back to your bank.");
									else if (lendMessage == 2)
										getPackets()
												.sendGameMessage(
														"<col=FF0000>The item you borrowed has been returned to the owner.");
									lendMessage = 0;
								}
								if (p2.prayer.usingPrayer(1, 14)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(7)) {
											p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
										} else {
											p2.prayer.increaseLeechBonus(7);
											p2.getPackets()
											.sendGameMessage(
													"Your curse drains Strength from the enemy, boosting your Strength.",
													true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2248,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2250));
													}
												}, 1);
										return;
									}
								}

							}
						}
						if (hit.getLook() == HitLook.RANGE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 2)) { // sap range
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(1)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your sap curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(1);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Range from the enemy, boosting your Range.",
												true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2217));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2218, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2219));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 11)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(4)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your leech curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(4);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Range from the enemy, boosting your Range.",
												true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2236, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2238));
										}
									});
									return;
								}
							}
						}
						if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 3)) { // sap mage
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(2)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your sap curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(2);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Magic from the enemy, boosting your Magic.",
												true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2220));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2221, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2222));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 12)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(5)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your leech curse has no effect.",
												true);
									} else {
										p2.prayer.increaseLeechBonus(5);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Magic from the enemy, boosting your Magic.",
												true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2240, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2242));
										}
									}, 1);
									return;
								}
							}
						}

						// overall

						if (p2.prayer.usingPrayer(1, 13)) { // leech defence
							if (Utils.getRandom(10) == 0) {
								if (p2.prayer.reachedMax(6)) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your leech curse has no effect.",
											true);
								} else {
									p2.prayer.increaseLeechBonus(6);
									p2.getPackets()
									.sendGameMessage(
											"Your curse drains Defence from the enemy, boosting your Defence.",
											true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2244, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2246));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 15)) {
							if (Utils.getRandom(10) == 0) {
								if (getRunEnergy() <= 0) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your leech curse has no effect.",
											true);
								} else {
									p2.setRunEnergy(p2.getRunEnergy() > 90 ? 100
											: p2.getRunEnergy() + 10);
									setRunEnergy(p2.getRunEnergy() > 10 ? getRunEnergy() - 10
											: 0);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2256, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2258));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 16)) {
							if (Utils.getRandom(10) == 0) {
								if (combatDefinitions
										.getSpecialAttackPercentage() <= 0) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your leech curse has no effect.",
											true);
								} else {
									p2.combatDefinitions.restoreSpecialAttack();
									combatDefinitions
									.desecreaseSpecialAttack(10);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2252, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2254));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 4)) { // sap spec
							if (Utils.getRandom(10) == 0) {
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2223));
								p2.prayer.setBoostedLeech(true);
								if (combatDefinitions
										.getSpecialAttackPercentage() <= 0) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your sap curse has no effect.",
											true);
								} else {
									combatDefinitions
									.desecreaseSpecialAttack(10);
								}
								World.sendProjectile(p2, this, 2224, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2225));
									}
								}, 1);
								return;
							}
						}
					}
				}
			}
		} else {
			NPC n = (NPC) source;
			if (n.getId() == 13448)
				sendSoulSplit(hit, n);
		}
	}

	@Override
	public void sendDeath(final Entity source) {
		if (prayer.hasPrayersOn()
				&& getTemporaryAttributtes().get("startedDuel") != Boolean.TRUE) {
			if (prayer.usingPrayer(0, 22)) {
				setNextGraphics(new Graphics(437));
				final Player target = this;
				if (isAtMultiArea()) {
					for (int regionId : getMapRegionsIds()) {
						List<Integer> playersIndexes = World
								.getRegion(regionId).getPlayerIndexes();
						if (playersIndexes != null) {
							for (int playerIndex : playersIndexes) {
								Player player = World.getPlayers().get(
										playerIndex);
								if (player == null
										|| !player.hasStarted()
										|| player.isDead()
										|| player.hasFinished()
										|| !player.withinDistance(this, 1)
										|| !player.isCanPvp()
										|| !target.getControlerManager()
										.canHit(player))
									continue;
								player.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 2.5)),
												HitLook.REGULAR_DAMAGE));
							}
						}
						List<Integer> npcsIndexes = World.getRegion(regionId)
								.getNPCsIndexes();
						if (npcsIndexes != null) {
							for (int npcIndex : npcsIndexes) {
								NPC npc = World.getNPCs().get(npcIndex);
								if (npc == null
										|| npc.isDead()
										|| npc.hasFinished()
										|| !npc.withinDistance(this, 1)
										|| !npc.getDefinitions()
										.hasAttackOption()
										|| !target.getControlerManager()
										.canHit(npc))
									continue;
								npc.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 2.5)),
												HitLook.REGULAR_DAMAGE));
							}
						}
					}
				} else {
					if (source != null && source != this && !source.isDead()
							&& !source.hasFinished()
							&& source.withinDistance(this, 1))
						source.applyHit(new Hit(target, Utils
								.getRandom((int) (skills
										.getLevelForXp(Skills.PRAYER) * 2.5)),
										HitLook.REGULAR_DAMAGE));
				}
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1, target.getY(),
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1, target.getY(),
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() - 1,
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() + 1,
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1,
										target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1,
										target.getY() + 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1,
										target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1,
										target.getY() + 1, target.getPlane()));
					}
				});
			} else if (prayer.usingPrayer(1, 17)) {
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() + 2, getPlane()), 2260, 24, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() + 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX(), getY() + 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX(), getY() - 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				final Player target = this;
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						setNextGraphics(new Graphics(2259));

						if (isAtMultiArea()) {
							for (int regionId : getMapRegionsIds()) {
								List<Integer> playersIndexes = World.getRegion(
										regionId).getPlayerIndexes();
								if (playersIndexes != null) {
									for (int playerIndex : playersIndexes) {
										Player player = World.getPlayers().get(
												playerIndex);
										if (player == null
												|| !player.hasStarted()
												|| player.isDead()
												|| player.hasFinished()
												|| !player.isCanPvp()
												|| !player.withinDistance(
														target, 2)
														|| !target
														.getControlerManager()
														.canHit(player))
											continue;
										player.applyHit(new Hit(
												target,
												Utils.getRandom((skills
														.getLevelForXp(Skills.PRAYER) * 3)),
														HitLook.REGULAR_DAMAGE));
									}
								}
								List<Integer> npcsIndexes = World.getRegion(
										regionId).getNPCsIndexes();
								if (npcsIndexes != null) {
									for (int npcIndex : npcsIndexes) {
										NPC npc = World.getNPCs().get(npcIndex);
										if (npc == null
												|| npc.isDead()
												|| npc.hasFinished()
												|| !npc.withinDistance(target,
														2)
														|| !npc.getDefinitions()
														.hasAttackOption()
														|| !target
														.getControlerManager()
														.canHit(npc))
											continue;
										npc.applyHit(new Hit(
												target,
												Utils.getRandom((skills
														.getLevelForXp(Skills.PRAYER) * 3)),
														HitLook.REGULAR_DAMAGE));
									}
								}
							}
						} else {
							if (source != null && source != target
									&& !source.isDead()
									&& !source.hasFinished()
									&& source.withinDistance(target, 2))
								source.applyHit(new Hit(
										target,
										Utils.getRandom((skills
												.getLevelForXp(Skills.PRAYER) * 3)),
												HitLook.REGULAR_DAMAGE));
						}

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY() + 2,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY() - 2,
										getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY() + 2,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY() - 2,
										getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX(), getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX(), getY() - 2, getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 1, getY() + 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 1, getY() - 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 1, getY() + 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 1, getY() - 1,
										getPlane()));
					}
				});
			}
		}
		setNextAnimation(new Animation(-1));
		if (!controlerManager.sendDeath())
			return;
		lock(7);
		stopAll();
		if (familiar != null)
			familiar.sendDeath(this);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					getPackets().sendGameMessage("Oh dear, you have died.");
					if (source instanceof Player) {
						Player killer = (Player) source;
						killer.setAttackedByDelay(4);
					}
				} else if (loop == 3) {
					controlerManager.startControler("DeathEvent");
					//getGraveStone().deploy(getLastWorldTile(), drop, gravestoneNpcType, player, drops);
				} else if (loop == 4) {
					getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}

	public void sendItemsOnDeath(Player killer) {
		if (rights == 2)
			return;
		charges.die();
		auraManager.removeAura();
		CopyOnWriteArrayList<Item> containedItems = new CopyOnWriteArrayList<Item>();
		for (int i = 0; i < 14; i++) {
			if (equipment.getItem(i) != null
					&& equipment.getItem(i).getId() != -1
					&& equipment.getItem(i).getAmount() != -1)
				containedItems.add(new Item(equipment.getItem(i).getId(),
						equipment.getItem(i).getAmount()));
		}
		for (int i = 0; i < 28; i++) {
			if (inventory.getItem(i) != null
					&& inventory.getItem(i).getId() != -1
					&& inventory.getItem(i).getAmount() != -1)
				containedItems.add(new Item(getInventory().getItem(i).getId(),
						getInventory().getItem(i).getAmount()));
		}
		if (containedItems.isEmpty())
			return;
		int keptAmount = 0;
		if(!(controlerManager.getControler() instanceof CorpBeastControler)
				&& !(controlerManager.getControler() instanceof CrucibleControler)) {
			keptAmount = hasSkull() ? 0 : 3;
			if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0))
				keptAmount++;
		}
		if (donator && Utils.random(2) == 0)
			keptAmount += 1;
		CopyOnWriteArrayList<Item> keptItems = new CopyOnWriteArrayList<Item>();
		Item lastItem = new Item(1, 1);
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				int price = item.getDefinitions().getValue();
				if (price >= lastItem.getDefinitions().getValue()) {
					lastItem = item;
				}
			}
			keptItems.add(lastItem);
			containedItems.remove(lastItem);
			lastItem = new Item(1, 1);
		}
		inventory.reset();
		equipment.reset();
		for (Item item : keptItems) {
			getInventory().addItem(item);
		}
		for (Item item : containedItems) {
			World.addGroundItem(item, getLastWorldTile(), killer == null ? this : killer, false, 180,
					true, true);
		}
	}

	public void increaseKillCount(Player killed) {
		killed.deathCount++;
		PkRank.checkRank(killed);
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		killCount++;
		getPackets().sendGameMessage(
				"<col=ff0000>You have killed " + killed.getDisplayName()
				+ ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}

	public void increaseKillCountSafe(Player killed) {
		PkRank.checkRank(killed);
		if (killed.getSession().getIP().equals(getSession().getIP()))
			return;
		killCount++;
		getPackets().sendGameMessage(
				"<col=ff0000>You have killed " + killed.getDisplayName()
				+ ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}

	public void sendRandomJail(Player p) {
		p.resetWalkSteps();
		switch (Utils.getRandom(6)) {
		case 0:
			p.setNextWorldTile(new WorldTile(2669, 10387, 0));
			break;
		case 1:
			p.setNextWorldTile(new WorldTile(2669, 10383, 0));
			break;
		case 2:
			p.setNextWorldTile(new WorldTile(2669, 10379, 0));
			break;
		case 3:
			p.setNextWorldTile(new WorldTile(2673, 10379, 0));
			break;
		case 4:
			p.setNextWorldTile(new WorldTile(2673, 10385, 0));
			break;
		case 5:
			p.setNextWorldTile(new WorldTile(2677, 10387, 0));
			break;
		case 6:
			p.setNextWorldTile(new WorldTile(2677, 10383, 0));
			break;
		}
	}

	@Override
	public int getSize() {
		return appearance.getSize();
	}

	public boolean isCanPvp() {
		return canPvp;
	}

	public void setCanPvp(boolean canPvp) {
		this.canPvp = canPvp;
		appearance.loadAppearanceBlock();
		getPackets().sendPlayerOption(canPvp ? "Attack" : "null", 1, true);
		getPackets().sendPlayerUnderNPCPriority(canPvp);
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public long getLockDelay() {
		return lockDelay;
	}

	public boolean isLocked() {
		return lockDelay >= Utils.currentTimeMillis();
	}

	public void lock() {
		lockDelay = Long.MAX_VALUE;
	}

	public void lock(long time) {
		lockDelay = Utils.currentTimeMillis() + (time * 600);
	}

	public void unlock() {
		lockDelay = 0;
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
						  int totalDelay) {
		useStairs(emoteId, dest, useDelay, totalDelay, null);
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
						  int totalDelay, final String message) {
		stopAll();
		lock(totalDelay);
		if (emoteId != -1)
			setNextAnimation(new Animation(emoteId));
		if (useDelay == 0)
			setNextWorldTile(dest);
		else {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (isDead())
						return;
					setNextWorldTile(dest);
					if (message != null)
						getPackets().sendGameMessage(message);
				}
			}, useDelay - 1);
		}
	}

	public Bank getBank() {
		return bank;
	}

	public ControlerManager getControlerManager() {
		return controlerManager;
	}

	public void switchMouseButtons() {
		mouseButtons = !mouseButtons;
		refreshMouseButtons();
	}

	public void switchAllowChatEffects() {
		allowChatEffects = !allowChatEffects;
		refreshAllowChatEffects();
	}

	public void refreshAllowChatEffects() {
		getPackets().sendConfig(171, allowChatEffects ? 0 : 1);
	}

	public void refreshMouseButtons() {
		getPackets().sendConfig(170, mouseButtons ? 0 : 1);
	}

	public void refreshPrivateChatSetup() {
		getPackets().sendConfig(287, privateChatSetup);
	}

	public void refreshOtherChatsSetup() {
		int value = friendChatSetup << 6;
		getPackets().sendConfig(1438, value);
	}

	public void setPrivateChatSetup(int privateChatSetup) {
		this.privateChatSetup = privateChatSetup;
	}

	public void setFriendChatSetup(int friendChatSetup) {
		this.friendChatSetup = friendChatSetup;
	}

	public int getPrivateChatSetup() {
		return privateChatSetup;
	}

	public boolean isForceNextMapLoadRefresh() {
		return forceNextMapLoadRefresh;
	}

	public void setForceNextMapLoadRefresh(boolean forceNextMapLoadRefresh) {
		this.forceNextMapLoadRefresh = forceNextMapLoadRefresh;
	}

	public FriendsIgnores getFriendsIgnores() {
		return friendsIgnores;
	}

	/*
	 * do not use this, only used by pm
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void addPotDelay(long time) {
		potDelay = time + Utils.currentTimeMillis();
	}

	public long getPotDelay() {
		return potDelay;
	}

	public void addFoodDelay(long time) {
		foodDelay = time + Utils.currentTimeMillis();
	}

	public long getFoodDelay() {
		return foodDelay;
	}

	public long getBoneDelay() {
		return boneDelay;
	}

	public void addBoneDelay(long time) {
		boneDelay = time + Utils.currentTimeMillis();
	}

	public void addPoisonImmune(long time) {
		poisonImmune = time + Utils.currentTimeMillis();
		getPoison().reset();
	}

	public long getPoisonImmune() {
		return poisonImmune;
	}

	public void addFireImmune(long time) {
		fireImmune = time + Utils.currentTimeMillis();
	}

	public long getFireImmune() {
		return fireImmune;
	}

	@Override
	public void heal(int ammount, int extra) {
		super.heal(ammount, extra);
		refreshHitPoints();
	}

	public MusicsManager getMusicsManager() {
		return musicsManager;
	}

	public HintIconsManager getHintIconsManager() {
		return hintIconsManager;
	}

	public boolean isCastVeng() {
		return castedVeng;
	}

	public void setCastVeng(boolean castVeng) {
		this.castedVeng = castVeng;
	}

	public int getKillCount() {
		return killCount;
	}

	public int getBarrowsKillCount() {
		return barrowsKillCount;
	}

	public int setBarrowsKillCount(int barrowsKillCount) {
		return this.barrowsKillCount = barrowsKillCount;
	}

	public int setKillCount(int killCount) {
		return this.killCount = killCount;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public int setDeathCount(int deathCount) {
		return this.deathCount = deathCount;
	}

	public void setCloseInterfacesEvent(Runnable closeInterfacesEvent) {
		this.closeInterfacesEvent = closeInterfacesEvent;
	}

	public long getMuted() {
		return muted;
	}

	public void setMuted(long muted) {
		this.muted = muted;
	}

	public long getJailed() {
		return jailed;
	}

	public void setJailed(long jailed) {
		this.jailed = jailed;
	}

	public boolean isPermBanned() {
		return permBanned;
	}

	public void setPermBanned(boolean permBanned) {
		this.permBanned = permBanned;
	}

	public long getBanned() {
		return banned;
	}

	public void setBanned(long banned) {
		this.banned = banned;
	}

	public ChargesManager getCharges() {
		return charges;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean[] getKilledBarrowBrothers() {
		return killedBarrowBrothers;
	}

	public void setHiddenBrother(int hiddenBrother) {
		this.hiddenBrother = hiddenBrother;
	}

	public int getHiddenBrother() {
		return hiddenBrother;
	}

	public void resetBarrows() {
		hiddenBrother = -1;
		killedBarrowBrothers = new boolean[7]; //includes new bro for future use
		barrowsKillCount = 0;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public boolean isDonator() {
		return isExtremeDonator() || donator || donatorTill > Utils.currentTimeMillis();
	}

	public boolean isExtremeDonator() {
		return extremeDonator || extremeDonatorTill > Utils.currentTimeMillis();
	}

	public boolean isExtremePermDonator() {
		return extremeDonator;
	}

	public void setExtremeDonator(boolean extremeDonator) {
		this.extremeDonator = extremeDonator;
	}

	public boolean isGraphicDesigner() {
		return isGraphicDesigner;
	}

	public boolean isForumModerator() {
		return isForumModerator;
	}
	
	public void setGraphicDesigner(boolean isGraphicDesigner) {
		this.isGraphicDesigner = isGraphicDesigner;
	}
	
	public void setForumModerator(boolean isForumModerator) {
		this.isForumModerator = isForumModerator;
	}

	@SuppressWarnings("deprecation")
	public void makeDonator(int months) {
		if (donatorTill < Utils.currentTimeMillis())
			donatorTill = Utils.currentTimeMillis();
		Date date = new Date(donatorTill);
		date.setMonth(date.getMonth() + months);
		donatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public void makeDonatorDays(int days) {
		if (donatorTill < Utils.currentTimeMillis())
			donatorTill = Utils.currentTimeMillis();
		Date date = new Date(donatorTill);
		date.setDate(date.getDate()+days);
		donatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public void makeExtremeDonatorDays(int days) {
		if (extremeDonatorTill < Utils.currentTimeMillis())
			extremeDonatorTill = Utils.currentTimeMillis();
		Date date = new Date(extremeDonatorTill);
		date.setDate(date.getDate()+days);
		extremeDonatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public String getDonatorTill() {
		return (donator ? "never" : new Date(donatorTill).toGMTString()) + ".";
	}

	@SuppressWarnings("deprecation")
	public String getExtremeDonatorTill() {
		return (extremeDonator ? "never" : new Date(extremeDonatorTill).toGMTString()) + ".";
	}

	public void setDonator(boolean donator) {
		this.donator = donator;
	}

	public String getRecovQuestion() {
		return recovQuestion;
	}

	public void setRecovQuestion(String recovQuestion) {
		this.recovQuestion = recovQuestion;
	}

	public String getRecovAnswer() {
		return recovAnswer;
	}

	public void setRecovAnswer(String recovAnswer) {
		this.recovAnswer = recovAnswer;
	}

	public String getLastMsg() {
		return lastMsg;
	}

	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
	}

	public int[] getPouches() {
		return pouches;
	}

	public EmotesManager getEmotesManager() {
		return emotesManager;
	}

	public String getLastIP() {
		return lastIP;
	}

	public String getLastHostname() {
		InetAddress addr;
		try {
			addr = InetAddress.getByName(getLastIP());
			String hostname = addr.getHostName();
			return hostname;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PriceCheckManager getPriceCheckManager() {
		return priceCheckManager;
	}

	public void setPestPoints(int pestPoints) {
		this.pestPoints = pestPoints;
	}

	public int getPestPoints() {
		return pestPoints;
	}

	public boolean isUpdateMovementType() {
		return updateMovementType;
	}

	public long getLastPublicMessage() {
		return lastPublicMessage;
	}

	public void setLastPublicMessage(long lastPublicMessage) {
		this.lastPublicMessage = lastPublicMessage;
	}

	public CutscenesManager getCutscenesManager() {
		return cutscenesManager;
	}

	public void kickPlayerFromFriendsChannel(String name) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.kickPlayerFromChat(this, name);
	}

	public void sendFriendsChannelMessage(String message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendMessage(this, message);
	}

	public void sendFriendsChannelQuickMessage(QuickChatMessage message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendQuickMessage(this, message);
	}

	public void sendPublicChatMessage(PublicChatMessage message) {
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playersIndexes = World.getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player p = World.getPlayers().get(playerIndex);
				if (p == null
						|| !p.hasStarted()
						|| p.hasFinished()
						|| p.getLocalPlayerUpdate().getLocalPlayers()[getIndex()] == null)
					continue;
				p.getPackets().sendPublicMessage(this, message);
			}
		}
	}

	public int[] getCompletionistCapeCustomized() {
		return completionistCapeCustomized;
	}

	public void setCompletionistCapeCustomized(int[] skillcapeCustomized) {
		this.completionistCapeCustomized = skillcapeCustomized;
	}

	public int[] getMaxedCapeCustomized() {
		return maxedCapeCustomized;
	}

	public void setMaxedCapeCustomized(int[] maxedCapeCustomized) {
		this.maxedCapeCustomized = maxedCapeCustomized;
	}

	public void setSkullId(int skullId) {
		this.skullId = skullId;
	}

	public int getSkullId() {
		return skullId;
	}

	public boolean isFilterGame() {
		return filterGame;
	}

	public void setFilterGame(boolean filterGame) {
		this.filterGame = filterGame;
	}

	public void addLogicPacketToQueue(LogicPacket packet) {
		for (LogicPacket p : logicPackets) {
			if (p.getId() == packet.getId()) {
				logicPackets.remove(p);
				break;
			}
		}
		logicPackets.add(packet);
	}

	public DominionTower getDominionTower() {
		return dominionTower;
	}

	public void setPrayerRenewalDelay(int delay) {
		this.prayerRenewalDelay = delay;
	}

	public int getOverloadDelay() {
		return overloadDelay;
	}

	public void setOverloadDelay(int overloadDelay) {
		this.overloadDelay = overloadDelay;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTeleBlockDelay(long teleDelay) {
		getTemporaryAttributtes().put("TeleBlocked",
				teleDelay + Utils.currentTimeMillis());
	}

	public long getTeleBlockDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("TeleBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public void setPrayerDelay(long teleDelay) {
		getTemporaryAttributtes().put("PrayerBlocked",
				teleDelay + Utils.currentTimeMillis());
		prayer.closeAllPrayers();
	}

	public long getPrayerDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("PrayerBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public Familiar getFamiliar() {
		return familiar;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}

	public FriendChatsManager getCurrentFriendChat() {
		return currentFriendChat;
	}

	public void setCurrentFriendChat(FriendChatsManager currentFriendChat) {
		this.currentFriendChat = currentFriendChat;
	}

	public String getCurrentFriendChatOwner() {
		return currentFriendChatOwner;
	}

	public void setCurrentFriendChatOwner(String currentFriendChatOwner) {
		this.currentFriendChatOwner = currentFriendChatOwner;
	}

	public int getSummoningLeftClickOption() {
		return summoningLeftClickOption;
	}

	public void setSummoningLeftClickOption(int summoningLeftClickOption) {
		this.summoningLeftClickOption = summoningLeftClickOption;
	}

	public boolean canSpawn() {
		if (Wilderness.isAtWild(this)
				|| getControlerManager().getControler() instanceof FightPitsArena
				|| getControlerManager().getControler() instanceof CorpBeastControler
				|| getControlerManager().getControler() instanceof PestControlLobby
				|| getControlerManager().getControler() instanceof PestControlGame
				|| getControlerManager().getControler() instanceof ZGDControler
				|| getControlerManager().getControler() instanceof GodWars
				|| getControlerManager().getControler() instanceof DTControler
				|| getControlerManager().getControler() instanceof DuelArena
				|| getControlerManager().getControler() instanceof CastleWarsPlaying
				|| getControlerManager().getControler() instanceof CastleWarsWaiting
				|| getControlerManager().getControler() instanceof FightCaves
				|| getControlerManager().getControler() instanceof FightKiln
				|| FfaZone.inPvpArea(this)
				|| getControlerManager().getControler() instanceof NomadsRequiem
				|| getControlerManager().getControler() instanceof QueenBlackDragonController
				|| getControlerManager().getControler() instanceof WarControler) {
			return false;
		}
		if(getControlerManager().getControler() instanceof CrucibleControler) {
			CrucibleControler controler = (CrucibleControler) getControlerManager().getControler();
			return !controler.isInside();
		}
		return true;
	}

	public long getPolDelay() {
		return polDelay;
	}

	public void addPolDelay(long delay) {
		polDelay = delay + Utils.currentTimeMillis();
	}

	public void setPolDelay(long delay) {
		this.polDelay = delay;
	}

	public List<Integer> getSwitchItemCache() {
		return switchItemCache;
	}

	public AuraManager getAuraManager() {
		return auraManager;
	}

	public int getMovementType() {
		if (getTemporaryMoveType() != -1)
			return getTemporaryMoveType();
		return getRun() ? RUN_MOVE_TYPE : WALK_MOVE_TYPE;
	}

	public List<String> getOwnedObjectManagerKeys() {
		if (ownedObjectsManagerKeys == null) // temporary
			ownedObjectsManagerKeys = new LinkedList<String>();
		return ownedObjectsManagerKeys;
	}

	public boolean hasInstantSpecial(final int weaponId) {
		switch (weaponId) {
		case 4153:
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
		case 1377:
		case 13472:
		case 35:// Excalibur
		case 8280:
		case 14632:
			return true;
		default: return false;
		}
	}

	public void performInstantSpecial(final int weaponId) {
		int specAmt = PlayerCombat.getSpecialAmmount(weaponId);
		if (combatDefinitions.hasRingOfVigour())
			specAmt *= 0.9;
		if (combatDefinitions.getSpecialAttackPercentage() < specAmt) {
			getPackets().sendGameMessage("You don't have enough power left.");
			combatDefinitions.desecreaseSpecialAttack(0);
			return;
		}
		if (this.getSwitchItemCache().size() > 0) {
			ButtonHandler.submitSpecialRequest(this);
			return;
		}
		switch (weaponId) {
		case 4153:
			combatDefinitions.setInstantAttack(true);
			combatDefinitions.switchUsingSpecialAttack();
			Entity target = (Entity) getTemporaryAttributtes().get("last_target");
			if (target != null && target.getTemporaryAttributtes().get("last_attacker") == this) {
				if (!(getActionManager().getAction() instanceof PlayerCombat) || ((PlayerCombat) getActionManager().getAction()).getTarget() != target) {
					getActionManager().setAction(new PlayerCombat(target));
				}
			}
			break;
		case 1377:
		case 13472:
			setNextAnimation(new Animation(1056));
			setNextGraphics(new Graphics(246));
			setNextForceTalk(new ForceTalk("Raarrrrrgggggghhhhhhh!"));
			int defence = (int) (skills.getLevelForXp(Skills.DEFENCE) * 0.90D);
			int attack = (int) (skills.getLevelForXp(Skills.ATTACK) * 0.90D);
			int range = (int) (skills.getLevelForXp(Skills.RANGE) * 0.90D);
			int magic = (int) (skills.getLevelForXp(Skills.MAGIC) * 0.90D);
			int strength = (int) (skills.getLevelForXp(Skills.STRENGTH) * 1.2D);
			skills.set(Skills.DEFENCE, defence);
			skills.set(Skills.ATTACK, attack);
			skills.set(Skills.RANGE, range);
			skills.set(Skills.MAGIC, magic);
			skills.set(Skills.STRENGTH, strength);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 35:// Excalibur
		case 8280:
		case 14632:
			setNextAnimation(new Animation(1168));
			setNextGraphics(new Graphics(247));
			setNextForceTalk(new ForceTalk("For Matrix!"));
			final boolean enhanced = weaponId == 14632;
			skills.set(
					Skills.DEFENCE,
					enhanced ? (int) (skills.getLevelForXp(Skills.DEFENCE) * 1.15D)
							: (skills.getLevel(Skills.DEFENCE) + 8));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 5;

				@Override
				public void run() {
					if (isDead() || hasFinished()
							|| getHitpoints() >= getMaxHitpoints()) {
						stop();
						return;
					}
					heal(enhanced ? 80 : 40);
					if (count-- == 0) {
						stop();
						return;
					}
				}
			}, 4, 2);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
			setNextAnimation(new Animation(12804));
			setNextGraphics(new Graphics(2319));// 2320
			setNextGraphics(new Graphics(2321));
			addPolDelay(60000);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		}
	}

	public void setDisableEquip(boolean equip) {
		disableEquip = equip;
	}

	public boolean isEquipDisabled() {
		return disableEquip;
	}

	public void addDisplayTime(long i) {
		this.displayTime = i + Utils.currentTimeMillis();
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public int getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(int publicStatus) {
		this.publicStatus = publicStatus;
	}

	public int getClanStatus() {
		return clanStatus;
	}

	public void setClanStatus(int clanStatus) {
		this.clanStatus = clanStatus;
	}

	public int getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public int getAssistStatus() {
		return assistStatus;
	}

	public void setAssistStatus(int assistStatus) {
		this.assistStatus = assistStatus;
	}

	public boolean isSpawnsMode() {
		return spawnsMode;
	}

	public void setSpawnsMode(boolean spawnsMode) {
		this.spawnsMode = spawnsMode;
	}

	public Notes getNotes() {
		return notes;
	}

	public IsaacKeyPair getIsaacKeyPair() {
		return isaacKeyPair;
	}

	public QuestManager getQuestManager() {
		return questManager;
	}

	public boolean isCompletedFightCaves() {
		return completedFightCaves;
	}

	public void setCompletedFightCaves() {
		if(!completedFightCaves) {
			completedFightCaves = true;
			refreshFightKilnEntrance();
		}
	}

	public boolean isCompletedFightKiln() {
		return completedFightKiln;
	}

	public void setCompletedFightKiln() {
		completedFightKiln = true;
	}


	public boolean isWonFightPits() {
		return wonFightPits;
	}

	public void setWonFightPits() {
		wonFightPits = true;
	}

	public boolean isCantTrade() {
		return cantTrade;
	}

	public void setCantTrade(boolean canTrade) {
		this.cantTrade = canTrade;
	}

	public String getYellColor() {
		return yellColor;
	}

	public void setYellColor(String yellColor) {
		this.yellColor = yellColor;
	}

	/**
	 * Gets the pet.
	 * @return The pet.
	 */
	public Pet getPet() {
		return pet;
	}

	/**
	 * Sets the pet.
	 * @param pet The pet to set.
	 */
	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public boolean isSupporter() {
		return isSupporter;
	}

	public void setSupporter(boolean isSupporter) {
		this.isSupporter = isSupporter;
	}

	public Master getSlayerMaster() {
		return master;
	}

	public void setSlayerMaster(Master master) {
		this.master = master;
	}

	public SlayerTask getSlayerTask() {
		return slayerTask;
	}

	public void setSlayerTask(SlayerTask slayerTask) {
		this.slayerTask = slayerTask;
	}

	/**
	 * Gets the petManager.
	 * @return The petManager.
	 */
	public PetManager getPetManager() {
		return petManager;
	}

	/**
	 * Sets the petManager.
	 * @param petManager The petManager to set.
	 */
	public void setPetManager(PetManager petManager) {
		this.petManager = petManager;
	}

	public boolean isXpLocked() {
		return xpLocked;
	}

	public void setXpLocked(boolean locked) {
		this.xpLocked = locked;
	}

	public int getLastBonfire() {
		return lastBonfire;
	}

	public void setLastBonfire(int lastBonfire) {
		this.lastBonfire = lastBonfire;
	}

	public boolean isYellOff() {
		return yellOff;
	}

	public void setYellOff(boolean yellOff) {
		this.yellOff = yellOff;
	}

	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	public double getHpBoostMultiplier() {
		return hpBoostMultiplier;
	}

	public void setHpBoostMultiplier(double hpBoostMultiplier) {
		this.hpBoostMultiplier = hpBoostMultiplier;
	}

	/**
	 * Gets the killedQueenBlackDragon.
	 * @return The killedQueenBlackDragon.
	 */
	public boolean isKilledQueenBlackDragon() {
		return killedQueenBlackDragon;
	}

	/**
	 * Sets the killedQueenBlackDragon.
	 * @param killedQueenBlackDragon The killedQueenBlackDragon to set.
	 */
	public void setKilledQueenBlackDragon(boolean killedQueenBlackDragon) {
		this.killedQueenBlackDragon = killedQueenBlackDragon;
	}

	public boolean hasLargeSceneView() {
		return largeSceneView;
	}

	public void setLargeSceneView(boolean largeSceneView) {
		this.largeSceneView = largeSceneView;
	}

	public boolean isOldItemsLook() {
		return oldItemsLook;
	}

	public void switchItemsLook() {
		oldItemsLook = !oldItemsLook;
		getPackets().sendItemsLook();
	}

	/**
	 * @return the runeSpanPoint
	 */
	public int getRuneSpanPoints() {
		return runeSpanPoints;
	}
	public void setRuneSpanPoint(int runeSpanPoints) {
		this.runeSpanPoints = runeSpanPoints;
	}
	/**
	 * Adds points
	 * @param points
	 */
	public void addRunespanPoints(int points) {
		this.runeSpanPoints += points;
	}
	
	public DuelRules getLastDuelRules() {
		return lastDuelRules;
	}

	public void setLastDuelRules(DuelRules duelRules) {
		this.lastDuelRules = duelRules;
	}

	public boolean isTalkedWithMarv() {
		return talkedWithMarv;
	}

	public void setTalkedWithMarv() {
		talkedWithMarv = true;
	}

	public int getCrucibleHighScore() {
		return crucibleHighScore;
	}

	public void increaseCrucibleHighScore() {
		crucibleHighScore++;
	}
	
	public void setVoted(long ms) {
		voted = ms + Utils.currentTimeMillis();
	}

	public boolean hasVoted() {
		//disabled so that donators vote for the first 2 days of list reset ^^
		return isDonator() || voted > Utils.currentTimeMillis();
	}

	public int getLastLoggedIn() {
		return lastlogged;
	}

	public long getMoneyPouch() {
		return moneyPouch;
	}

	public void setMoneyPouch(long moneyPouch) {
		this.moneyPouch = moneyPouch;
	}

	public boolean getMoneyPouchShow() {
		return moneyPouchShow;
	}

	public void setMoneyPouchShowing(boolean moneyPouchShow) {
		this.moneyPouchShow = moneyPouchShow;
	}

	public boolean isShowingPouch() {
		return moneyPouchShow;
	}
	
	public GraveStone getGraveStone() {
		return stone;
	}
	
	public MoneyPouch getPouch() {
		return pouch;
	}
	
	public ShootingStar getShootingStar() {
		return ShootingStar;
	}

	public void sm(String message) {
		getPackets().sendGameMessage(message);
		
	}

	public Object getTemporaryAttribute(String string) {
		return null;
	}

	public int getFirstColumn() {
		return this.firstColumn;
	}
	
	public int getSecondColumn() {
		return this.secondColumn;
	}
	
	public int getThirdColumn() {
		return this.thirdColumn;
	}
	
	public void setFirstColumn(int i) {
		this.firstColumn = i;
	}
	
	public void setSecondColumn(int i) {
		this.secondColumn = i;
	}
	
	public void setThirdColumn(int i) {
		this.thirdColumn = i;
	}

	public void setRouteEvent(RouteEvent routeEvent) {
		this.routeEvent = routeEvent;
	}

    public RouteEvent getRouteEvent() {
        return routeEvent;
    }
}
