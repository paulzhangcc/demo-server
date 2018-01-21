package com.paulzhangcc.demo.constant;

import com.paulzhangcc.sharing.SharingProperties;
import com.paulzhangcc.sharing.spring.ApplicationContextHelper;

/**
 * @author paul
 * @description
 * @date 2018/1/3
 */
public class CmsConsts {

    public static final String ACCESS_PREFIX_0 = "/cms/pic";
    public static final String ACCESS_PREFIX_1 = "cms/pic";

    public static enum CmsEnum {
        banner(100, "banner"), notice(200, "公告"), information(300, "资讯"), avatar(400, "头像");

        CmsEnum(Integer typeCode, String typeName) {
            this.typeCode = typeCode;
            this.typeName = typeName;
        }

        /**
         * 类型编码
         */
        private Integer typeCode;
        /**
         * 类型名称
         */
        private String typeName;


        public Integer getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(Integer typeCode) {
            this.typeCode = typeCode;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        /**
         * 获取存储位置
         *
         * @return
         */
        public String getStorageLocationPrefix() {
            SharingProperties sharingProperties = ApplicationContextHelper.getBean(SharingProperties.class);
            String fileSystemRoot = sharingProperties.getFileSystemRoot();
            if (fileSystemRoot.endsWith("/")) {
                fileSystemRoot = fileSystemRoot + ACCESS_PREFIX_1;
            } else {
                fileSystemRoot = fileSystemRoot + ACCESS_PREFIX_0;
            }
            return fileSystemRoot +"/"+ this.name().toLowerCase()+"/";
        }

        public String getAccessLocationPrefix(){
            SharingProperties sharingProperties = ApplicationContextHelper.getBean(SharingProperties.class);
            String serverLink = sharingProperties.getStaticServerLink();
            if (serverLink.endsWith("/")) {
                serverLink = serverLink + ACCESS_PREFIX_1;
            } else {
                serverLink = serverLink + ACCESS_PREFIX_0;
            }
            return serverLink +"/"+ this.name().toLowerCase()+"/";
        }


    }
}
