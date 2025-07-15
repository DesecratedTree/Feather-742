package com.feather.game.tasks;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class WorldTasksManager {

	private static final ConcurrentLinkedQueue<WorldTaskInformation> tasks = new ConcurrentLinkedQueue<>();
	private static final ConcurrentHashMap<Object, ConcurrentLinkedQueue<WorldTaskInformation>> entityBoundTasks = new ConcurrentHashMap<>();
	private static final AtomicInteger taskIdCounter = new AtomicInteger(0);

	public static void processTasks() {
		Iterator<WorldTaskInformation> iterator = tasks.iterator();

		while (iterator.hasNext()) {
			WorldTaskInformation taskInfo = iterator.next();

			// Check if task should be removed due to entity cleanup
			if (taskInfo.isMarkedForRemoval()) {
				iterator.remove();
				continue;
			}

			// Handle delay countdown
			if (taskInfo.continueCount > 0) {
				taskInfo.continueCount--;
				continue;
			}

			try {
				// Execute the task
				taskInfo.task.run();

				// Check if task should be removed after execution
				if (taskInfo.task.needRemove) {
					iterator.remove();
					removeFromEntityBinding(taskInfo);
				} else {
					// Reset counter for periodic tasks
					taskInfo.continueCount = taskInfo.continueMaxCount;
				}

			} catch (Exception e) {
				// Log error and remove problematic task
				System.err.println("Error executing task " + taskInfo.taskId + ": " + e.getMessage());
				iterator.remove();
				removeFromEntityBinding(taskInfo);
			}
		}
	}

	/**
	 * Schedule a task with entity binding for automatic cleanup
	 */
	public static int schedule(WorldTask task, Object boundEntity, int delayCount, int periodCount) {
		if (task == null || boundEntity == null || delayCount < 0 || periodCount < 0) {
			return -1;
		}

		int taskId = taskIdCounter.incrementAndGet();
		WorldTaskInformation taskInfo = new WorldTaskInformation(task, delayCount, periodCount, boundEntity, taskId);

		tasks.add(taskInfo);

		// Add to entity binding map for cleanup
		entityBoundTasks.computeIfAbsent(boundEntity, k -> new ConcurrentLinkedQueue<>()).add(taskInfo);

		return taskId;
	}

	/**
	 * Schedule a one-time task with entity binding
	 */
	public static int schedule(WorldTask task, Object boundEntity, int delayCount) {
		return schedule(task, boundEntity, delayCount, -1);
	}

	/**
	 * Schedule an immediate one-time task with entity binding
	 */
	public static int schedule(WorldTask task, Object boundEntity) {
		return schedule(task, boundEntity, 0, -1);
	}

	// Legacy methods without entity binding (not recommended for new code)
	public static int schedule(WorldTask task, int delayCount, int periodCount) {
		if (task == null || delayCount < 0 || periodCount < 0) {
			return -1;
		}

		int taskId = taskIdCounter.incrementAndGet();
		WorldTaskInformation taskInfo = new WorldTaskInformation(task, delayCount, periodCount, null, taskId);
		tasks.add(taskInfo);
		return taskId;
	}

	public static int schedule(WorldTask task, int delayCount) {
		return schedule(task, delayCount, -1);
	}

	public static int schedule(WorldTask task) {
		return schedule(task, 0, -1);
	}

	/**
	 * Cancel a specific task by ID
	 */
	public static boolean cancelTask(int taskId) {
		return tasks.removeIf(taskInfo -> taskInfo.taskId == taskId);
	}

	/**
	 * Clean up all tasks bound to a specific entity (call when player logs out, NPC is removed, etc.)
	 */
	public static void cleanupEntityTasks(Object entity) {
		if (entity == null) return;

		ConcurrentLinkedQueue<WorldTaskInformation> entityTasks = entityBoundTasks.remove(entity);
		if (entityTasks != null) {
			for (WorldTaskInformation taskInfo : entityTasks) {
				taskInfo.markForRemoval();
				try {
					taskInfo.task.stop(); // Call stop() method
				} catch (Exception e) {
					System.err.println("Error stopping task " + taskInfo.taskId + ": " + e.getMessage());
				}
			}
		}

		// Also remove from main tasks queue
		tasks.removeIf(taskInfo -> entity.equals(taskInfo.boundEntity));
	}

	/**
	 * Clean up all tasks - useful for server shutdown
	 */
	public static void cleanupAllTasks() {
		for (WorldTaskInformation taskInfo : tasks) {
			try {
				taskInfo.task.stop();
			} catch (Exception e) {
				System.err.println("Error stopping task " + taskInfo.taskId + ": " + e.getMessage());
			}
		}
		tasks.clear();
		entityBoundTasks.clear();
	}

	/**
	 * Get count of tasks bound to a specific entity
	 */
	public static int getEntityTaskCount(Object entity) {
		ConcurrentLinkedQueue<WorldTaskInformation> entityTasks = entityBoundTasks.get(entity);
		return entityTasks != null ? entityTasks.size() : 0;
	}

	public static int getTasksCount() {
		return tasks.size();
	}

	public static int getBoundEntitiesCount() {
		return entityBoundTasks.size();
	}

	private static void removeFromEntityBinding(WorldTaskInformation taskInfo) {
		if (taskInfo.boundEntity != null) {
			ConcurrentLinkedQueue<WorldTaskInformation> entityTasks = entityBoundTasks.get(taskInfo.boundEntity);
			if (entityTasks != null) {
				entityTasks.remove(taskInfo);
				if (entityTasks.isEmpty()) {
					entityBoundTasks.remove(taskInfo.boundEntity);
				}
			}
		}
	}

	private WorldTasksManager() {
		// Utility class
	}

	private static final class WorldTaskInformation {
		private final WorldTask task;
		private final int continueMaxCount;
		private int continueCount;
		private final Object boundEntity;
		private final int taskId;
		private volatile boolean markedForRemoval = false;

		public WorldTaskInformation(WorldTask task, int continueCount, int continueMaxCount, Object boundEntity, int taskId) {
			this.task = task;
			this.continueCount = continueCount;
			this.continueMaxCount = continueMaxCount;
			this.boundEntity = boundEntity;
			this.taskId = taskId;

			// One-time tasks should be removed after execution
			if (continueMaxCount == -1) {
				task.needRemove = true;
			}
		}

		public boolean isMarkedForRemoval() {
			return markedForRemoval;
		}

		public void markForRemoval() {
			this.markedForRemoval = true;
		}
	}
}