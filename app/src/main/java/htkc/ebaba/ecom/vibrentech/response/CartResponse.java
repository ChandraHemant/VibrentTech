package htkc.ebaba.ecom.vibrentech.response;

public class CartResponse {
    public CartResponse(String success, Boolean error, String cpname, String cimage, String cdescr, String cpid, String cuid, String cqntty, String cprice, String cid) {
        this.success = success;
        this.error = error;
        this.cpname = cpname;
        this.cimage = cimage;
        this.cdescr = cdescr;
        this.cpid = cpid;
        this.cuid = cuid;
        this.cqntty = cqntty;
        this.cprice = cprice;
        this.cid = cid;
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

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public String getCimage() {
        return cimage;
    }

    public void setCimage(String cimage) {
        this.cimage = cimage;
    }

    public String getCdescr() {
        return cdescr;
    }

    public void setCdescr(String cdescr) {
        this.cdescr = cdescr;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getCqntty() {
        return cqntty;
    }

    public void setCqntty(String cqntty) {
        this.cqntty = cqntty;
    }

    public String getCprice() {
        return cprice;
    }

    public void setCprice(String cprice) {
        this.cprice = cprice;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    private String success;
    private Boolean error;
    private String cpname;
    private String cimage;
    private String cdescr;
    private String cpid;
    private String cuid;
    private String cqntty;
    private String cprice;
    private String cid;



}
