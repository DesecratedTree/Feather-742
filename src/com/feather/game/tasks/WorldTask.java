package com.feather.game.tasks;

public abstract class WorldTask {
	public volatile boolean needRemove = false;

	/**
	 * Execute the task
	 */
	public abstract void run();

	/**
	 * Called when the task is being cleaned up or cancelled
	 * Override this to release resources, clear references, etc.
	 */
	public void stop() {
		// Default implementation - override if needed
	}

	/**
	 * Mark this task for removal after the next execution
	 */
	public final void cancel() {
		this.needRemove = true;
	}
}
