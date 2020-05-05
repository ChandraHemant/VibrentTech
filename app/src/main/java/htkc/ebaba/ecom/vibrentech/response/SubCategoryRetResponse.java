package htkc.ebaba.ecom.vibrentech.response;

public class SubCategoryRetResponse {

    private String success;
    private Boolean error;
    private String sub_name;
    private String sub_id;

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

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public SubCategoryRetResponse(String success, Boolean error, String sub_name, String sub_id) {
        this.success = success;
        this.error = error;
        this.sub_name = sub_name;
        this.sub_id = sub_id;
    }
}
