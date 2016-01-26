package me.ninja4826.forum.model.listeners;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

import me.ninja4826.forum.model.entity.ItemInterface;

public class InsertListener {

	public class PreInsertListener implements PreInsertEventListener {
		@Override
		public boolean onPreInsert(PreInsertEvent event) {
			return ((ItemInterface<?>)event.getEntity()).beforeInsert();
		}
	}
	
	public class PostInsertListener implements PostInsertEventListener {
		@Override
		public void onPostInsert(PostInsertEvent event) {
			((ItemInterface<?>)event.getEntity()).afterInsert();
		}

		@Override
		public boolean requiresPostCommitHanding(EntityPersister persister) {
			return false;
		}
	}
}
