package com.zj.zhijue.bean;

/**
 * 充值时长
 */
public class RechargeTimeBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String productName;//服务名称
        private String productUrl;//服务图片
        private String firstPrice;//首冲价格
        private String price;//正常价格
        private String serverDuration;//服务时长
        private int isNew;//0首冲
        private String bargainTotalPrice;

        public String getBargainTotalPrice() {
            return bargainTotalPrice;
        }

        public void setBargainTotalPrice(String bargainTotalPrice) {
            this.bargainTotalPrice = bargainTotalPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductUrl() {
            return productUrl;
        }

        public void setProductUrl(String productUrl) {
            this.productUrl = productUrl;
        }

        public String getFirstPrice() {
            return firstPrice;
        }

        public void setFirstPrice(String firstPrice) {
            this.firstPrice = firstPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getServerDuration() {
            return serverDuration;
        }

        public void setServerDuration(String serverDuration) {
            this.serverDuration = serverDuration;
        }

        public int getIsNew() {
            return isNew;
        }

        public void setIsNew(int isNew) {
            this.isNew = isNew;
        }
    }
}
