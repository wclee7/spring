package crise.studio.model.VO;

import java.util.List;

public class BaseVO {

    private ResultVO result;

    private Object data;

    private List<Object> dataList;

    public BaseVO(ResultVO resultVO) {
        this.result = resultVO;
    }

    public BaseVO() {

    }

    public BaseVO(Object data) {
        this.data = data;
    }

    public BaseVO(List<Object> dataList) {
        this.dataList = dataList;
    }

    public BaseVO(Object data, List<Object> dataList) {
        this.data = data;
        this.dataList = dataList;
    }

    public ResultVO getResult() {
        return result;
    }

    public void setResult(ResultVO result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) { this.dataList = dataList; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseVO{");
        sb.append("result=").append(result);
        sb.append(", data=").append(data);
        sb.append(", dataList=").append(dataList);
        sb.append('}');
        return sb.toString();
    }
}
