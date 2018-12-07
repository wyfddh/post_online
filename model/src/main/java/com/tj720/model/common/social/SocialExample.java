package com.tj720.model.common.social;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SocialExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SocialExample() {
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

        public Criteria andSocialNameIsNull() {
            addCriterion("social_name is null");
            return (Criteria) this;
        }

        public Criteria andSocialNameIsNotNull() {
            addCriterion("social_name is not null");
            return (Criteria) this;
        }

        public Criteria andSocialNameEqualTo(String value) {
            addCriterion("social_name =", value, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameNotEqualTo(String value) {
            addCriterion("social_name <>", value, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameGreaterThan(String value) {
            addCriterion("social_name >", value, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameGreaterThanOrEqualTo(String value) {
            addCriterion("social_name >=", value, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameLessThan(String value) {
            addCriterion("social_name <", value, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameLessThanOrEqualTo(String value) {
            addCriterion("social_name <=", value, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameLike(String value) {
            addCriterion("social_name like", value, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameNotLike(String value) {
            addCriterion("social_name not like", value, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameIn(List<String> values) {
            addCriterion("social_name in", values, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameNotIn(List<String> values) {
            addCriterion("social_name not in", values, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameBetween(String value1, String value2) {
            addCriterion("social_name between", value1, value2, "socialName");
            return (Criteria) this;
        }

        public Criteria andSocialNameNotBetween(String value1, String value2) {
            addCriterion("social_name not between", value1, value2, "socialName");
            return (Criteria) this;
        }

        public Criteria andCooperationModeIsNull() {
            addCriterion("cooperation_mode is null");
            return (Criteria) this;
        }

        public Criteria andCooperationModeIsNotNull() {
            addCriterion("cooperation_mode is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationModeEqualTo(String value) {
            addCriterion("cooperation_mode =", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeNotEqualTo(String value) {
            addCriterion("cooperation_mode <>", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeGreaterThan(String value) {
            addCriterion("cooperation_mode >", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeGreaterThanOrEqualTo(String value) {
            addCriterion("cooperation_mode >=", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeLessThan(String value) {
            addCriterion("cooperation_mode <", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeLessThanOrEqualTo(String value) {
            addCriterion("cooperation_mode <=", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeLike(String value) {
            addCriterion("cooperation_mode like", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeNotLike(String value) {
            addCriterion("cooperation_mode not like", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeIn(List<String> values) {
            addCriterion("cooperation_mode in", values, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeNotIn(List<String> values) {
            addCriterion("cooperation_mode not in", values, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeBetween(String value1, String value2) {
            addCriterion("cooperation_mode between", value1, value2, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeNotBetween(String value1, String value2) {
            addCriterion("cooperation_mode not between", value1, value2, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitIsNull() {
            addCriterion("cooperation_unit is null");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitIsNotNull() {
            addCriterion("cooperation_unit is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitEqualTo(String value) {
            addCriterion("cooperation_unit =", value, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitNotEqualTo(String value) {
            addCriterion("cooperation_unit <>", value, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitGreaterThan(String value) {
            addCriterion("cooperation_unit >", value, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitGreaterThanOrEqualTo(String value) {
            addCriterion("cooperation_unit >=", value, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitLessThan(String value) {
            addCriterion("cooperation_unit <", value, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitLessThanOrEqualTo(String value) {
            addCriterion("cooperation_unit <=", value, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitLike(String value) {
            addCriterion("cooperation_unit like", value, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitNotLike(String value) {
            addCriterion("cooperation_unit not like", value, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitIn(List<String> values) {
            addCriterion("cooperation_unit in", values, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitNotIn(List<String> values) {
            addCriterion("cooperation_unit not in", values, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitBetween(String value1, String value2) {
            addCriterion("cooperation_unit between", value1, value2, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andCooperationUnitNotBetween(String value1, String value2) {
            addCriterion("cooperation_unit not between", value1, value2, "cooperationUnit");
            return (Criteria) this;
        }

        public Criteria andHoldTimeIsNull() {
            addCriterion("hold_time is null");
            return (Criteria) this;
        }

        public Criteria andHoldTimeIsNotNull() {
            addCriterion("hold_time is not null");
            return (Criteria) this;
        }

        public Criteria andHoldTimeEqualTo(Date value) {
            addCriterion("hold_time =", value, "holdTime");
            return (Criteria) this;
        }

        public Criteria andHoldTimeNotEqualTo(Date value) {
            addCriterion("hold_time <>", value, "holdTime");
            return (Criteria) this;
        }

        public Criteria andHoldTimeGreaterThan(Date value) {
            addCriterion("hold_time >", value, "holdTime");
            return (Criteria) this;
        }

        public Criteria andHoldTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("hold_time >=", value, "holdTime");
            return (Criteria) this;
        }

        public Criteria andHoldTimeLessThan(Date value) {
            addCriterion("hold_time <", value, "holdTime");
            return (Criteria) this;
        }

        public Criteria andHoldTimeLessThanOrEqualTo(Date value) {
            addCriterion("hold_time <=", value, "holdTime");
            return (Criteria) this;
        }

        public Criteria andHoldTimeIn(List<Date> values) {
            addCriterion("hold_time in", values, "holdTime");
            return (Criteria) this;
        }

        public Criteria andHoldTimeNotIn(List<Date> values) {
            addCriterion("hold_time not in", values, "holdTime");
            return (Criteria) this;
        }

        public Criteria andHoldTimeBetween(Date value1, Date value2) {
            addCriterion("hold_time between", value1, value2, "holdTime");
            return (Criteria) this;
        }

        public Criteria andHoldTimeNotBetween(Date value1, Date value2) {
            addCriterion("hold_time not between", value1, value2, "holdTime");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsIsNull() {
            addCriterion("adoption_patterns is null");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsIsNotNull() {
            addCriterion("adoption_patterns is not null");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsEqualTo(String value) {
            addCriterion("adoption_patterns =", value, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsNotEqualTo(String value) {
            addCriterion("adoption_patterns <>", value, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsGreaterThan(String value) {
            addCriterion("adoption_patterns >", value, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsGreaterThanOrEqualTo(String value) {
            addCriterion("adoption_patterns >=", value, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsLessThan(String value) {
            addCriterion("adoption_patterns <", value, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsLessThanOrEqualTo(String value) {
            addCriterion("adoption_patterns <=", value, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsLike(String value) {
            addCriterion("adoption_patterns like", value, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsNotLike(String value) {
            addCriterion("adoption_patterns not like", value, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsIn(List<String> values) {
            addCriterion("adoption_patterns in", values, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsNotIn(List<String> values) {
            addCriterion("adoption_patterns not in", values, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsBetween(String value1, String value2) {
            addCriterion("adoption_patterns between", value1, value2, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andAdoptionPatternsNotBetween(String value1, String value2) {
            addCriterion("adoption_patterns not between", value1, value2, "adoptionPatterns");
            return (Criteria) this;
        }

        public Criteria andKeyWordIsNull() {
            addCriterion("key_word is null");
            return (Criteria) this;
        }

        public Criteria andKeyWordIsNotNull() {
            addCriterion("key_word is not null");
            return (Criteria) this;
        }

        public Criteria andKeyWordEqualTo(String value) {
            addCriterion("key_word =", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordNotEqualTo(String value) {
            addCriterion("key_word <>", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordGreaterThan(String value) {
            addCriterion("key_word >", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordGreaterThanOrEqualTo(String value) {
            addCriterion("key_word >=", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordLessThan(String value) {
            addCriterion("key_word <", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordLessThanOrEqualTo(String value) {
            addCriterion("key_word <=", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordLike(String value) {
            addCriterion("key_word like", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordNotLike(String value) {
            addCriterion("key_word not like", value, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordIn(List<String> values) {
            addCriterion("key_word in", values, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordNotIn(List<String> values) {
            addCriterion("key_word not in", values, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordBetween(String value1, String value2) {
            addCriterion("key_word between", value1, value2, "keyWord");
            return (Criteria) this;
        }

        public Criteria andKeyWordNotBetween(String value1, String value2) {
            addCriterion("key_word not between", value1, value2, "keyWord");
            return (Criteria) this;
        }

        public Criteria andHeadIsNull() {
            addCriterion("head is null");
            return (Criteria) this;
        }

        public Criteria andHeadIsNotNull() {
            addCriterion("head is not null");
            return (Criteria) this;
        }

        public Criteria andHeadEqualTo(String value) {
            addCriterion("head =", value, "head");
            return (Criteria) this;
        }

        public Criteria andHeadNotEqualTo(String value) {
            addCriterion("head <>", value, "head");
            return (Criteria) this;
        }

        public Criteria andHeadGreaterThan(String value) {
            addCriterion("head >", value, "head");
            return (Criteria) this;
        }

        public Criteria andHeadGreaterThanOrEqualTo(String value) {
            addCriterion("head >=", value, "head");
            return (Criteria) this;
        }

        public Criteria andHeadLessThan(String value) {
            addCriterion("head <", value, "head");
            return (Criteria) this;
        }

        public Criteria andHeadLessThanOrEqualTo(String value) {
            addCriterion("head <=", value, "head");
            return (Criteria) this;
        }

        public Criteria andHeadLike(String value) {
            addCriterion("head like", value, "head");
            return (Criteria) this;
        }

        public Criteria andHeadNotLike(String value) {
            addCriterion("head not like", value, "head");
            return (Criteria) this;
        }

        public Criteria andHeadIn(List<String> values) {
            addCriterion("head in", values, "head");
            return (Criteria) this;
        }

        public Criteria andHeadNotIn(List<String> values) {
            addCriterion("head not in", values, "head");
            return (Criteria) this;
        }

        public Criteria andHeadBetween(String value1, String value2) {
            addCriterion("head between", value1, value2, "head");
            return (Criteria) this;
        }

        public Criteria andHeadNotBetween(String value1, String value2) {
            addCriterion("head not between", value1, value2, "head");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionIsNull() {
            addCriterion("social_instruction is null");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionIsNotNull() {
            addCriterion("social_instruction is not null");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionEqualTo(String value) {
            addCriterion("social_instruction =", value, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionNotEqualTo(String value) {
            addCriterion("social_instruction <>", value, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionGreaterThan(String value) {
            addCriterion("social_instruction >", value, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionGreaterThanOrEqualTo(String value) {
            addCriterion("social_instruction >=", value, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionLessThan(String value) {
            addCriterion("social_instruction <", value, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionLessThanOrEqualTo(String value) {
            addCriterion("social_instruction <=", value, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionLike(String value) {
            addCriterion("social_instruction like", value, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionNotLike(String value) {
            addCriterion("social_instruction not like", value, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionIn(List<String> values) {
            addCriterion("social_instruction in", values, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionNotIn(List<String> values) {
            addCriterion("social_instruction not in", values, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionBetween(String value1, String value2) {
            addCriterion("social_instruction between", value1, value2, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialInstructionNotBetween(String value1, String value2) {
            addCriterion("social_instruction not between", value1, value2, "socialInstruction");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeIsNull() {
            addCriterion("social_purpose is null");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeIsNotNull() {
            addCriterion("social_purpose is not null");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeEqualTo(String value) {
            addCriterion("social_purpose =", value, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeNotEqualTo(String value) {
            addCriterion("social_purpose <>", value, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeGreaterThan(String value) {
            addCriterion("social_purpose >", value, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeGreaterThanOrEqualTo(String value) {
            addCriterion("social_purpose >=", value, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeLessThan(String value) {
            addCriterion("social_purpose <", value, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeLessThanOrEqualTo(String value) {
            addCriterion("social_purpose <=", value, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeLike(String value) {
            addCriterion("social_purpose like", value, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeNotLike(String value) {
            addCriterion("social_purpose not like", value, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeIn(List<String> values) {
            addCriterion("social_purpose in", values, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeNotIn(List<String> values) {
            addCriterion("social_purpose not in", values, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeBetween(String value1, String value2) {
            addCriterion("social_purpose between", value1, value2, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andSocialPurposeNotBetween(String value1, String value2) {
            addCriterion("social_purpose not between", value1, value2, "socialPurpose");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNull() {
            addCriterion("updater is null");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNotNull() {
            addCriterion("updater is not null");
            return (Criteria) this;
        }

        public Criteria andUpdaterEqualTo(String value) {
            addCriterion("updater =", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotEqualTo(String value) {
            addCriterion("updater <>", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThan(String value) {
            addCriterion("updater >", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThanOrEqualTo(String value) {
            addCriterion("updater >=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThan(String value) {
            addCriterion("updater <", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThanOrEqualTo(String value) {
            addCriterion("updater <=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLike(String value) {
            addCriterion("updater like", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotLike(String value) {
            addCriterion("updater not like", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterIn(List<String> values) {
            addCriterion("updater in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotIn(List<String> values) {
            addCriterion("updater not in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterBetween(String value1, String value2) {
            addCriterion("updater between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotBetween(String value1, String value2) {
            addCriterion("updater not between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
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

        public Criteria andIsdeleteIsNull() {
            addCriterion("isdelete is null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIsNotNull() {
            addCriterion("isdelete is not null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteEqualTo(Integer value) {
            addCriterion("isdelete =", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotEqualTo(Integer value) {
            addCriterion("isdelete <>", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThan(Integer value) {
            addCriterion("isdelete >", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThanOrEqualTo(Integer value) {
            addCriterion("isdelete >=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThan(Integer value) {
            addCriterion("isdelete <", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThanOrEqualTo(Integer value) {
            addCriterion("isdelete <=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIn(List<Integer> values) {
            addCriterion("isdelete in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotIn(List<Integer> values) {
            addCriterion("isdelete not in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteBetween(Integer value1, Integer value2) {
            addCriterion("isdelete between", value1, value2, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotBetween(Integer value1, Integer value2) {
            addCriterion("isdelete not between", value1, value2, "isdelete");
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