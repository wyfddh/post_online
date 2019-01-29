package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostLiteratureMapper;
import com.tj720.model.literature.PostLiterature;
import com.tj720.service.PostLiteratureSerialNumberService;
import com.tj720.utils.ParseNumUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostLiteratureSerialNumberServiceImpl implements PostLiteratureSerialNumberService{

    @Autowired
    private PostLiteratureMapper postLiteratureMapper;

    @Override
    public JsonResult getSerialNumber(String dataName, String selectID) {
        return new JsonResult(1, this.buildSerialNumber(dataName, selectID));
    }

    /**
     * 获取索书号
     * @param dataName
     * @param baseType
     * @return
     */
    private Map<String, String> buildSerialNumber(String dataName, String baseType) {
        // 判断书籍是否已存在
        List<PostLiterature> postLiterature = postLiteratureMapper.getPostLiteratureByName(dataName);
        if (CollectionUtils.isNotEmpty(postLiterature)) {
            PostLiterature literature = postLiterature.get(postLiterature.size() - 1);
            if (StringUtils.isNotEmpty(literature.getCallNo()) && literature.getCallNo().indexOf("/") > -1) {
                Map<String, String> resMap = new HashMap<String, String>(2);
                resMap.put("searchValue", postLiterature.get(0).getSearchValue());
                resMap.put("callNo", baseType + "/" + literature.getCallNo().split("/")[1]);
                return resMap;
            }
        }
        // 判断标识符
        int blackInx = dataName.indexOf(" ");
        int bracketsInx = dataName.indexOf("（");
        int bracketsInxCn = dataName.indexOf("(");
        int pointInx = dataName.indexOf(".");
//        int dashInx = dataName.lastIndexOf("-");

//        int start = Math.max(blackInx, Math.max(bracketsInx, Math.max(bracketsInxCn, pointInx)));
        int start = dataName.length();
        if (blackInx > -1) {
            start = Math.min(start, blackInx);
        }
        if (bracketsInx > -1) {
            start = Math.min(start, bracketsInx);
        }
        if (bracketsInxCn > -1) {
            start = Math.min(start, bracketsInxCn);
        }
        if (pointInx > -1) {
            start = Math.min(start, pointInx);
        }
        if (start == -1 || start == dataName.length()) {
            Map<String, String> resMap = new HashMap<String, String>(2);
            resMap.put("searchValue", dataName);
            PostLiterature literature =  postLiteratureMapper.getLiteratureByBaseType(baseType);
            String callNo = "";
            if (literature != null) {
                callNo = literature.getCallNo();
                if (StringUtils.isNotEmpty(callNo) && callNo.indexOf("/") > -1) {
                    callNo = baseType + "/" + (Integer.valueOf(callNo.split("/")[1].substring(0, 1)) + 1);
                } else {
                    callNo = baseType + "/1";
                }
            } else {
                callNo = baseType + "/1";
            }
            resMap.put("callNo", callNo);
            return resMap;
        }

        String serialNumber = "";
        String typeId = "";
        String typeName = dataName.substring(start);
        String beforeName = dataName.substring(0, start);

        // 移除所有含 "-" 的括号
        if (typeName.startsWith("(")) {
            if (typeName.substring(0, typeName.indexOf(")")).contains("-") || typeName.substring(0, typeName.indexOf(")")).contains("、")) {
                start = typeName.indexOf(")") + 1;
            } else {
                start = 0;
            }
        } else if (typeName.startsWith("（")) {
            if (typeName.substring(0, typeName.indexOf("）")).contains("-") || typeName.substring(0, typeName.indexOf("）")).contains("、")) {
                start = typeName.indexOf("）") + 1;
            } else {
                start = 0;
            }
        } else {
            start = 0;
        }
        beforeName += typeName.substring(0, start);
        typeName = typeName.substring(start);
        typeName = this.removeDashIncBrackets(typeName);
        String searchValue = beforeName;
        // 获取种次号
        if (dataName.indexOf("合订") > -1) {
            searchValue += ":合订";
        }
        if (dataName.indexOf("增订") > -1) {
            searchValue += ":增订";
        }
        if (dataName.indexOf("修订") > -1) {
            searchValue += ":修订";
        }
        if (typeName.indexOf("上") > -1) {
            searchValue += ":上";
        } else if (typeName.indexOf("中") > -1) {
            searchValue += ":中";
        } else if (typeName.indexOf("下") > -1) {
            searchValue += ":下";
        }
        List<PostLiterature> postLiteratures = postLiteratureMapper.getPostLiteratureListByName(searchValue);
        if (CollectionUtils.isNotEmpty(postLiterature)) {
            typeId = postLiteratures.get(0).getCallNo().split("/")[1].split(":")[0];
        } else {
            PostLiterature literature = postLiteratureMapper.getLiteratureByBaseType(baseType);
            String callNo = literature.getCallNo();
            if (StringUtils.isNotEmpty(callNo) && callNo.indexOf("/") > -1) {
                typeId = Integer.toString((Integer.valueOf(callNo.split("/")[1].substring(0, 1)) + 1));
            } else {
                typeId = "1";
            }
        }

        // 根据副词量词取辅助区分号
        Map<String, String> map = this.checkSign(typeName, beforeName, searchValue);
        String result = map.get("result");
        String unknowName = map.get("unknowName");
        String knowName = "";
        if (!unknowName.equals(typeName)) {
            if (unknowName.length() == 1 && (")".equals(unknowName) || "）".equals(unknowName))) {
                unknowName = "";
            } else {
                Integer site = typeName.indexOf(unknowName);
                knowName = typeName.substring(0, typeName.indexOf(unknowName));
            }
        }
        // 取无辅助词中的辅助区分号
        if (StringUtils.isNotBlank(unknowName)) {
            if (StringUtils.isBlank(serialNumber)) {
                serialNumber = this.checkNormal(unknowName, beforeName, knowName, searchValue);
            }
        }
        if (!result.startsWith(":")) {
            result = ":" + result;
        }
        if (result.endsWith(":")) {
            if (result.length() == 1) {
                result = "";
            } else {
                result = result.substring(0, result.length() - 1);
            }
        }
        if (!serialNumber.startsWith(":")) {
            serialNumber = ":" + serialNumber;
        }
        if (serialNumber.endsWith(":")) {
            if (serialNumber.length() == 1) {
                serialNumber = "";
            } else {
                serialNumber = serialNumber.substring(0, result.length() - 1);
            }
        }
        Map<String, String> resMap = new HashMap<String, String>(2);
        resMap.put("searchValue", searchValue);
        resMap.put("callNo", baseType + "/" + typeId + result + serialNumber);
        return resMap;
    }

    private boolean checkType(String dataName) {
        return dataName.indexOf("年鉴") > -1 ? true : dataName.indexOf("研究") > -1 ? true :dataName.indexOf("档案") > -1 ? true :
                dataName.indexOf("密档") > -1 ? true : dataName.indexOf("通讯") > -1 ? true : dataName.indexOf("选辑") > -1 ? true :
                        dataName.indexOf("杂志") > -1 ? true : dataName.indexOf("文物") > -1 ? true : dataName.indexOf("古今") > -1 ? true :
                                dataName.indexOf("水文资料") > -1 ? true : dataName.indexOf("气象资料") > -1 ? true : dataName.indexOf("论文集") > -1 ? true : false;
    }

    /**
     * 移除字符串内部的含“-”的地方
     * @param typeName
     * @return
     */
    private String removeDashIncBrackets(String typeName) {
        String result = "";
        String[] startBracket = typeName.split("\\(");
        // 根据辅助区分符截断字符串
        for (int i = 0; i < startBracket.length; i++) {
            String[] startBracketCn = startBracket[i].split("（");
            for (int j = 0; j < startBracketCn.length; j++) {
                String[] endBracket = startBracketCn[j].split("\\)");
                for (int k = 0; k < endBracket.length; k++) {
                    String[] endBracketCn = endBracket[k].split("）");
                    for (int l = 0; l < endBracketCn.length; l++) {
                        String[] space = endBracketCn[l].split(" +");
                        for (int m = 0; m < space.length; m++) {
                            String[] point = space[m].split("\\.");
                            for (int n = 0; n < point.length; n++) {
                                String temp = point[n];
                                if (temp.indexOf("-") == -1) {
                                    result += temp;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 取无辅助词中的辅助区分号
     * @param unknowName
     * @param beforeName
     * @param knowName
     * @return
     */
    private String checkNormal(String unknowName, String beforeName, String knowName, String searchValue) {
        // 年份读取锁
        int numberSwitch = -1;
        // 三位数字读取锁
        int yearSwitch = -1;
        String value = "";
        for (int i = 0, length = unknowName.length(); i < length; i++) {
            char ch = unknowName.charAt(i);
            // 处理四位数年代
            if (48 <= ch && ch <= 57 && yearSwitch < i) {
                String temp = "";
                boolean flag = false;
                for (int j = i; j < i + 5; j++) {
                    if (unknowName.length() - 1 < j) {
                        if (j == i + 4) {
                            i += 3;
                            value = ":" + temp + (StringUtils.isNotBlank(value) ? value : "");
                            numberSwitch += 4;
                            flag = true;
                            break;
                        }
                        yearSwitch += 4;
                        break;
                    }
                    char afterCh = unknowName.charAt(j);
                    if (48 <= afterCh && afterCh <= 57) {
                        temp += String.valueOf(afterCh);
                    }
                    if (48 > afterCh || afterCh > 57) {
                        if (j == i + 4) {
                            value = ":" + temp + (StringUtils.isNotBlank(value) ? value : "");
                            i += 3;
                            numberSwitch += 4;
                            yearSwitch += 4;
                            flag = true;
                        }
                        break;
                    }
                }
                if (flag) {
                    continue;
                }
            }
            // 处理1-999阿拉伯数字
            if (48 <= ch && ch <= 57 && numberSwitch < i) {
                boolean flag = true;
                String result = "";
                for (int j = i; j < i + 4; j++) {
                    String temp = "";
                    // 当后面不足4位元素，返回
                    if (j > unknowName.length() - 1) {
                        flag = false;
                        break;
                    }
                    char afterCh = unknowName.charAt(j);
                    if (48 <= afterCh && afterCh <= 57) {
                        // 若第四位仍是数字，则判断为年，跳过
                        if (j == i + 3) {
                            result = "";
                            break;
                        }
                        temp = String.valueOf(afterCh);
                    } else {
                        break;
                    }
                    result += temp;
                }
                if (StringUtils.isNotEmpty(result)) {
                    value += ":" + result;
                }
                i += result.length() - 1;
            }
            // 处理中文数字
            else if (ch == '十' || ch == '一' || ch == '二' || ch == '三' || ch == '四' || ch == '五' || ch == '六' || ch == '七' || ch == '八' || ch == '九') {
                String tempName = unknowName.substring(i);
                for (int j = 0, size = tempName.length(); j < size; j++) {
                    ch = tempName.charAt(j);
                    if (ch != '十' && ch != '一' && ch != '二' && ch != '三' && ch != '四' && ch != '五' && ch != '六' && ch != '七' && ch != '八' && ch != '九' && ch != '百') {
                        tempName = tempName.substring(0, j);
                        i += j;
                        break;
                    }
                    if (j == size - 1) {
                        i += tempName.length() - 1;
                    }
                }
                value += ":" + ParseNumUtil.numberCnToInt(tempName);
            }
            // 处理希腊数字
            else if (ch == 'I' || ch == 'V' || ch == 'X' || ch == 'L' || ch == 'C' || ch == 'D' || ch == 'M') {
                String tempName = unknowName.substring(i);
                for (int j = 0; j < tempName.length(); j++) {
                    ch = tempName.charAt(j);
                    if (ch != 'I' && ch != 'V' && ch != 'X' && ch != 'L' && ch != 'C' && ch != 'D' && ch != 'M') {
                        tempName = tempName.substring(0, j);
                        i += j - 1;
                        break;
                    }
                }
                value += ":" + ParseNumUtil.romanToInt(tempName);
            }
            // 处理序列
            else if (ch == '上') {
                value += ":1";
            } else if (ch == '中') {
                value += ":2";
            } else if (ch == '下') {
                int index = searchValue.lastIndexOf("下");
                String bookNameStart = new StringBuilder(searchValue).replace(searchValue.lastIndexOf("下"), searchValue.lastIndexOf("下") + 1, "中").toString();
                Integer count = postLiteratureMapper.getPostLiteratureListByName(bookNameStart).size();
                if (null != count && count > 0) {
                    value += ":3";
                } else {
                    value += ":2";
                }
            }

        }
        return value;
    }

    /**
     * 根据副词量词取辅助号区分号
     * @param typeName
     * @param beforeName
     * @return
     */
    private Map<String, String> checkSign(String typeName, String beforeName, String searchValue) {
        char[] adverb = new char[]{'第', '卷', '新'};
        char[] classifier = new char[]{'卷', '册', '辑', '部', '集', '年', '期', '篇', '编'};
        char[] typeCh = typeName.toCharArray();
        String result = "";
        String unknowName = typeName;
        for (int i = 0, length = typeCh.length; i < length; i++) {
            for (int j = 0, size = adverb.length; j < size; j++) {
                if (typeCh[i] == adverb[j]) {
                    if (i == typeCh.length - 1) {
                        continue;
                    }
                    unknowName = typeName.substring(i + 1);
                    int adverbNum = this.getAfterNum(typeName.substring(i + 1), beforeName, searchValue);

                    if (typeName.length() - 1 > i) {
                        if (i > 0 && typeName.charAt(i - 1) == '总') {
                            if (0 != adverbNum) {
                                unknowName = unknowName.substring(Integer.toString(adverbNum).length());
                                continue;
                            }
                        }
                    }
                    if (0 != adverbNum) {
                        unknowName = unknowName.substring(Integer.toString(adverbNum).length());
                        result += ":" + adverbNum;
                    }
                }
            }
            for (int j = 0, size = classifier.length; j < size; j++) {
                Boolean flg = false;
                if (typeCh[i] == classifier[j]) {
                    for (int k = i - 4; k < i; k++) {
                        if (k < 0) {
                            continue;
                        } else {
                            for (int l = 0; l < adverb.length; l++) {
                                if (typeCh[k] == adverb[l]) {
                                    flg = true;
                                    break;
                                }
                            }
                            if (flg) {
                                break;
                            }
                        }
                    }
                    if (flg) {
                        continue;
                    } else {
                        unknowName = typeName.substring(i);
                        int start = 0;
                        if (i > 4) {
                            start = i - 4;
                        }
                        for (int k = i - 1; k > start ; k--) {
                            char ch = typeName.charAt(k);
                            boolean flag = false;
                            for (int l = 0; l < classifier.length; l++) {
                                if (ch == classifier[l]) {
                                    start = k + 1;
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                break;
                            }
                        }
                        String startTypeName = typeName.substring(0, start);
                        String useTypeName = typeName.substring(start, i);
                        String endTypeName = typeName.substring(i, typeName.length());
                        int classifierNum = this.getBeforeNum(typeName.substring(start, i), beforeName, searchValue);
                        for (int k = 0; k < useTypeName.length(); k++) {
                            startTypeName += "N";
                        }
                        typeName = startTypeName + endTypeName;
                        if (classifierNum != 0) {
                            if (classifier[j] == '年' && classifierNum > 999) {
                                result = classifierNum + (StringUtils.isNotBlank(result) ? ":" + result : "");
                            } else {
                                result += ":" + classifierNum;
                            }
                        }
                    }
                }
            }
        }
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("result", result);
        map.put("unknowName", unknowName);
        return map;
    }

    /**
     * 读取副词后的内容
     * @param typeName
     * @param beforeName
     * @return
     */
    private int getAfterNum(String typeName, String beforeName, String searchValue) {
        char[] typeNameCh = typeName.toUpperCase().toCharArray();
        String type = this.checkNumType(typeNameCh[0]);
        return this.getValueByType(type, typeName, beforeName, searchValue);
    }

    /**
     * 读取量词前的内容
     * @param typeName
     * @param beforeName
     * @return
     */
    private int getBeforeNum(String typeName, String beforeName, String searchValue) {
        char[] typeNameCh = typeName.toCharArray();
        for (int i = 0; i < typeNameCh.length; i++) {
            String type = this.checkNumType(typeNameCh[i]);
            if (StringUtils.isNotBlank(type)) {
                typeName = typeName.substring(i);
                return this.getValueByType(type, typeName, beforeName + typeName.substring(0, i), searchValue);
            }
        }
        return 0;
    }

    /**
     * 读取转换数字
     * @param type
     * @param typeName
     * @param beforeName
     * @return
     */
    private int getValueByType(String type, String typeName, String beforeName, String searchValue) {
        switch (type) {
            case "number":
                String value = "";
                for (int i = 0, length = typeName.length(); i < length; i++) {
                    char ch = typeName.charAt(i);
                    if (48 <= ch && ch <= 57) {
                        value += String.valueOf(ch);
                    }
                    if (48 > ch || ch > 57 || i == typeName.length() - 1) {
                        return Integer.parseInt(value);
                    }
                }
                break;
            case "numberCN":
                String numberCN = typeName;
                for (int j = 0, size = typeName.length(); j < size; j++) {
                    char ch = typeName.charAt(j);
                    if (ch != '十' && ch != '一' && ch != '二' && ch != '三' && ch != '四' && ch != '五' && ch != '六' && ch != '七' && ch != '八' && ch != '九' && ch != '百') {
                        numberCN = typeName.substring(0, j);
                        break;
                    }
                }
                return ParseNumUtil.numberCnToInt(numberCN);
            case "roman":
                String roman = typeName;
                for (int j = 0, size = typeName.length(); j < size; j++) {
                    char ch = typeName.charAt(j);
                    if (ch != 'I' && ch != 'V' && ch != 'X' && ch != 'L' && ch != 'C' && ch != 'D' && ch != 'M') {
                        roman = typeName.substring(0, j);
                        break;
                    }
                }
                return ParseNumUtil.romanToInt(roman);
            case "sequence":
                for (int j = 0, size = typeName.length(); j < size; j++) {
                    char ch = typeName.charAt(j);
                    if (ch == '上') {
                        return 1;
                    } else if (ch == '中') {
                        return 2;
                    } else if (ch == '下') {
                        /**
                         * 书名处理
                         */
                        int index = searchValue.lastIndexOf("下");
                        String bookNameStart = new StringBuilder(searchValue).replace(searchValue.lastIndexOf("下"), searchValue.lastIndexOf("下") + 1, "中").toString();
                        Integer count = postLiteratureMapper.getPostLiteratureListByName(bookNameStart).size();
                        if (null != count && count > 0) {
                            return 3;
                        } else {
                            return 2;
                        }
                    }
                }
                break;
                default:
                    return 0;
        }
        return 0;
    }

    private String checkNumType(char ch) {
        if (48 <= ch && ch <= 57) {
            return "number";
        } else if (ch == '十' || ch == '一' || ch == '二' || ch == '三' || ch == '四' || ch == '五' || ch == '六' || ch == '七' || ch == '八' || ch == '九') {
            return "numberCN";
        } else if (ch == 'I' || ch == 'V' || ch == 'X' || ch == 'L' || ch == 'C' || ch == 'D' || ch == 'M') {
            return "roman";
        } else if (ch == '上' || ch == '中' || ch == '下') {
            return "sequence";
        }
        return "";
    }
}
