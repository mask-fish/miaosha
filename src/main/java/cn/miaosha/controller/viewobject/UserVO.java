package cn.miaosha.controller.viewobject;

/**
 * Description:
 * User: mask-fish
 * Date: 2019/4/18 0018
 * Time: 20:25
 */
public class UserVO {
    private Integer id;
    private String name ;
    private Byte gender;
    private Integer age ;
    private String telphone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
