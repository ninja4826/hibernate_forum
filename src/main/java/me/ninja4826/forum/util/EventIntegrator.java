package me.ninja4826.forum.util;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import me.ninja4826.forum.model.listeners.InsertListener;
import me.ninja4826.forum.model.listeners.UpdateListener;

public class EventIntegrator implements Integrator {

	@SuppressWarnings("unchecked")
	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

		eventListenerRegistry.prependListeners(EventType.PRE_INSERT, InsertListener.PreInsertListener.class);
		eventListenerRegistry.appendListeners(EventType.POST_INSERT, InsertListener.PostInsertListener.class);

		eventListenerRegistry.prependListeners(EventType.PRE_UPDATE, UpdateListener.PreUpdateListener.class);
		eventListenerRegistry.appendListeners(EventType.POST_UPDATE, UpdateListener.PostUpdateListener.class);
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
		
	}

}
