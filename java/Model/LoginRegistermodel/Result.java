
package Model.LoginRegistermodel;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("data")
    private Object mData;
    @SerializedName("result")
    private String mResult;

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        mData = data;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}
