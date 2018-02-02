package cn.zhaocaiapp.zc_app_android.bean.response.common;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 */

public class RelationListResp {
    private List<CommInfo>qualification;
    private List<CommInfo>occupation;

    public List<CommInfo> getQualification() {
        return qualification;
    }

    public void setQualification(List<CommInfo> qualification) {
        this.qualification = qualification;
    }

    public List<CommInfo> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<CommInfo> occupation) {
        this.occupation = occupation;
    }

    public class CommInfo{
        /**
         * "dictCode":0,
         "dictName":"学历",
         "dictType":"QUALIFICATION",
         "dictValue1":"博士"
         * */
        private int dictCode;
        private String dictName;
        private String dictType;
        private String dictValue1;

        public int getDictCode() {
            return dictCode;
        }

        public void setDictCode(int dictCode) {
            this.dictCode = dictCode;
        }

        public String getDictName() {
            return dictName;
        }

        public void setDictName(String dictName) {
            this.dictName = dictName;
        }

        public String getDictType() {
            return dictType;
        }

        public void setDictType(String dictType) {
            this.dictType = dictType;
        }

        public String getDictValue1() {
            return dictValue1;
        }

        public void setDictValue1(String dictValue1) {
            this.dictValue1 = dictValue1;
        }

        @Override
        public String toString() {
            return "Qualification{" +
                    "dictCode=" + dictCode +
                    ", dictName='" + dictName + '\'' +
                    ", dictType='" + dictType + '\'' +
                    ", dictValue1='" + dictValue1 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RelationListResp{" +
                "qualification=" + qualification +
                ", occupation=" + occupation +
                '}';
    }
}
