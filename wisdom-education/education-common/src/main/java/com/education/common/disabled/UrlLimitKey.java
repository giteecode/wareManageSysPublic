package com.education.common.disabled;

import com.education.common.utils.RequestUtils;

public class UrlLimitKey implements LimitKey {

    @Override
    public String getTargetUrl() {
        return RequestUtils.getRequestUrl();
    }
}
