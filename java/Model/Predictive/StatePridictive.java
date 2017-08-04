
package Model.Predictive;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StatePridictive {

    @SerializedName("data")
    private String mData;
    @SerializedName("result")
    private String mResult;

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}
