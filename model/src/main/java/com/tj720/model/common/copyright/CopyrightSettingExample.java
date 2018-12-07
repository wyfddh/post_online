package com.tj720.model.common.copyright;

import java.util.ArrayList;
import java.util.List;

public class CopyrightSettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CopyrightSettingExample() {
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

        public Criteria andXidIsNull() {
            addCriterion("xid is null");
            return (Criteria) this;
        }

        public Criteria andXidIsNotNull() {
            addCriterion("xid is not null");
            return (Criteria) this;
        }

        public Criteria andXidEqualTo(String value) {
            addCriterion("xid =", value, "xid");
            return (Criteria) this;
        }

        public Criteria andXidNotEqualTo(String value) {
            addCriterion("xid <>", value, "xid");
            return (Criteria) this;
        }

        public Criteria andXidGreaterThan(String value) {
            addCriterion("xid >", value, "xid");
            return (Criteria) this;
        }

        public Criteria andXidGreaterThanOrEqualTo(String value) {
            addCriterion("xid >=", value, "xid");
            return (Criteria) this;
        }

        public Criteria andXidLessThan(String value) {
            addCriterion("xid <", value, "xid");
            return (Criteria) this;
        }

        public Criteria andXidLessThanOrEqualTo(String value) {
            addCriterion("xid <=", value, "xid");
            return (Criteria) this;
        }

        public Criteria andXidLike(String value) {
            addCriterion("xid like", value, "xid");
            return (Criteria) this;
        }

        public Criteria andXidNotLike(String value) {
            addCriterion("xid not like", value, "xid");
            return (Criteria) this;
        }

        public Criteria andXidIn(List<String> values) {
            addCriterion("xid in", values, "xid");
            return (Criteria) this;
        }

        public Criteria andXidNotIn(List<String> values) {
            addCriterion("xid not in", values, "xid");
            return (Criteria) this;
        }

        public Criteria andXidBetween(String value1, String value2) {
            addCriterion("xid between", value1, value2, "xid");
            return (Criteria) this;
        }

        public Criteria andXidNotBetween(String value1, String value2) {
            addCriterion("xid not between", value1, value2, "xid");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusIsNull() {
            addCriterion("watermark_status is null");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusIsNotNull() {
            addCriterion("watermark_status is not null");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusEqualTo(String value) {
            addCriterion("watermark_status =", value, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusNotEqualTo(String value) {
            addCriterion("watermark_status <>", value, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusGreaterThan(String value) {
            addCriterion("watermark_status >", value, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusGreaterThanOrEqualTo(String value) {
            addCriterion("watermark_status >=", value, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusLessThan(String value) {
            addCriterion("watermark_status <", value, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusLessThanOrEqualTo(String value) {
            addCriterion("watermark_status <=", value, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusLike(String value) {
            addCriterion("watermark_status like", value, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusNotLike(String value) {
            addCriterion("watermark_status not like", value, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusIn(List<String> values) {
            addCriterion("watermark_status in", values, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusNotIn(List<String> values) {
            addCriterion("watermark_status not in", values, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusBetween(String value1, String value2) {
            addCriterion("watermark_status between", value1, value2, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkStatusNotBetween(String value1, String value2) {
            addCriterion("watermark_status not between", value1, value2, "watermarkStatus");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathIsNull() {
            addCriterion("watermark_path is null");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathIsNotNull() {
            addCriterion("watermark_path is not null");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathEqualTo(String value) {
            addCriterion("watermark_path =", value, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathNotEqualTo(String value) {
            addCriterion("watermark_path <>", value, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathGreaterThan(String value) {
            addCriterion("watermark_path >", value, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathGreaterThanOrEqualTo(String value) {
            addCriterion("watermark_path >=", value, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathLessThan(String value) {
            addCriterion("watermark_path <", value, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathLessThanOrEqualTo(String value) {
            addCriterion("watermark_path <=", value, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathLike(String value) {
            addCriterion("watermark_path like", value, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathNotLike(String value) {
            addCriterion("watermark_path not like", value, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathIn(List<String> values) {
            addCriterion("watermark_path in", values, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathNotIn(List<String> values) {
            addCriterion("watermark_path not in", values, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathBetween(String value1, String value2) {
            addCriterion("watermark_path between", value1, value2, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andWatermarkPathNotBetween(String value1, String value2) {
            addCriterion("watermark_path not between", value1, value2, "watermarkPath");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusIsNull() {
            addCriterion("copyright_status is null");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusIsNotNull() {
            addCriterion("copyright_status is not null");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusEqualTo(String value) {
            addCriterion("copyright_status =", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusNotEqualTo(String value) {
            addCriterion("copyright_status <>", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusGreaterThan(String value) {
            addCriterion("copyright_status >", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusGreaterThanOrEqualTo(String value) {
            addCriterion("copyright_status >=", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusLessThan(String value) {
            addCriterion("copyright_status <", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusLessThanOrEqualTo(String value) {
            addCriterion("copyright_status <=", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusLike(String value) {
            addCriterion("copyright_status like", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusNotLike(String value) {
            addCriterion("copyright_status not like", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusIn(List<String> values) {
            addCriterion("copyright_status in", values, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusNotIn(List<String> values) {
            addCriterion("copyright_status not in", values, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusBetween(String value1, String value2) {
            addCriterion("copyright_status between", value1, value2, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusNotBetween(String value1, String value2) {
            addCriterion("copyright_status not between", value1, value2, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentIsNull() {
            addCriterion("copyright_content is null");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentIsNotNull() {
            addCriterion("copyright_content is not null");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentEqualTo(String value) {
            addCriterion("copyright_content =", value, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentNotEqualTo(String value) {
            addCriterion("copyright_content <>", value, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentGreaterThan(String value) {
            addCriterion("copyright_content >", value, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentGreaterThanOrEqualTo(String value) {
            addCriterion("copyright_content >=", value, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentLessThan(String value) {
            addCriterion("copyright_content <", value, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentLessThanOrEqualTo(String value) {
            addCriterion("copyright_content <=", value, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentLike(String value) {
            addCriterion("copyright_content like", value, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentNotLike(String value) {
            addCriterion("copyright_content not like", value, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentIn(List<String> values) {
            addCriterion("copyright_content in", values, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentNotIn(List<String> values) {
            addCriterion("copyright_content not in", values, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentBetween(String value1, String value2) {
            addCriterion("copyright_content between", value1, value2, "copyrightContent");
            return (Criteria) this;
        }

        public Criteria andCopyrightContentNotBetween(String value1, String value2) {
            addCriterion("copyright_content not between", value1, value2, "copyrightContent");
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