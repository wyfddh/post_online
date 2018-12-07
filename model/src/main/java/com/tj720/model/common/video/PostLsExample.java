package com.tj720.model.common.video;

import java.util.ArrayList;
import java.util.List;

public class PostLsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PostLsExample() {
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

        public Criteria andLsCodeIsNull() {
            addCriterion("ls_code is null");
            return (Criteria) this;
        }

        public Criteria andLsCodeIsNotNull() {
            addCriterion("ls_code is not null");
            return (Criteria) this;
        }

        public Criteria andLsCodeEqualTo(String value) {
            addCriterion("ls_code =", value, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeNotEqualTo(String value) {
            addCriterion("ls_code <>", value, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeGreaterThan(String value) {
            addCriterion("ls_code >", value, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ls_code >=", value, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeLessThan(String value) {
            addCriterion("ls_code <", value, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeLessThanOrEqualTo(String value) {
            addCriterion("ls_code <=", value, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeLike(String value) {
            addCriterion("ls_code like", value, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeNotLike(String value) {
            addCriterion("ls_code not like", value, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeIn(List<String> values) {
            addCriterion("ls_code in", values, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeNotIn(List<String> values) {
            addCriterion("ls_code not in", values, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeBetween(String value1, String value2) {
            addCriterion("ls_code between", value1, value2, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsCodeNotBetween(String value1, String value2) {
            addCriterion("ls_code not between", value1, value2, "lsCode");
            return (Criteria) this;
        }

        public Criteria andLsKeyIsNull() {
            addCriterion("ls_key is null");
            return (Criteria) this;
        }

        public Criteria andLsKeyIsNotNull() {
            addCriterion("ls_key is not null");
            return (Criteria) this;
        }

        public Criteria andLsKeyEqualTo(String value) {
            addCriterion("ls_key =", value, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyNotEqualTo(String value) {
            addCriterion("ls_key <>", value, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyGreaterThan(String value) {
            addCriterion("ls_key >", value, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyGreaterThanOrEqualTo(String value) {
            addCriterion("ls_key >=", value, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyLessThan(String value) {
            addCriterion("ls_key <", value, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyLessThanOrEqualTo(String value) {
            addCriterion("ls_key <=", value, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyLike(String value) {
            addCriterion("ls_key like", value, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyNotLike(String value) {
            addCriterion("ls_key not like", value, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyIn(List<String> values) {
            addCriterion("ls_key in", values, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyNotIn(List<String> values) {
            addCriterion("ls_key not in", values, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyBetween(String value1, String value2) {
            addCriterion("ls_key between", value1, value2, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsKeyNotBetween(String value1, String value2) {
            addCriterion("ls_key not between", value1, value2, "lsKey");
            return (Criteria) this;
        }

        public Criteria andLsTypeIsNull() {
            addCriterion("ls_type is null");
            return (Criteria) this;
        }

        public Criteria andLsTypeIsNotNull() {
            addCriterion("ls_type is not null");
            return (Criteria) this;
        }

        public Criteria andLsTypeEqualTo(String value) {
            addCriterion("ls_type =", value, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeNotEqualTo(String value) {
            addCriterion("ls_type <>", value, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeGreaterThan(String value) {
            addCriterion("ls_type >", value, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ls_type >=", value, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeLessThan(String value) {
            addCriterion("ls_type <", value, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeLessThanOrEqualTo(String value) {
            addCriterion("ls_type <=", value, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeLike(String value) {
            addCriterion("ls_type like", value, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeNotLike(String value) {
            addCriterion("ls_type not like", value, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeIn(List<String> values) {
            addCriterion("ls_type in", values, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeNotIn(List<String> values) {
            addCriterion("ls_type not in", values, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeBetween(String value1, String value2) {
            addCriterion("ls_type between", value1, value2, "lsType");
            return (Criteria) this;
        }

        public Criteria andLsTypeNotBetween(String value1, String value2) {
            addCriterion("ls_type not between", value1, value2, "lsType");
            return (Criteria) this;
        }

        public Criteria andExt1IsNull() {
            addCriterion("ext1 is null");
            return (Criteria) this;
        }

        public Criteria andExt1IsNotNull() {
            addCriterion("ext1 is not null");
            return (Criteria) this;
        }

        public Criteria andExt1EqualTo(String value) {
            addCriterion("ext1 =", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotEqualTo(String value) {
            addCriterion("ext1 <>", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThan(String value) {
            addCriterion("ext1 >", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThanOrEqualTo(String value) {
            addCriterion("ext1 >=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThan(String value) {
            addCriterion("ext1 <", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThanOrEqualTo(String value) {
            addCriterion("ext1 <=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Like(String value) {
            addCriterion("ext1 like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotLike(String value) {
            addCriterion("ext1 not like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1In(List<String> values) {
            addCriterion("ext1 in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotIn(List<String> values) {
            addCriterion("ext1 not in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Between(String value1, String value2) {
            addCriterion("ext1 between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotBetween(String value1, String value2) {
            addCriterion("ext1 not between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt2IsNull() {
            addCriterion("ext2 is null");
            return (Criteria) this;
        }

        public Criteria andExt2IsNotNull() {
            addCriterion("ext2 is not null");
            return (Criteria) this;
        }

        public Criteria andExt2EqualTo(String value) {
            addCriterion("ext2 =", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotEqualTo(String value) {
            addCriterion("ext2 <>", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThan(String value) {
            addCriterion("ext2 >", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThanOrEqualTo(String value) {
            addCriterion("ext2 >=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThan(String value) {
            addCriterion("ext2 <", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThanOrEqualTo(String value) {
            addCriterion("ext2 <=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Like(String value) {
            addCriterion("ext2 like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotLike(String value) {
            addCriterion("ext2 not like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2In(List<String> values) {
            addCriterion("ext2 in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotIn(List<String> values) {
            addCriterion("ext2 not in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Between(String value1, String value2) {
            addCriterion("ext2 between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotBetween(String value1, String value2) {
            addCriterion("ext2 not between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt3IsNull() {
            addCriterion("ext3 is null");
            return (Criteria) this;
        }

        public Criteria andExt3IsNotNull() {
            addCriterion("ext3 is not null");
            return (Criteria) this;
        }

        public Criteria andExt3EqualTo(String value) {
            addCriterion("ext3 =", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotEqualTo(String value) {
            addCriterion("ext3 <>", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThan(String value) {
            addCriterion("ext3 >", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThanOrEqualTo(String value) {
            addCriterion("ext3 >=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThan(String value) {
            addCriterion("ext3 <", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThanOrEqualTo(String value) {
            addCriterion("ext3 <=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Like(String value) {
            addCriterion("ext3 like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotLike(String value) {
            addCriterion("ext3 not like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3In(List<String> values) {
            addCriterion("ext3 in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotIn(List<String> values) {
            addCriterion("ext3 not in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Between(String value1, String value2) {
            addCriterion("ext3 between", value1, value2, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotBetween(String value1, String value2) {
            addCriterion("ext3 not between", value1, value2, "ext3");
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