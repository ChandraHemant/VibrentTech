package htkc.ebaba.ecom.vibrentech.response;

public class RegisterResponse {

    private String phone;
    private String success;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    private Boolean error;

    public RegisterResponse(String phone, String success, Boolean error) {
        this.phone = phone;
        this.success = success;
        this.error = error;
    }
}
