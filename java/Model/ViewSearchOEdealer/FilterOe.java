
package Model.ViewSearchOEdealer;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FilterOe {

    @SerializedName("data")
    private List<FilterOEdata> mData;
    @SerializedName("result")
    private String mResult;

    public List<FilterOEdata> getData() {
        return mData;
    }

    public void setData(List<FilterOEdata> data) {
        mData = data;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}
