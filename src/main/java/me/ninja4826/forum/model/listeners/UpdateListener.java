package me.ninja4826.forum.model.listeners;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

import me.ninja4826.forum.model.entity.ItemInterface;

public class UpdateListener {
	
	public class PreUpdateListener implements PreUpdateEventListener {
		@Override
		public boolean onPreUpdate(PreUpdateEvent event) {
			return ((ItemInterface<?>)event.getEntity()).beforeInsert();
		}
	}
	
	public class PostUpdateListener implements PostUpdateEventListener {
		@Override
		public void onPostUpdate(PostUpdateEvent event) {
			((ItemInterface<?>)event.getEntity()).afterInsert();
		}

		@Override
		public boolean requiresPostCommitHanding(EntityPersister persister) {
			return false;
		}
	}
}
