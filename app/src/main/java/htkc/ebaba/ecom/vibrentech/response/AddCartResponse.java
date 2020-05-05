package htkc.ebaba.ecom.vibrentech.response;

public class AddCartResponse {
    private String success;
    private String msg;
    private boolean error;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public AddCartResponse(String success, String msg, boolean error) {
        this.success = success;
        this.msg = msg;
        this.error = error;
    }
}
