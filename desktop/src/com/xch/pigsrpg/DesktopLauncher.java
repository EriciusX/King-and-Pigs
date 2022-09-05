package com.xch.pigsrpg;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.xch.pigsrpg.core.Pigsrpg;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(Pigsrpg.TITLE);
		config.setWindowedMode(Pigsrpg.V_WIDTH,Pigsrpg.V_HEIGHT);
		config.useVsync(true);
		config.setForegroundFPS(Pigsrpg.FPS);
		new Lwjgl3Application(new Pigsrpg(), config);
	}
}
