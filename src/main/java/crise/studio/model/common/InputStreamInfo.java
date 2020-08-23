package crise.studio.model.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.InputStream;

public class InputStreamInfo {

    private InputStream inputStream;

    private Long contentLength;

    public InputStreamInfo(InputStream is, Long contentLength) {
        this.inputStream = is;
        this.contentLength = contentLength;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
