package com.itranswarp.wxapi.menu;

import java.util.ArrayList;
import java.util.List;

public class SubMenu extends AbstractButton {

	public List<AbstractButton> sub_button = new ArrayList<AbstractButton>(5);

	public SubMenu() {
	}

	public SubMenu(String name, AbstractButton... buttons) {
		this.name = name;
		for (AbstractButton button : buttons) {
			addButton(button);
		}
	}

	public void addButton(AbstractButton button) {
		if (this.sub_button.size() >= 5) {
			throw new IllegalArgumentException("Cannot add more buttons.");
		}
		this.sub_button.add(button);
	}
}
