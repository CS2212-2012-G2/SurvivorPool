package client;

/* Author: CS2212 Group 2
 * File Name: BlackBerryApp.java
 * Date: 25/01/2012
 * Project: UWOSurvivorPool
 * Course: CS2212b
 * Description: The gateway class which create the first instance of the Blackberry application.
 * */

import net.rim.device.api.ui.*;

public class BlackBerryApp extends UiApplication {

	public BlackBerryApp () {
		pushScreen(new SplashScreen()); // loads first class screen
	}

	public static void main(String[] args) {
		BlackBerryApp  BBApp = new BlackBerryApp (); // creates new instance
		BBApp.enterEventDispatcher(); // launches the event dispatcher
	}
}
