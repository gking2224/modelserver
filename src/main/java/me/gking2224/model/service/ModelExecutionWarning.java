package me.gking2224.model.service;

public class ModelExecutionWarning {

    public ModelExecutionWarning() {
        super();
    }
    public ModelExecutionWarning(int code, String summary, String detail) {
        super();
        this.code = code;
        this.summary = summary;
        this.detail = detail;
    }
    private int code;
    private String summary;
    private String detail;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    @Override
    public String toString() {
        return "ModelExecutionWarning [code=" + code + ", summary=" + summary + ", detail=" + detail + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime * result + ((detail == null) ? 0 : detail.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ModelExecutionWarning other = (ModelExecutionWarning) obj;
        if (code != other.code)
            return false;
        if (detail == null) {
            if (other.detail != null)
                return false;
        } else if (!detail.equals(other.detail))
            return false;
        if (summary == null) {
            if (other.summary != null)
                return false;
        } else if (!summary.equals(other.summary))
            return false;
        return true;
    }
}
