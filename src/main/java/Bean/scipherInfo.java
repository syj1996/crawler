package Bean;

public class scipherInfo {
    private  String sequence;
    private  String productType;
    private  String productName;
    private  String company;
    private  String comfirmTime;
    private  String licence;

    public scipherInfo(String sequence, String productType, String productName, String company, String comfirmTime, String licence) {
        this.sequence = sequence;
        this.productType = productType;
        this.productName = productName;
        this.company = company;
        this.comfirmTime = comfirmTime;
        this.licence = licence;
    }

    @Override
    public String toString() {
        return "scipherInfo{" +
                ", productName='" + productName + '\'' +
                ", company='" + company + '\'' +
                ", licence='" + licence + '\'' +
                '}';
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getComfirmTime() {
        return comfirmTime;
    }

    public void setComfirmTime(String comfirmTime) {
        this.comfirmTime = comfirmTime;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }
}
