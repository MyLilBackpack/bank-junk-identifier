package com.bankjunk;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

/**
 * Local dev launcher used by the `run` Gradle task. It boots a RuneLite client
 * with this plugin side-loaded. It is NOT the plugin entry point the Plugin Hub
 * uses — that is BankJunkPlugin (referenced by runelite-plugin.properties).
 *
 * Goes in src/test/java/com/bankjunk/ (test scope), alongside the other tests.
 */
public class BankJunkPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BankJunkPlugin.class);
		RuneLite.main(args);
	}
}
