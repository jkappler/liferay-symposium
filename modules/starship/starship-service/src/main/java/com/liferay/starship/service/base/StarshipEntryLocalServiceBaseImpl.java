/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.starship.service.base;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.starship.model.StarshipEntry;
import com.liferay.starship.service.StarshipEntryLocalService;
import com.liferay.starship.service.persistence.StarshipEntryPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the starship entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.starship.service.impl.StarshipEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.starship.service.impl.StarshipEntryLocalServiceImpl
 * @generated
 */
public abstract class StarshipEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, IdentifiableOSGiService, StarshipEntryLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>StarshipEntryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.starship.service.StarshipEntryLocalServiceUtil</code>.
	 */

	/**
	 * Adds the starship entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StarshipEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param starshipEntry the starship entry
	 * @return the starship entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public StarshipEntry addStarshipEntry(StarshipEntry starshipEntry) {
		starshipEntry.setNew(true);

		return starshipEntryPersistence.update(starshipEntry);
	}

	/**
	 * Creates a new starship entry with the primary key. Does not add the starship entry to the database.
	 *
	 * @param starshipEntryId the primary key for the new starship entry
	 * @return the new starship entry
	 */
	@Override
	@Transactional(enabled = false)
	public StarshipEntry createStarshipEntry(long starshipEntryId) {
		return starshipEntryPersistence.create(starshipEntryId);
	}

	/**
	 * Deletes the starship entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StarshipEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param starshipEntryId the primary key of the starship entry
	 * @return the starship entry that was removed
	 * @throws PortalException if a starship entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public StarshipEntry deleteStarshipEntry(long starshipEntryId)
		throws PortalException {

		return starshipEntryPersistence.remove(starshipEntryId);
	}

	/**
	 * Deletes the starship entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StarshipEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param starshipEntry the starship entry
	 * @return the starship entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public StarshipEntry deleteStarshipEntry(StarshipEntry starshipEntry) {
		return starshipEntryPersistence.remove(starshipEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			StarshipEntry.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return starshipEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.starship.model.impl.StarshipEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return starshipEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.starship.model.impl.StarshipEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return starshipEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return starshipEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return starshipEntryPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public StarshipEntry fetchStarshipEntry(long starshipEntryId) {
		return starshipEntryPersistence.fetchByPrimaryKey(starshipEntryId);
	}

	/**
	 * Returns the starship entry matching the UUID and group.
	 *
	 * @param uuid the starship entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching starship entry, or <code>null</code> if a matching starship entry could not be found
	 */
	@Override
	public StarshipEntry fetchStarshipEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return starshipEntryPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the starship entry with the primary key.
	 *
	 * @param starshipEntryId the primary key of the starship entry
	 * @return the starship entry
	 * @throws PortalException if a starship entry with the primary key could not be found
	 */
	@Override
	public StarshipEntry getStarshipEntry(long starshipEntryId)
		throws PortalException {

		return starshipEntryPersistence.findByPrimaryKey(starshipEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(starshipEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(StarshipEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("starshipEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			starshipEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(StarshipEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"starshipEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(starshipEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(StarshipEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("starshipEntryId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<StarshipEntry>() {

				@Override
				public void performAction(StarshipEntry starshipEntry)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, starshipEntry);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(StarshipEntry.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return starshipEntryPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return starshipEntryLocalService.deleteStarshipEntry(
			(StarshipEntry)persistedModel);
	}

	public BasePersistence<StarshipEntry> getBasePersistence() {
		return starshipEntryPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return starshipEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the starship entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the starship entries
	 * @param companyId the primary key of the company
	 * @return the matching starship entries, or an empty list if no matches were found
	 */
	@Override
	public List<StarshipEntry> getStarshipEntriesByUuidAndCompanyId(
		String uuid, long companyId) {

		return starshipEntryPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of starship entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the starship entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of starship entries
	 * @param end the upper bound of the range of starship entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching starship entries, or an empty list if no matches were found
	 */
	@Override
	public List<StarshipEntry> getStarshipEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<StarshipEntry> orderByComparator) {

		return starshipEntryPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the starship entry matching the UUID and group.
	 *
	 * @param uuid the starship entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching starship entry
	 * @throws PortalException if a matching starship entry could not be found
	 */
	@Override
	public StarshipEntry getStarshipEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return starshipEntryPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the starship entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.starship.model.impl.StarshipEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of starship entries
	 * @param end the upper bound of the range of starship entries (not inclusive)
	 * @return the range of starship entries
	 */
	@Override
	public List<StarshipEntry> getStarshipEntries(int start, int end) {
		return starshipEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of starship entries.
	 *
	 * @return the number of starship entries
	 */
	@Override
	public int getStarshipEntriesCount() {
		return starshipEntryPersistence.countAll();
	}

	/**
	 * Updates the starship entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StarshipEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param starshipEntry the starship entry
	 * @return the starship entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public StarshipEntry updateStarshipEntry(StarshipEntry starshipEntry) {
		return starshipEntryPersistence.update(starshipEntry);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			StarshipEntryLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		starshipEntryLocalService = (StarshipEntryLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return StarshipEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return StarshipEntry.class;
	}

	protected String getModelClassName() {
		return StarshipEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = starshipEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	protected StarshipEntryLocalService starshipEntryLocalService;

	@Reference
	protected StarshipEntryPersistence starshipEntryPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.CompanyLocalService
		companyLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.GroupLocalService
		groupLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ImageLocalService
		imageLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.SystemEventLocalService
		systemEventLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

}