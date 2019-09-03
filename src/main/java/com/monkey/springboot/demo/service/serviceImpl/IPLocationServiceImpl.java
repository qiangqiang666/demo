package com.monkey.springboot.demo.service.serviceImpl;

import com.monkey.springboot.demo.service.IPLocationService;
import com.monkey.springboot.demo.utils.ip.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述:
 * 〈淘宝〉
 *
 * @author Monkey
 * @create 2019/9/2 15:29
 */
@Service
public class IPLocationServiceImpl implements IPLocationService {


    @Override
    public String selectIpLocation(String ip) {
        String ipAddr;
        ipAddr = MTUtil.selectLocation(ip);
        if (StringUtils.isNotBlank(ipAddr)){
            return ipAddr;
        }
        ipAddr = TBUtil.selectLocation(ip);
        if (StringUtils.isNotBlank(ipAddr)){
            return ipAddr;
        }
        ipAddr = XLUtil.selectLocation(ip);
        if (StringUtils.isNotBlank(ipAddr)){
            return ipAddr;
        }
        ipAddr = IP138Util.selectLocation(ip);
        if (StringUtils.isNotBlank(ipAddr)){
            return ipAddr;
        }
        ipAddr = IP882667Util.selectLocation(ip);
        if (StringUtils.isNotBlank(ipAddr)){
            return ipAddr;
        }
        ipAddr = IP5Util.selectLocation(ip);
        if (StringUtils.isNotBlank(ipAddr)){
            return ipAddr;
        }
        ipAddr = IPSiteUtil.selectLocation(ip);
        if (StringUtils.isNotBlank(ipAddr)){
            return ipAddr;
        }
        return ipAddr;
    }
}