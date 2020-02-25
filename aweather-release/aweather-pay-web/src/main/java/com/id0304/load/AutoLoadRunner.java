package com.id0304.load;

import com.id0304.unionpay.acp.sdk.SDKConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AutoLoadRunner implements CommandLineRunner{

    @Override
    public void run(String... args) throws Exception {
        SDKConfig.getConfig().loadPropertiesFromSrc();
        System.out.println(">>>>>>>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<<<<<<<<");
    }
}
