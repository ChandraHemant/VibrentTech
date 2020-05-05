package htkc.ebaba.ecom.vibrentech.response;

public class ResendResponse {
    public ResendResponse(String success, String otp, Boolean error) {
        this.success = success;
        this.otp = otp;
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    private String success;
    private String otp;
    private Boolean error;

}
