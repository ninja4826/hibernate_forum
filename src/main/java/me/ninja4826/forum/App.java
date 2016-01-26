package me.ninja4826.forum;

import me.ninja4826.forum.model.table.registry.TableRegistry;

public class App {
	
	public static void main(String[] args) {
		try {
			Bootstrap.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
