package htkc.ebaba.ecom.vibrentech.response;

public class ProductDetailResponse {
    private String success;
    private Boolean error;
    private String p_name;
    private String p_img;
    private String p_slide1;
    private String p_slide2;
    private String p_slide3;
    private String p_descr;
    private String p_id;
    private String p_price;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_img() {
        return p_img;
    }

    public void setP_img(String p_img) {
        this.p_img = p_img;
    }

    public String getP_slide1() {
        return p_slide1;
    }

    public void setP_slide1(String p_slide1) {
        this.p_slide1 = p_slide1;
    }

    public String getP_slide2() {
        return p_slide2;
    }

    public void setP_slide2(String p_slide2) {
        this.p_slide2 = p_slide2;
    }

    public String getP_slide3() {
        return p_slide3;
    }

    public void setP_slide3(String p_slide3) {
        this.p_slide3 = p_slide3;
    }

    public String getP_descr() {
        return p_descr;
    }

    public void setP_descr(String p_descr) {
        this.p_descr = p_descr;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public ProductDetailResponse(String success, Boolean error, String p_name, String p_img, String p_slide1, String p_slide2, String p_slide3, String p_descr, String p_id, String p_price) {
        this.success = success;
        this.error = error;
        this.p_name = p_name;
        this.p_img = p_img;
        this.p_slide1 = p_slide1;
        this.p_slide2 = p_slide2;
        this.p_slide3 = p_slide3;
        this.p_descr = p_descr;
        this.p_id = p_id;
        this.p_price = p_price;
    }
}
