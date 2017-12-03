package com.luoruiyong.myview;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JLabel;

public class MenuManager extends MenuBar {
	public MenuManager() {
		add(new Menu("File"));
		add(new Menu("Edit"));
		add(new Menu("Help"));
	}
}
