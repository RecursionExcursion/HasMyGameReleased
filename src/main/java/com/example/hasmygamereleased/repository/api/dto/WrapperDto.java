package com.example.hasmygamereleased.repository.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperDto {

    @JsonProperty("applist")
    AppListDto appList;

    public WrapperDto() {

    }

    public AppListDto getAppList() {
        return appList;
    }

    public void setAppList(AppListDto appList) {
        this.appList = appList;
    }

    public static class AppListDto {

        @JsonProperty("apps")
        private AppDto[] apps;

        public AppListDto() {
        }

        public AppDto[] getApps() {
            return apps;
        }

        public void setApps(AppDto[] apps) {
            this.apps = apps;
        }

        public static class AppDto {

            @JsonProperty("appid")
            private long appId;
            @JsonProperty("name")
            private String name;

            public AppDto() {
            }

            public long getAppId() {
                return appId;
            }

            public void setAppId(long appId) {
                this.appId = appId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
