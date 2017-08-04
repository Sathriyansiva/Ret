
package Model.Forgot;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Forgot {

    @SerializedName("data")
    private Boolean mData;
    @SerializedName("result")
    private String mResult;

    public Boolean getData() {
        return mData;
    }

    public void setData(Boolean data) {
        mData = data;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}
