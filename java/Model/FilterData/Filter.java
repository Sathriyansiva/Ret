
package Model.FilterData;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Filter {

    @SerializedName("data")
    private List<SearchData> mData;
    @SerializedName("result")
    private String mResult;

    public List<SearchData> getData() {
        return mData;
    }

    public void setData(List<SearchData> data) {
        mData = data;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}
