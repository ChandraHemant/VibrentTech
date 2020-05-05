package htkc.ebaba.ecom.vibrentech.response;

public class User {

    private String uname;
    private String mobile;
    private String id;

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUname() {
        return uname;
    }



    public User(String uname, String mobile, String id) {
        this.id = id;
        this.mobile = mobile;
        this.uname = uname;
    }


}

