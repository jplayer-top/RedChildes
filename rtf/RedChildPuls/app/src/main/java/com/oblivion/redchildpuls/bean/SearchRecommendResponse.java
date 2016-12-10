package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by arkin on 2016/12/6.
 */
public class SearchRecommendResponse implements IResponse {

    /**
     * response : searchrecommend
     * searchKeywords : ["韩版时尚蕾丝裙","粉色毛衣","女裙","帽子","时尚女裙","时尚秋装"]
     */

    private String response;
    private List<String> searchKeywords;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<String> getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(List<String> searchKeywords) {
        this.searchKeywords = searchKeywords;
    }
}
