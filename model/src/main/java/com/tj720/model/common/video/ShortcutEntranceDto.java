package com.tj720.model.common.video;

import java.util.HashMap;
import java.util.List;

/**
 * 快捷入口dto
 * @Author: 程荣凯
 * @Date: 2018/11/2 11:55
 */
public class ShortcutEntranceDto {
    /**
     * 当前用户
     */
    private String currentUserId;
    /**
     * 快捷入口列表
     */
    private List<String> shortcutEntrance;

    private String type;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public List<String> getShortcutEntrance() {
        return shortcutEntrance;
    }

    public void setShortcutEntrance(List<String> shortcutEntrance) {
        this.shortcutEntrance = shortcutEntrance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
