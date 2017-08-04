
package Model.ViewOEdealer;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ViewOE {

    @SerializedName("data")
    private List<ViewOEdata> mData;
    @SerializedName("result")
    private String mResult;

    public List<ViewOEdata> getData() {
        return mData;
    }

    public void setData(List<ViewOEdata> data) {
        mData = data;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}
