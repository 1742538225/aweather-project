package com.id0304.config;

import com.id0304.constants.RoleContants;
import com.id0304.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author WuZhengHua
 * @Description TODO shiro配置类
 * @Date 14:03 2019/7/15
 **/
@Configuration
public class MyShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //1.设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //2.shiro内置过滤器,可以实现拦截
        /**
         * anon:无需认证可以访问
         * authc:必须认证才能访问
         * user:如果使用rememberMe的功能,可以直接访问
         * perms:该资源必须得到资源权限才可以访问
         * role:该资源必须得到角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        //全部放行
        filterMap.put("/login", "anon");
        //添加资源的授权字符串
//        filterMap.put("/add", "perms["+ RoleContants.ROLE_MEMBER+"]");
//        filterMap.put("/update", "perms["+ RoleContants.ROLE_MANAGER+"]");
        //未登录拦截
        filterMap.put("/*", "authc");
        //传入授权相关信息
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
//        //定制登录页
//        shiroFilterFactoryBean.setLoginUrl("/toLogin");
//        //定制未授权页
//        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        return shiroFilterFactoryBean;
    }

    @Bean(name = "userRealm")
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }
}