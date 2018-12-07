package com.tj720.model.common.themeshow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostThemeShowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PostThemeShowExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDatumIdsIsNull() {
            addCriterion("datum_ids is null");
            return (Criteria) this;
        }

        public Criteria andDatumIdsIsNotNull() {
            addCriterion("datum_ids is not null");
            return (Criteria) this;
        }

        public Criteria andDatumIdsEqualTo(String value) {
            addCriterion("datum_ids =", value, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsNotEqualTo(String value) {
            addCriterion("datum_ids <>", value, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsGreaterThan(String value) {
            addCriterion("datum_ids >", value, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsGreaterThanOrEqualTo(String value) {
            addCriterion("datum_ids >=", value, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsLessThan(String value) {
            addCriterion("datum_ids <", value, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsLessThanOrEqualTo(String value) {
            addCriterion("datum_ids <=", value, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsLike(String value) {
            addCriterion("datum_ids like", value, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsNotLike(String value) {
            addCriterion("datum_ids not like", value, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsIn(List<String> values) {
            addCriterion("datum_ids in", values, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsNotIn(List<String> values) {
            addCriterion("datum_ids not in", values, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsBetween(String value1, String value2) {
            addCriterion("datum_ids between", value1, value2, "datumIds");
            return (Criteria) this;
        }

        public Criteria andDatumIdsNotBetween(String value1, String value2) {
            addCriterion("datum_ids not between", value1, value2, "datumIds");
            return (Criteria) this;
        }

        public Criteria andThemeNameIsNull() {
            addCriterion("theme_name is null");
            return (Criteria) this;
        }

        public Criteria andThemeNameIsNotNull() {
            addCriterion("theme_name is not null");
            return (Criteria) this;
        }

        public Criteria andThemeNameEqualTo(String value) {
            addCriterion("theme_name =", value, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameNotEqualTo(String value) {
            addCriterion("theme_name <>", value, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameGreaterThan(String value) {
            addCriterion("theme_name >", value, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameGreaterThanOrEqualTo(String value) {
            addCriterion("theme_name >=", value, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameLessThan(String value) {
            addCriterion("theme_name <", value, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameLessThanOrEqualTo(String value) {
            addCriterion("theme_name <=", value, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameLike(String value) {
            addCriterion("theme_name like", value, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameNotLike(String value) {
            addCriterion("theme_name not like", value, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameIn(List<String> values) {
            addCriterion("theme_name in", values, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameNotIn(List<String> values) {
            addCriterion("theme_name not in", values, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameBetween(String value1, String value2) {
            addCriterion("theme_name between", value1, value2, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeNameNotBetween(String value1, String value2) {
            addCriterion("theme_name not between", value1, value2, "themeName");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeIsNull() {
            addCriterion("theme_describe is null");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeIsNotNull() {
            addCriterion("theme_describe is not null");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeEqualTo(String value) {
            addCriterion("theme_describe =", value, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeNotEqualTo(String value) {
            addCriterion("theme_describe <>", value, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeGreaterThan(String value) {
            addCriterion("theme_describe >", value, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("theme_describe >=", value, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeLessThan(String value) {
            addCriterion("theme_describe <", value, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeLessThanOrEqualTo(String value) {
            addCriterion("theme_describe <=", value, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeLike(String value) {
            addCriterion("theme_describe like", value, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeNotLike(String value) {
            addCriterion("theme_describe not like", value, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeIn(List<String> values) {
            addCriterion("theme_describe in", values, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeNotIn(List<String> values) {
            addCriterion("theme_describe not in", values, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeBetween(String value1, String value2) {
            addCriterion("theme_describe between", value1, value2, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeDescribeNotBetween(String value1, String value2) {
            addCriterion("theme_describe not between", value1, value2, "themeDescribe");
            return (Criteria) this;
        }

        public Criteria andThemeSourceIsNull() {
            addCriterion("theme_source is null");
            return (Criteria) this;
        }

        public Criteria andThemeSourceIsNotNull() {
            addCriterion("theme_source is not null");
            return (Criteria) this;
        }

        public Criteria andThemeSourceEqualTo(String value) {
            addCriterion("theme_source =", value, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceNotEqualTo(String value) {
            addCriterion("theme_source <>", value, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceGreaterThan(String value) {
            addCriterion("theme_source >", value, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceGreaterThanOrEqualTo(String value) {
            addCriterion("theme_source >=", value, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceLessThan(String value) {
            addCriterion("theme_source <", value, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceLessThanOrEqualTo(String value) {
            addCriterion("theme_source <=", value, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceLike(String value) {
            addCriterion("theme_source like", value, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceNotLike(String value) {
            addCriterion("theme_source not like", value, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceIn(List<String> values) {
            addCriterion("theme_source in", values, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceNotIn(List<String> values) {
            addCriterion("theme_source not in", values, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceBetween(String value1, String value2) {
            addCriterion("theme_source between", value1, value2, "themeSource");
            return (Criteria) this;
        }

        public Criteria andThemeSourceNotBetween(String value1, String value2) {
            addCriterion("theme_source not between", value1, value2, "themeSource");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountIsNull() {
            addCriterion("collection_amount is null");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountIsNotNull() {
            addCriterion("collection_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountEqualTo(String value) {
            addCriterion("collection_amount =", value, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountNotEqualTo(String value) {
            addCriterion("collection_amount <>", value, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountGreaterThan(String value) {
            addCriterion("collection_amount >", value, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountGreaterThanOrEqualTo(String value) {
            addCriterion("collection_amount >=", value, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountLessThan(String value) {
            addCriterion("collection_amount <", value, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountLessThanOrEqualTo(String value) {
            addCriterion("collection_amount <=", value, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountLike(String value) {
            addCriterion("collection_amount like", value, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountNotLike(String value) {
            addCriterion("collection_amount not like", value, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountIn(List<String> values) {
            addCriterion("collection_amount in", values, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountNotIn(List<String> values) {
            addCriterion("collection_amount not in", values, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountBetween(String value1, String value2) {
            addCriterion("collection_amount between", value1, value2, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andCollectionAmountNotBetween(String value1, String value2) {
            addCriterion("collection_amount not between", value1, value2, "collectionAmount");
            return (Criteria) this;
        }

        public Criteria andPageRecommendIsNull() {
            addCriterion("page_recommend is null");
            return (Criteria) this;
        }

        public Criteria andPageRecommendIsNotNull() {
            addCriterion("page_recommend is not null");
            return (Criteria) this;
        }

        public Criteria andPageRecommendEqualTo(String value) {
            addCriterion("page_recommend =", value, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendNotEqualTo(String value) {
            addCriterion("page_recommend <>", value, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendGreaterThan(String value) {
            addCriterion("page_recommend >", value, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendGreaterThanOrEqualTo(String value) {
            addCriterion("page_recommend >=", value, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendLessThan(String value) {
            addCriterion("page_recommend <", value, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendLessThanOrEqualTo(String value) {
            addCriterion("page_recommend <=", value, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendLike(String value) {
            addCriterion("page_recommend like", value, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendNotLike(String value) {
            addCriterion("page_recommend not like", value, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendIn(List<String> values) {
            addCriterion("page_recommend in", values, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendNotIn(List<String> values) {
            addCriterion("page_recommend not in", values, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendBetween(String value1, String value2) {
            addCriterion("page_recommend between", value1, value2, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andPageRecommendNotBetween(String value1, String value2) {
            addCriterion("page_recommend not between", value1, value2, "pageRecommend");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsIsNull() {
            addCriterion("selected_topics is null");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsIsNotNull() {
            addCriterion("selected_topics is not null");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsEqualTo(String value) {
            addCriterion("selected_topics =", value, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsNotEqualTo(String value) {
            addCriterion("selected_topics <>", value, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsGreaterThan(String value) {
            addCriterion("selected_topics >", value, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsGreaterThanOrEqualTo(String value) {
            addCriterion("selected_topics >=", value, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsLessThan(String value) {
            addCriterion("selected_topics <", value, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsLessThanOrEqualTo(String value) {
            addCriterion("selected_topics <=", value, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsLike(String value) {
            addCriterion("selected_topics like", value, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsNotLike(String value) {
            addCriterion("selected_topics not like", value, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsIn(List<String> values) {
            addCriterion("selected_topics in", values, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsNotIn(List<String> values) {
            addCriterion("selected_topics not in", values, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsBetween(String value1, String value2) {
            addCriterion("selected_topics between", value1, value2, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andSelectedTopicsNotBetween(String value1, String value2) {
            addCriterion("selected_topics not between", value1, value2, "selectedTopics");
            return (Criteria) this;
        }

        public Criteria andDataStateIsNull() {
            addCriterion("data_state is null");
            return (Criteria) this;
        }

        public Criteria andDataStateIsNotNull() {
            addCriterion("data_state is not null");
            return (Criteria) this;
        }

        public Criteria andDataStateEqualTo(String value) {
            addCriterion("data_state =", value, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateNotEqualTo(String value) {
            addCriterion("data_state <>", value, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateGreaterThan(String value) {
            addCriterion("data_state >", value, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateGreaterThanOrEqualTo(String value) {
            addCriterion("data_state >=", value, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateLessThan(String value) {
            addCriterion("data_state <", value, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateLessThanOrEqualTo(String value) {
            addCriterion("data_state <=", value, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateLike(String value) {
            addCriterion("data_state like", value, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateNotLike(String value) {
            addCriterion("data_state not like", value, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateIn(List<String> values) {
            addCriterion("data_state in", values, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateNotIn(List<String> values) {
            addCriterion("data_state not in", values, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateBetween(String value1, String value2) {
            addCriterion("data_state between", value1, value2, "dataState");
            return (Criteria) this;
        }

        public Criteria andDataStateNotBetween(String value1, String value2) {
            addCriterion("data_state not between", value1, value2, "dataState");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}