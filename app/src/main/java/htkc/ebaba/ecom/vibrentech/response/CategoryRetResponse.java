package htkc.ebaba.ecom.vibrentech.response;

public class CategoryRetResponse {

    private String success;
    private Boolean error;
    private String cat_name;
    private String cat_id;
    private String cat_img;

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

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_img() {
        return cat_img;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }

    public CategoryRetResponse(String success, Boolean error, String cat_name, String cat_id, String cat_img) {
        this.success = success;
        this.error = error;
        this.cat_name = cat_name;
        this.cat_id = cat_id;
        this.cat_img = cat_img;
    }
}
