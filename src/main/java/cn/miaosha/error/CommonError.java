package cn.miaosha.error;

/**
 * Description:
 * User: mask-fish
 * Date: 2019/4/18 0018
 * Time: 20:56
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
