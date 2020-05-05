package htkc.ebaba.ecom.vibrentech.response;

public class SliderRetResponse {

    private String success;
    private Boolean error;
    private String simage;
    private String sname;
    private String sid;

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

    public String getSimage() {
        return simage;
    }

    public void setSimage(String simage) {
        this.simage = simage;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public SliderRetResponse(String success, Boolean error, String simage, String sname, String sid) {
        this.success = success;
        this.error = error;
        this.simage = simage;
        this.sname = sname;
        this.sid = sid;
    }
}
