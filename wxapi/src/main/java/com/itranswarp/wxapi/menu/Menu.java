package com.itranswarp.wxapi.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	public List<AbstractButton> button = new ArrayList<AbstractButton>(5);

	public Rule matchrule;

	public void addButton(AbstractButton button) {
		if (this.button.size() >= 3) {
			throw new IllegalArgumentException("Cannot add more buttons.");
		}
		this.button.add(button);
	}

}
