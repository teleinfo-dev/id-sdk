package cn.teleinfo.idhub.sdk.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class AuthInterceptor implements RequestInterceptor {
        private String token;

        public AuthInterceptor(String token) {
            this.token = token;
        }

    /**
     * 添加请求头
     * @param template
     */
    @Override
        public void apply(RequestTemplate template) {
            template.header("Authorization", "Bearer " + token);
        }

}