package htkc.ebaba.ecom.vibrentech.response;

public class VerifyResponse {
    private String success;
    private String uname;
    private String mobile;
    private Boolean error;
    private String id;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public VerifyResponse(String uname, String id, String mobile, Boolean error, String success) {
        this.success = success;
        this.uname = uname;
        this.mobile = mobile;
        this.error = error;
        this.id = id;
    }
}

