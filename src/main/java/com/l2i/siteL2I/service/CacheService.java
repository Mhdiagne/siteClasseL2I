// package com.l2i.siteL2I.service;

// import org.hibernate.SessionFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import jakarta.persistence.EntityManager;

// /**
//  * CacheService
//  */
// @Service
// public class CacheService {
//     @Autowired
//     private EntityManager entityManager;

//     public void clearHibernateCache() {
//         SessionFactory sessionFactory = entityManager.unwrap(SessionFactory.class);
//         EhcacheRegionFactory regionFactory = (EhcacheRegionFactory) sessionFactory.getSessionFactoryOptions()
//                 .getCacheRegionFactory();
//         regionFactory.clearAll();
//     }
// }