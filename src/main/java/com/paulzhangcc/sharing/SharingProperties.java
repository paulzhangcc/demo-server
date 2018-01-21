package com.paulzhangcc.sharing;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author: Paul Zhang
 * @date: 16:21 2017/12/27
 */
@ConfigurationProperties(prefix = "custom.sharing", ignoreUnknownFields = true)
@Component
public class SharingProperties {


    public boolean isOpenThirdpartyService() {
        return openThirdpartyService;
    }

    public void setOpenThirdpartyService(boolean openThirdpartyService) {
        this.openThirdpartyService = openThirdpartyService;
    }

    private boolean openThirdpartyService = Boolean.TRUE ;

    public String getFileSystemRoot() {
        return fileSystemRoot;
    }

    public void setFileSystemRoot(String fileSystemRoot) {
        this.fileSystemRoot = fileSystemRoot;
    }

    private String fileSystemRoot = "/";

    public String getStaticCmsAccessPathPrefix() {
        return staticCmsAccessPathPrefix;
    }

    public void setStaticCmsAccessPathPrefix(String staticCmsAccessPathPrefix) {
        this.staticCmsAccessPathPrefix = staticCmsAccessPathPrefix;
    }

    private String staticCmsAccessPathPrefix;


    public String getStaticServerLink() {
        return staticServerLink;
    }

    public void setStaticServerLink(String staticServerLink) {
        this.staticServerLink = staticServerLink;
    }

    private String staticServerLink;

}
